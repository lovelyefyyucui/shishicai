package com.shishicai.app.utils;

import com.shishicai.BuildConfig;
import android.util.Log;
/**
 *日志打印管理
 *
 */
public class LogUtil {
	private static boolean DEBUG_MODE = BuildConfig.DEBUG;
	
	public static void v(String tag, String msg) {
	    if (DEBUG_MODE) {
	        Log.v(tag, msg);
	    }
	}
	
	public static void v(Class<?> clazz, String msg) {
	    v(clazz.getSimpleName(), msg);
	}
	
	public static void d(String tag, String msg) {
	    if (DEBUG_MODE) {
	        Log.d(tag, msg);
	    }
	}
	
	public static void d(Class<?> clazz, String msg) {
	    d(clazz.getSimpleName(), msg);
	}
	
	public static void i(String tag, String msg) {
	    if (DEBUG_MODE) {
	        Log.i(tag, msg);
	    }
	}
	
	public static void i(String tag, Object msg) {
	    if (DEBUG_MODE) {
	        Log.i(tag, String.valueOf(msg));
	    }
	}
	
	public static void i(Class<?> clazz, String msg) {
	    i(clazz.getSimpleName(), msg);
	}
	
	public static void w(String tag, String msg) {
	    if (DEBUG_MODE) {
	        Log.w(tag, msg);
	    }
	}
	
	public static void w(Class<?> clazz, String msg) {
	    w(clazz.getSimpleName(), msg);
	}
	
	public static void e(String tag, String msg) {
	    if (DEBUG_MODE) {
	        Log.e(tag, msg);
	    }
	}
	
	public static void e(Class<?> clazz, String msg) {
	    e(clazz.getSimpleName(), msg);
	}
}
