package com.shishicai.app.utils;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/30 0030.
 */

public class MyWebView extends WebView {
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String stringByEvaluatingJavaScriptFromString(String script) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                Field mp = WebView.class.getDeclaredField("mProvider");
                mp.setAccessible(true);
                Object webViewObject = mp.get(this);
                Field wc = webViewObject.getClass().getDeclaredField("mWebViewCore");
                wc.setAccessible(true);
                Object webViewCore = wc.get(webViewObject);
                Field bf = webViewCore.getClass().getDeclaredField("mBrowserFrame");
                bf.setAccessible(true);
                Object browserFrame = bf.get(webViewCore);
                Method stringByEvaluatingJavaScriptFromString = browserFrame.getClass()
                        .getDeclaredMethod("stringByEvaluatingJavaScriptFromString",
                                String.class);
                stringByEvaluatingJavaScriptFromString.setAccessible(true);
                Object obj_value = stringByEvaluatingJavaScriptFromString.invoke(
                        browserFrame, script);
                return String.valueOf(obj_value);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e("stringByEvaluatingJavaScriptFromString", e.getMessage());
            }
            return null;
        } else {
            try {
                Field[] fields = WebView.class.getDeclaredFields();
                // 由webview取到webviewcore
                Field field_webviewcore = WebView.class.getDeclaredField("mWebViewCore");
                field_webviewcore.setAccessible(true);
                Object obj_webviewcore = field_webviewcore.get(this);
                // 由webviewcore取到BrowserFrame
                Field field_BrowserFrame = obj_webviewcore.getClass().getDeclaredField(
                        "mBrowserFrame");
                field_BrowserFrame.setAccessible(true);
                Object obj_frame = field_BrowserFrame.get(obj_webviewcore);
                // 获取BrowserFrame对象的stringByEvaluatingJavaScriptFromString方法
                Method method_stringByEvaluatingJavaScriptFromString = obj_frame.getClass()
                        .getMethod("stringByEvaluatingJavaScriptFromString", String.class);
                // 执行stringByEvaluatingJavaScriptFromString方法
                Object obj_value = method_stringByEvaluatingJavaScriptFromString.invoke(
                        obj_frame,
                        script);
                // 返回执行结果
                return String.valueOf(obj_value);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e("stringByEvaluatingJavaScriptFromString", e.getMessage());
            }
            return null;
        }
    }
}
