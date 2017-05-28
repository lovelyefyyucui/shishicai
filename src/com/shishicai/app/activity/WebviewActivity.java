package com.shishicai.app.activity;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shishicai.app.domain.BaseModel;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.JudgeInfo;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.ui.ItemLongClickedPopWindow;
import com.shishicai.app.ui.SharePopupWindow;
import com.shishicai.app.utils.ActManager;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.DensityUtil;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.JavaScriptObject;
import com.shishicai.app.utils.LogUtil;
import com.shishicai.app.utils.PreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WebviewActivity extends Base2Activity implements OnClickListener, View.OnTouchListener
{
	private LinearLayout webLayout;
	private LinearLayout linear;
	private ImageView titleimgLeft;
	private TextView title;
	private WebView mWebView;
	private String link;
//	private String url;
	private boolean isHome;
	private String titleString;
	private boolean isHide;
	private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
	private ValueCallback<Uri[]> mUploadCallbackAboveL;
	private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
	private Uri imageUri;
	private String imgUrl, path;
	private int downX, downY;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activitys_webview);
		TAG = "WebviewActivity";
		// 设置标题图片文字
	    title = (TextView) findViewById(R.id.title_top);
	    titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		webLayout = (LinearLayout) findViewById(R.id.web_ll);
		linear = (LinearLayout) findViewById(R.id.msg_load);
		findViewById(R.id.web_home).setOnClickListener(this);
		findViewById(R.id.web_back).setOnClickListener(this);
		findViewById(R.id.web_forward).setOnClickListener(this);
		findViewById(R.id.web_refresh).setOnClickListener(this);
		findViewById(R.id.web_menu).setOnClickListener(this);
		isHome = true;
		final ProgressBar bar = (ProgressBar)findViewById(R.id.load_pb);
		mWebView = (WebView) findViewById(R.id.webView1);
		final Intent intent = getIntent();
		titleString = intent.getStringExtra("title");
		link = intent.getStringExtra("link");
		isHide = PreferencesUtils.getBoolean(this, Constant.IS_OPEN);
		if (TextUtils.isEmpty(link)){
			link = PreferencesUtils.getString(this, Constant.URL_SAVE);
		}else {
			PreferencesUtils.putString(this, Constant.URL_SAVE, link);
		}
		LogUtil.e(TAG, "title=" + titleString);
		LogUtil.e(TAG, "link=" + link);
