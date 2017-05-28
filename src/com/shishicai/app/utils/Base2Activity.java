package com.shishicai.app.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import cn.jpush.android.api.JPushInterface;
import com.umeng.analytics.MobclickAgent;

public abstract class Base2Activity extends Activity{

	protected String TAG = "Base2Activity";
	protected InputMethodManager inputMethodManager;

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
 		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 添加Activity到堆栈
		ActManager.getAppManager().addActivity(this);
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		ActManager.getAppManager().finishActivity(this);
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
    protected void onPause() {
    	// TODO Auto-generated method stub
    	JPushInterface.onPause(this);
    	// umeng
    	MobclickAgent.onPageEnd(TAG);
		MobclickAgent.onPause(this);
    	super.onPause();
    }
	

    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
