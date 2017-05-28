package com.shishicai.app.utils;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by Administrator on 2017/4/30 0030.
 */
public class JavaScriptObject {
    private Activity mContext;
    public JavaScriptObject(Activity context) {
        super();
        mContext = context;
    }

    @JavascriptInterface
    public void setTitle(String title){

    }
    @JavascriptInterface
    public void go2Recharge(){

    }
    @JavascriptInterface
    public void go2Register(){

    }
    @JavascriptInterface
    public void go2Loan(){

    }

    @JavascriptInterface
    public void getSource(String html) {
        LogUtil.e("Web" ,"html=" + html);
    }
}