//		url = link;
		LogUtil.e(TAG, "isHide=" + isHide);
		final WebSettings webSettings = mWebView .getSettings();
		mWebView.setScrollBarStyle(0);//滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上

		webSettings.setJavaScriptEnabled(true);//可用JS
		//打开页面时， 自适应屏幕
		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		//支持屏幕缩放
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		//不显示webview缩放按钮
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		if (HttpMethod.isNetworkConnected(this)) {// 有网络
			webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
		}else {
			webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			webSettings.setLoadsImagesAutomatically(true);
		} else {
			webSettings.setLoadsImagesAutomatically(false);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		// 开启 DOM storage API 功能
		webSettings.setDomStorageEnabled(true);
		//开启 database storage API 功能
		webSettings.setDatabaseEnabled(true);
		String cacheDirPath = getCacheDir().getAbsolutePath();
		//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
		LogUtil.e(TAG, "cacheDirPath=" + cacheDirPath);
		//设置数据库缓存路径
		webSettings.setDatabasePath(cacheDirPath);
		//设置  Application Caches 缓存目录
		webSettings.setAppCachePath(cacheDirPath);
		//开启 Application Caches 功能
		webSettings.setAppCacheEnabled(true);
//		mWebView.addJavascriptInterface(new JavaScriptObject(this),"android");


		mWebView.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
				if (TextUtils.equals(url, link)){
					isHome = true;
				}
//				else if (url.contains("a=kefu")){
//					isHome = false;
//					String urlStr = url.replace("u10064.dded76", "www");
//					mWebView.loadUrl(urlStr);
//					return true;
//				}
				else {
					isHome = false;
				}
				LogUtil.e(TAG, "url=" + url);
//				WebviewActivity.this.url = url;
				mWebView.loadUrl(url);//载入网页
				return  true;//super.shouldOverrideUrlLoading(view,url)
			}//重写点击动作,用webview载入  "<span style=\"color:#FF0000\">网页加载失败</span>",

			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				// 加载网页失败时处理  如：
				view.loadDataWithBaseURL(null, "<span style=\"color:#FF0000\">网页加载失败</span>", "text/html", "utf-8", 	null);
			}

			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				handler.proceed();  // 接受信任所有网站的https证书
			}

			@Override
			public void onPageFinished(WebView view, String url) {
//				view.loadUrl("javascript:window.android.getSource('<head>'+" +
//						"document.getElementsByTagName('html')[0].innerHTML+'</head>');");
				if(!webSettings.getLoadsImagesAutomatically()) {
					webSettings.setLoadsImagesAutomatically(true);
				}
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient(){
			public void onProgressChanged(WebView view,int progress){//载入进度改变而触发
				super.onProgressChanged(view, progress);
				if (progress == 100) {
					bar.setVisibility(View.GONE);
				} else {
					if (View.GONE == bar.getVisibility()) {
						bar.setVisibility(View.VISIBLE);
					}
					bar.setProgress(progress);
				}
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
				AlertDialog.Builder b = new AlertDialog.Builder(WebviewActivity.this);
//				b.setTitle("提示");
				b.setMessage(message);
				b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				});
				b.setCancelable(false);
				b.create().show();
				return true;
			}

			//设置响应js 的Confirm()函数
			@Override
			public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
				AlertDialog.Builder b = new AlertDialog.Builder(WebviewActivity.this);
				b.setTitle("Confirm");
				b.setMessage(message);
				b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				});
				b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
				});
				b.create().show();
				return true;
			}

			//设置响应js 的Prompt()函数
			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
				final View v = View.inflate(WebviewActivity.this, R.layout.prompt_dialog, null);
				((TextView) v.findViewById(R.id.prompt_message_text)).setText(message);
				((EditText) v.findViewById(R.id.prompt_input_field)).setText(defaultValue);
				AlertDialog.Builder b = new AlertDialog.Builder(WebviewActivity.this);
				b.setTitle("Prompt");
				b.setView(v);
				b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String value = ((EditText) v.findViewById(R.id.prompt_input_field)).getText().toString();
						result.confirm(value);
					}
				});
				b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
				});
				b.create().show();
				return true;
			}

			@Override
			public boolean onShowFileChooser(WebView webView,
											 ValueCallback<Uri[]> filePathCallback,
											 FileChooserParams fileChooserParams) {
				mUploadCallbackAboveL=filePathCallback;
				take();
				return true;
			}


			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage=uploadMsg;
				take();
			}
			public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType) {
				mUploadMessage=uploadMsg;
				take();
			}
			public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType, String capture) {
				mUploadMessage=uploadMsg;
				take();
			}
		});

		mWebView.setOnTouchListener(this);

		mWebView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				WebView.HitTestResult result = ((WebView)v).getHitTestResult();
				if (null == result)
					return false;
				int type = result.getType();
				if (type == WebView.HitTestResult.UNKNOWN_TYPE)
					return false;
				if (type == WebView.HitTestResult.EDIT_TEXT_TYPE) {
					//let TextViewhandles context menu return true;
				}

				final ItemLongClickedPopWindow itemLongClickedPopWindow = new ItemLongClickedPopWindow(WebviewActivity.this, ItemLongClickedPopWindow.IMAGE_VIEW_POPUPWINDOW, DensityUtil.dip2px(WebviewActivity.this, 120), DensityUtil.dip2px(WebviewActivity.this, 90));
				// Setup custom handlingdepending on the type
				switch (type) {
					case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
						break;
					case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
						break;
					case WebView.HitTestResult.GEO_TYPE: // TODO
						break;
					case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
						// Log.d(DEG_TAG, "超链接");
						break;
					case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
						break;
					case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
						imgUrl = result.getExtra();
						LogUtil.e(TAG, "imgUrl=" + imgUrl);
						//通过GestureDetector获取按下的位置，来定位PopWindow显示的位置
						itemLongClickedPopWindow.showAtLocation(v,Gravity.NO_GRAVITY, downX, downY + 10);
						break;
					default:
						break;
				}

				itemLongClickedPopWindow.getView(R.id.item_longclicked_viewImage)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								itemLongClickedPopWindow.dismiss();
								if (TextUtils.isEmpty(imgUrl)){
									HttpMethod.Toast(WebviewActivity.this, "请先保存图片");
								}else if (!TextUtils.isEmpty(path)){
									Intent intent1 = new Intent(WebviewActivity.this, ShowImgActivity.class);
									intent1.putExtra("path", path);
									startActivity(intent1);
								}
							}
						});
				itemLongClickedPopWindow.getView(R.id.item_longclicked_saveImage)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								itemLongClickedPopWindow.dismiss();
								new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
							}
						});
				return true;
			}
		});

		if (isHide)
		{
			findViewById(R.id.title_layout).setVisibility(View.GONE);
			mWebView.loadUrl(link);
		}else {
			findViewById(R.id.web_bottom).setVisibility(View.GONE);
			title.setText(titleString);
			linear.setVisibility(View.VISIBLE);
			webLayout.setVisibility(View.GONE);
			webSettings.setTextSize(WebSettings.TextSize.LARGEST);//字体大小
			getHtml(link);
		}
	}


	/***
	 * 功能：用线程保存图片
	 *
	 * @author wangyp
	 */
	private class SaveImage extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			String result;
			try {
				String sdcard = Environment.getExternalStorageDirectory().toString();
				File file = new File(sdcard + "/Download");
				if (!file.exists()) {
					file.mkdirs();
				}
//				int idx = imgUrl.lastIndexOf(".");
//				String ext = imgUrl.substring(idx);
				String imgUrlEncoder = "";
				try {
					imgUrlEncoder = URLEncoder.encode(imgUrl, "utf-8");  //输出%C4%E3%BA%C3
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				file = new File(sdcard + "/Download/" + imgUrlEncoder);// + ext
				InputStream inputStream = null;
				URL url = new URL(imgUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(20000);
				if (conn.getResponseCode() == 200) {
					inputStream = conn.getInputStream();
				}
				byte[] buffer = new byte[4096];
				int len = 0;
				FileOutputStream outStream = new FileOutputStream(file);
				while ((len = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				outStream.close();
				path = file.getAbsolutePath();
				result = "图片已保存至：" + path;
			} catch (Exception e) {
				result = "保存失败！" + e.getLocalizedMessage();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			HttpMethod.Toast(WebviewActivity.this, result);
		}
	}


	private SharePopupWindow popWindow;


	@Override
	public void onClick(View v)
	{
		switch (v.getId()) {
		case R.id.title_left:
			finish();
			break;
			case R.id.web_home:
				isHome = true;
				if (isHide)
				{
					mWebView.loadUrl(link);
				}else {
					getHtml(link);
				}
				break;
			case R.id.web_back:
				if (mWebView.canGoBack()) {
					mWebView.goBack();
				}else {
					HttpMethod.Toast(this, "不能再后退了!");
				}
				break;
			case R.id.web_forward:
				if (mWebView.canGoForward()) {
					mWebView.goForward();
				}else {
					HttpMethod.Toast(this, "不能再前进了!");
				}
				break;
			case R.id.web_refresh:
				mWebView.reload();
				break;
			case R.id.web_menu:
				if (popWindow == null){
					popWindow = new SharePopupWindow(this, this);
				}
				popWindow.showAtLocation(v, Gravity.NO_GRAVITY, v.getLeft(), v.getTop());
				break;
			case R.id.font1:
				popWindow.dismiss();
				getShare();
				break;
			case R.id.font2:
				popWindow.dismiss();
				Intent intent = new Intent(this, MsgActivity.class);
				startActivity(intent);
				break;
			case R.id.font3:
				popWindow.dismiss();
				clearCacheFolder(getCacheDir());
				break;
		}
	}

	private long firstBack;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack() && event.getRepeatCount() == 0 && !isHome) {
			    mWebView.goBack();
	            return true;     
	        }
	        if(keyCode == KeyEvent.KEYCODE_BACK && isHome){
				if (isHide){
					long secondBack = System.currentTimeMillis();
					if (secondBack - firstBack > 2000) {
						Toast.makeText(this, "再按一次退出程序",
								Toast.LENGTH_SHORT).show();
						firstBack = secondBack;
						return true;
					} else {
//						PreferencesUtils.putString(this, Constant.URL_SAVE, url);
						PreferencesUtils.putLong(this, Constant.EXIT_TIME, System.currentTimeMillis());
						MobclickAgent.onKillProcess(this);
						ActManager.getAppManager().AppExit(this);
					}
				}else {
//					PreferencesUtils.putString(this, Constant.URL_SAVE, url);
					finish();
					return true;
				}
	        }
		return super.onKeyDown(keyCode, event);
	}

	private void getHtml(String url){
		HttpUser.get(url, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e(TAG, "err=" + arg3.getMessage());
				linear.setVisibility(View.GONE);
				webLayout.setVisibility(View.VISIBLE);
				Toast.makeText(WebviewActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
			}


			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				linear.setVisibility(View.GONE);
				webLayout.setVisibility(View.VISIBLE);
				LogUtil.e(TAG, "result=" + str);
				if (!TextUtils.isEmpty(str)) {
					String body = str.substring(str.indexOf("<article"), str.indexOf("</article>"));
					String html="<html><body>" + body + "</article>" + "</body></html>";
					LogUtil.e(TAG, "html=" + html);
					mWebView.loadDataWithBaseURL(link, html, "text/html","UTF-8", null);
				}
			}
		});
	}

	// clear the cache before time numDays
	private int clearCacheFolder(File dir) {
		int deletedFiles = 0;
		if (dir!= null && dir.isDirectory()) {
			try {
				for (File child:dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child);
					}
					if (child.delete()) {
							deletedFiles++;
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
//		deleteDatabase("webview.db");
//		deleteDatabase("webviewCache.db");
//		if (deletedFiles > 0){
			HttpMethod.Toast(this, "缓存清除完成!");
//		}
		return deletedFiles;
	}


	private void getShare(){
		HttpUser.get(Constant.URL_SHARE, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e(TAG, "err=" + arg3.getMessage());
				Toast.makeText(WebviewActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
			}


			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				LogUtil.e(TAG, "result=" + str);
				if (!TextUtils.isEmpty(str)) {
					BaseModel<JudgeInfo> info = GsonUtils.parseJSON(str, new TypeToken<BaseModel<JudgeInfo>>() {
					}.getType());
					if (info.isSuccess())
					{
						String title, content;
						title = info.getMsg().getName();
//						if (TextUtils.equals(info.getMsg().getOpen(), "0"))
//						{
//							content = info.getMsg().getLinks();
//						}else {
//							content = "热门彩种，专业权威数据分析，足不出户，掌握最新动态";
//						}
						content = info.getMsg().getLinks();
						if (TextUtils.isEmpty(content)){
							content = "热门彩种，专业权威数据分析，足不出户，掌握最新动态www.90068.com";
						}
						Intent share_intent = new Intent();
						share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
						share_intent.setType("text/plain");//设置分享内容的类型
						share_intent.putExtra(Intent.EXTRA_SUBJECT, title);//添n加分享内容标题
						share_intent.putExtra(Intent.EXTRA_TEXT, content);//添加分享内容
						//创建分享的Dialog
						share_intent = Intent.createChooser(share_intent, "分享到...");
						startActivity(share_intent);
					}
				}
			}

		});
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		downX = (int) event.getX();
		downY = (int) event.getY();
		LogUtil.e(TAG, "x=" + downX + ",y=" + downY);
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==FILECHOOSER_RESULTCODE)
		{
			if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
			Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
			if (mUploadCallbackAboveL != null) {
				onActivityResultAboveL(requestCode, resultCode, data);
			}
			else  if (mUploadMessage != null) {

				if (result != null) {
					String path = getPath(getApplicationContext(),
							result);
					Uri uri = Uri.fromFile(new File(path));
					mUploadMessage
							.onReceiveValue(uri);
				} else {
					mUploadMessage.onReceiveValue(imageUri);
				}
				mUploadMessage = null;

			}
		}
	}



	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
		if (requestCode != FILECHOOSER_RESULTCODE
				|| mUploadCallbackAboveL == null) {
			return;
		}

		Uri[] results = null;

		if (resultCode == Activity.RESULT_OK) {

			if (data == null) {

				results = new Uri[]{imageUri};
			} else {
				String dataString = data.getDataString();
				ClipData clipData = data.getClipData();

				if (clipData != null) {
					results = new Uri[clipData.getItemCount()];
					for (int i = 0; i < clipData.getItemCount(); i++) {
						ClipData.Item item = clipData.getItemAt(i);
						results[i] = item.getUri();
					}
				}

				if (dataString != null)
					results = new Uri[]{Uri.parse(dataString)};
			}
		}
		if(results!=null){
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		}else{
			results = new Uri[]{imageUri};
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		}

		return;
	}



	private void take(){
		File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
		// Create the storage directory if it does not exist
		if (! imageStorageDir.exists()){
			imageStorageDir.mkdirs();
		}
		File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
		imageUri = Uri.fromFile(file);

		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
		for(ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent i = new Intent(captureIntent);
			i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
			i.setPackage(packageName);
			i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			cameraIntents.add(i);

		}
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("image/*");
		Intent chooserIntent = Intent.createChooser(i,"Image Chooser");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
		startActivityForResult(chooserIntent,  FILECHOOSER_RESULTCODE);
	}

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[]{split[1]};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}


	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context       The context.
	 * @param uri           The Uri to query.
	 * @param selection     (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {column};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null) cursor.close();
		}
		return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		if (isHide){
			HttpUser.cancel(this);
		}
		if (popWindow != null)
		{
			if (popWindow.isShowing())
			{
				popWindow.dismiss();
			}
			popWindow = null;
		}
	}

	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);//must store the new intent unless getIntent() will return the old one
	}

}
