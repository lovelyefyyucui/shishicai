package com.shishicai.app.activity.mine;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import com.shishicai.R;

import com.shishicai.app.Constant;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.LogUtil;

public class MineSetting extends Activity implements OnClickListener {
	
	private TextView title, person, phone, aboutus,pwd;// 标题信息
	private ImageView titleimgLeft; 
	private ImageView thumb;
	private Button exit;
	private final String TAG = "com.soshow.hiyoga";
	private static final int CAMERA_WITH_DATA = 3024; // /*用来标识请求照相功能的activity*/
	private static final int PHOTO_PICKED_WITH_DATA = 3025; // /*用来标识请求gallery的activity*/
	private static final String PHOTO = Constant.DIR_IMAGE + "PHOTO_5.jpg";
	private DisplayImageOptions options;
	private SharedPreferences sharedpreferences;

			@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.mine_setting);
			options = new DisplayImageOptions.Builder()
	         .showStubImage(R.drawable.g_car_sample)    //在ImageView加载过程中显示图片
	         .showImageForEmptyUri(R.drawable.g_car_sample)  //image连接地址为空时
	         .showImageOnFail(R.drawable.g_car_sample)  //image加载失败
	         .cacheInMemory(true)  //加载图片时会在内存中加载缓存
	         .cacheOnDisc(true)   //加载图片时会在磁盘中加载缓存
	         .displayer(new RoundedBitmapDisplayer(20))  //设置用户加载图片task(这里是圆角图片显示)
	         .build();
			// 设置标题图片文字
			title = (TextView) findViewById(R.id.title_top);
			title.setText("设置");
			titleimgLeft = (ImageView) findViewById(R.id.title_left);
			titleimgLeft.setImageResource(R.drawable.title_left);
			titleimgLeft.setOnClickListener(this);
			thumb = (ImageView) findViewById(R.id.mine_head_picture);
			exit = (Button) findViewById(R.id.mine_exit);
			exit.setOnClickListener(this);
			String url = Constant.URLDomain + "/uc_server/avatar.php?uid=" + "&size=small";
			ImageLoader.getInstance().displayImage(url, thumb, options, Constant.animateFirstListener);
			thumb.setOnClickListener(this);
			person = (TextView) findViewById(R.id.mine_person);
			person.setOnClickListener(this);
			phone = (TextView) findViewById(R.id.mine_phone);
			phone.setOnClickListener(this);
			pwd = (TextView) findViewById(R.id.mine_pwd);
			pwd.setOnClickListener(this);
			aboutus = (TextView) findViewById(R.id.aboutus);
			aboutus.setOnClickListener(this);
	}
			
			

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.title_left:
					MineSetting.this.finish();
					break;
				case R.id.mine_head_picture:
					handleThumb();
					break;
				case R.id.mine_person:
					Intent intent = new Intent(this, MineInformation.class);
					startActivity(intent);
					break;
				case R.id.mine_pwd:
					Intent intent1 = new Intent(this, MinePasswod.class);
					startActivity(intent1);
					break;
				case R.id.aboutus:
					
					break;
				case R.id.mine_exit:
					sharedpreferences = getSharedPreferences(
							TAG, 0);
					SharedPreferences.Editor editor = sharedpreferences.edit();// 调用SharedPreferences.Editor方法对SharedPreferences进行修改
					//editor.remove("type");
					editor.putInt("type", 0);
					editor.putInt("status", 0);
					editor.commit();
					Constant.isLogin = false;
					HttpMethod.sessionValue = "";
					setResult(49);
					finish();
					break;
				}
			}



			public void handleThumb() {
				String cancel = "取消";
				String[] choices;
				choices = new String[2];
				choices[0] = "拍照"; // 拍照
				choices[1] = "用户相册"; // 从相册中选择

				Context context = this;
				// Wrap our context to inflate list items using correct theme
				final Context dialogContext = new ContextThemeWrapper(context,
						android.R.style.Theme_Light);

				final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
						android.R.layout.simple_list_item_1, choices);

				final AlertDialog.Builder builder = new AlertDialog.Builder(
						dialogContext);
				builder.setSingleChoiceItems(adapter, -1,
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int which)
							{
								dialog.dismiss();
								switch (which)
								{
								case 0:
								{
									String status = Environment
											.getExternalStorageState();
									// 判断是否有SD卡
									if (status.equals(Environment.MEDIA_MOUNTED))
									{
										doTakePhoto();// 用户点击了从照相机获取
									}
									else
									{
										Toast.makeText(MineSetting.this,
												"请先按装sd卡", Toast.LENGTH_SHORT).show();
									}
									break;
								}
								case 1:
									doPickPhotoFromGallery();// 从相册中去获取
									break;
								}
							}
						});
				builder.setNegativeButton(cancel, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
			
			/*
			 * 从相册获取
			 */
			protected void doPickPhotoFromGallery() {
				try
				{
					// Launch picker to choose photo for selected contact
					final Intent intent = getPhotoPickIntent();
					startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
				}
				catch (ActivityNotFoundException e)
				{
					Toast.makeText(this, "获取相册失败", Toast.LENGTH_LONG).show();
				}
			}

			/*
			 *  封装请求Gallery的intent
			 */
			private Intent getPhotoPickIntent() {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setType("image/*");
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", 100);
				intent.putExtra("outputY", 100);
				intent.putExtra("return-data", true);
				return intent;
			}

			/*
			 * 拍照
			 */
			protected void doTakePhoto() {
				try
				{
					final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE,
							null);
					startActivityForResult(intent, CAMERA_WITH_DATA);
				}
				catch (ActivityNotFoundException e)
				{
					Toast.makeText(this, "获取相机失败", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			protected void onActivityResult(int requestCode, int resultCode,
					Intent data) {
				switch (requestCode)
				{
				case PHOTO_PICKED_WITH_DATA:
					if (data.hasExtra("data")) {
						// 调用Gallery返回的
						final Bitmap photo = data.getParcelableExtra("data");
						// 下面就是显示照片了
						cacheToLocal(photo);
					}
					break;
				case CAMERA_WITH_DATA:
					if (data.hasExtra("data")) {
						// 照相机程序返回的,再次调用图片剪辑程序去修剪图片
						final Bitmap photo1 = data.getParcelableExtra("data");
						cacheToLocal(photo1);
					}
					break;
				}
				super.onActivityResult(requestCode, resultCode, data);
			}
			
			private void cacheToLocal(Bitmap bmp) {
				File file = new File(PHOTO);
				BufferedOutputStream bos = null;
				try
				{
					bos = new BufferedOutputStream(new FileOutputStream(file));
				}
				catch (FileNotFoundException e)
				{
					LogUtil.e(TAG + "fileerr",e.toString());
				}
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				try
				{
					bos.flush();
					bos.close();
				}
				catch (IOException e)
				{
					Log.e(TAG + "err",e.toString());
				}
				uploadData(file);
			}

			private void uploadData(File file) {
				Log.e(TAG + "file",file.toString());
				if (HttpMethod.isNetworkConnected(MineSetting.this)) 
				{
					RequestParams params = new RequestParams();
					params.put("authkey", Constant.session_id);
					try {
						params.put("file", file);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						Log.e(TAG + "file",e1.toString());
					}
					HttpUser.post(Constant.URLADV + "uploadavatar.php", params, new AsyncHttpResponseHandler(){

						public void onFailure(int arg0, Header[] arg1,
											  byte[] arg2, Throwable arg3) {
							Log.e(TAG,Constant.session_id + "err:" + arg3);
						}

						public void onSuccess(int arg0, Header[] arg1,
											  byte[] arg2) {
							String str = new String(arg2);
							Log.e(TAG,Constant.session_id + "result:" + str);
							JSONObject jsonObject;
							try
							{
								jsonObject = new JSONObject(str);
								if (TextUtils.equals(jsonObject.optString("status"),"200")) {
									Toast.makeText(MineSetting.this, "头像修改成功", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								LogUtil.e(TAG + "jsonerr",e.toString());
							}

						}

					});
				}else{
					HttpMethod.Toast(MineSetting.this);
				}
			}
}
