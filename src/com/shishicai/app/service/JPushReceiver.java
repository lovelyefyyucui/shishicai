package com.shishicai.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.shishicai.R;
import com.shishicai.app.activity.MainActivity;
import com.shishicai.app.activity.WebviewActivity;
import com.shishicai.app.db.dao.MsgInfo;
import com.shishicai.app.Constant;
import com.shishicai.app.utils.LogUtil;
import com.shishicai.app.utils.PreferencesUtils;

import java.util.Date;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/4/29 0029.
 */

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private int notifactionId;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogUtil.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            if (!TextUtils.isEmpty(regId)){
                LogUtil.e(TAG, "[MyReceiver] Registration Id : " + regId);
                PreferencesUtils.putString(context, Constant.DEVICE_ID, regId);
            }
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extraJson = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
            LogUtil.e(TAG, "Message title : " + title);
            LogUtil.e(TAG, "Message message : " + message);
            LogUtil.e(TAG, "Message extraJson : " + extraJson);
            LogUtil.e(TAG, "Message alert : " + alert);
//            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            LogUtil.e(TAG, "[MyReceiver] notify Id: " + notifactionId);
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extraJson = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
            LogUtil.e(TAG, "Message title : " + title);
            LogUtil.e(TAG, "Message id : " + msgId);
            LogUtil.e(TAG, "Message message : " + message);
            LogUtil.e(TAG, "Message extraJson : " + extraJson);
            LogUtil.e(TAG, "Message alert : " + alert);
            MsgInfo info = new MsgInfo();
            info.setMsgId(msgId);
            info.setTitle(notifactionId + "");//title
            info.setContent(alert);
            info.setTime(new Date());
            info.save();
//            if (info.save()) {
//                HttpMethod.Toast(context, "存储成功");
//            } else {
//                HttpMethod.Toast(context, "存储失败");
//            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.e(TAG, "[MyReceiver] click notify");

            JPushInterface.reportNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MSG_ID));
            boolean isOpen = PreferencesUtils.getBoolean(context, Constant.IS_OPEN);
            Intent i = new Intent();
            // 打开自定义的Activity
            if (!isOpen)
            {
                i.setClass(context, MainActivity.class);
            }else {
                i.setClass(context, WebviewActivity.class);
                String url = PreferencesUtils.getString(context, Constant.URL_SAVE);
                intent.putExtra("link", url);//"http://www.shishizhong888.com"
                intent.putExtra("title", context.getResources().getString(R.string.app_name));
            }
//            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtil.e(TAG, "[MyReceiver] RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.e(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            LogUtil.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            }
            else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
//    private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
//    }
}
