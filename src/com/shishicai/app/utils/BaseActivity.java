package com.shishicai.app.utils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import cn.jpush.android.api.JPushInterface;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends FragmentActivity {
	protected String TAG = "BaseActivity";
	
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // umeng
        MobclickAgent.onPageStart(TAG);//统计页面的访问量
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        
    }
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	JPushInterface.onPause(this);
    	// umeng
    	MobclickAgent.onPageEnd(TAG);
		MobclickAgent.onPause(this);
    	super.onPause();
    }
   

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		ActManager.getAppManager().finishActivity(this);
	}

    /**
     * back
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }
}
