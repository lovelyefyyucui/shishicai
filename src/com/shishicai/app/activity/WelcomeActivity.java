package com.shishicai.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.domain.BaseModel;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.JudgeInfo;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;
import com.shishicai.app.utils.PreferencesUtils;

import org.apache.http.Header;
import org.litepal.tablemanager.Connector;

import java.lang.ref.WeakReference;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends Base2Activity {
	private static Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcom);
		TAG = "WelcomeActivity";
		String registrationId = PreferencesUtils.getString(this, Constant.DEVICE_ID);
		if (TextUtils.isEmpty(registrationId)){
			registrationId = JPushInterface.getRegistrationID(this);
			if (!TextUtils.isEmpty(registrationId)){
				LogUtil.e(TAG, "Registration Id : " + registrationId);
				PreferencesUtils.putString(this, Constant.DEVICE_ID, registrationId);
			}
		}
		Connector.getDatabase();
		handler = new Handler();
		LoadMainTabTask task = new LoadMainTabTask(this);
		handler.postDelayed(task, 1000);
	}
	/**
	 * 延迟2秒进入主界面
	 * @author Administrator
	 */
	private static class LoadMainTabTask implements Runnable {
		WeakReference<WelcomeActivity> mActivity;

		LoadMainTabTask(WelcomeActivity mActivity){
			this.mActivity = new WeakReference<WelcomeActivity>(mActivity);
		}

		public void run() {
			if (mActivity.get() != null){
				if (HttpMethod.isNetworkConnected(mActivity.get())) {
					LogUtil.e("WelcomeActivity", "url=" + Constant.URL_JUDGE);
					HttpUser.get(Constant.URL_JUDGE, new AsyncHttpResponseHandler(){

						//连接失败
						public void onFailure(int arg0, Header[] arg1,
											  byte[] arg2, Throwable arg3) {
							LogUtil.e("WelcomeActivity", "err=" + arg3.getMessage());
							Intent intent = new Intent();
//							intent.setClass(mActivity.get(), DayNewsActivity.class);
							intent.setClass(mActivity.get(), MainActivity.class);
//							intent.setClass(mActivity.get(), WebviewActivity.class);
//							intent.putExtra("link", "http://www.shishizhong888.com");
//							intent.putExtra("title", "hide");
							mActivity.get().startActivity(intent);
							mActivity.get().finish();
						}
						//连接成功
						public void onSuccess(int arg0, Header[] arg1,
											  byte[] arg2) {
							String str = new String(arg2);
							LogUtil.e("WelcomeActivity", "result=" + str);
							LogUtil.e("WelcomeActivity", "code=" + arg0);
							if (!TextUtils.isEmpty(str)) {
								BaseModel<JudgeInfo> info = null;
								try {
									info = GsonUtils.parseJSON(str, new TypeToken<BaseModel<JudgeInfo>>() {
									}.getType());
								}catch (JsonSyntaxException e){
									LogUtil.e("WelcomeActivity", "err=" + e.getMessage());
									e.printStackTrace();
								}
								if (info != null && info.isSuccess())
								{
									Intent intent = new Intent();
									if (info.getMsg() != null && TextUtils.equals(info.getMsg().getOpen(), "0"))
									{
										PreferencesUtils.putBoolean(mActivity.get(), Constant.IS_OPEN, false);
										LogUtil.e("WelcomeActivity", "info=" + info.getMsg().toString());
										intent.setClass(mActivity.get(), MainActivity.class);

//										intent.setClass(mActivity.get(), WebviewActivity.class);
//										intent.putExtra("link", "http://www.shishizhong888.com");//
//										intent.putExtra("title", mActivity.get().getResources().getString(R.string.app_name));
									}else {
										PreferencesUtils.putBoolean(mActivity.get(), Constant.IS_OPEN, true);
										intent.setClass(mActivity.get(), WebviewActivity.class);
										intent.putExtra("link", info.getMsg().getLinks());//"http://www.shishizhong888.com"
//										intent.putExtra("link", "http://www.shishizhong888.com");//info.getMsg().getLinks()
										intent.putExtra("title", mActivity.get().getResources().getString(R.string.app_name));
									}
									mActivity.get().startActivity(intent);
									mActivity.get().finish();
								}else {
									PreferencesUtils.putBoolean(mActivity.get(), Constant.IS_OPEN, true);
									Intent intent = new Intent();
									intent.setClass(mActivity.get(), WebviewActivity.class);
									intent.putExtra("link", "http://www.shishizhong888.com");//
									intent.putExtra("title", mActivity.get().getResources().getString(R.string.app_name));
									mActivity.get().startActivity(intent);
									mActivity.get().finish();
								}
							}

						}

					});
				}else{
					Intent intent = new Intent();
//					intent.setClass(mActivity.get(), NoInternet.class);
//					intent.setClass(mActivity.get(), DayNewsActivity.class);
					intent.setClass(mActivity.get(), MainActivity.class);
					mActivity.get().startActivity(intent);
					mActivity.get().finish();
				}
			}
		}
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
		HttpUser.cancel(this);
	}
}
