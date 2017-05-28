package com.shishicai.app.utils;

import java.util.LinkedList;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

 /**
  * 
  * @author YeFengYun
  *
  * @describe Activity管理栈
  */
public class ActManager {
	
	private static final String TAG = "ActManager";
	private static LinkedList<Activity> activityStack;
	private static ActManager instance;

	private ActManager(){
	}

	/**
	 * 单一实例
	 */
	public static ActManager getAppManager() {
		if (instance == null) {
			instance = new ActManager();
		}
		return instance;
	}

	/**
	 * 压栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new LinkedList<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 当前Activity
	 */
	public Activity currentActivity() {
		Activity activity = null;
		
		if(activityStack.size() > 0){
			activity = activityStack.getLast();
		}
	
		return activity;
	}

	/**
	 * 结束当前Activity
	 */
	public void finishActivity() {
		Activity activity = activityStack.getLast();
		if (null != activity)
			finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}
	
	/**
	 * 获取链表Activity（
	 */
	public Activity getTopActivity() {
		Activity activity = activityStack.getLast();
		return activity;
	}


	/**
	 * 依据类名退出
	 * 
	 * @param cls
	 *            Activity的类名
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	
	/**
	 * 结束所有Activity，但保留最后一个
	 */
	public void finishActivitiesAndKeepLastOne() {
		for (int i = 0, size = activityStack.size()-1; i < size; i++) {
			activityStack.get(0).finish();
			activityStack.remove(0);
		}
	}
	
	public void printActStack(){
		for (int i = 0; i < activityStack.size(); i++) {
			LogUtil.e(TAG, activityStack.get(i).getClass().getSimpleName());
		}
	}
	
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
//			System.exit(0); 
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage());
			e.printStackTrace();
		}
	}

}
