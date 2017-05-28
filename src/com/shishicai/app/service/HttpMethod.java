package com.shishicai.app.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class HttpMethod {

	private static final String TAG = "HttpMethod";
	private static String SESSION_ID = "ECS_ID";
	public static String sessionValue = "";
	
	public static String PostMethod(String url, List<NameValuePair> params) {
		BufferedReader in = null;
		String result = null;
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 20*1000);
			client.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 20*1000);
 			HttpPost request = new HttpPost(url);
			if(!sessionValue.equals("")){
				request.setHeader("Cookie","ECS_ID="+sessionValue);
			}
			request.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			HttpResponse response = client.execute(request);
			
			CookieStore cookieStore = ((AbstractHttpClient) client).getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for(int i=0,leng=cookies.size(); i<leng; i++)
			{
				  if(SESSION_ID.equals(cookies.get(i).getName()))
				  {
					  sessionValue =  cookies.get(i).getValue();//保持会话
					  Log.e(TAG, "sessionValue = " + sessionValue);
					  break;
				  }
			}
	
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
 			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					Log.e(TAG, ioe.toString());
				}
			}
		}
		return result;
	}

	public static String GetMethod(String url) {
		BufferedReader in = null;
		String result = null;
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 20*1000);
			client.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 20*1000);
			HttpGet request = new HttpGet(url);
			Log.e(TAG, "sessionValue="+sessionValue);
			if(!sessionValue.equals("")){
				request.setHeader("cookie","ECS_ID="+sessionValue);
			}
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"gbk"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			Log.e(TAG,"sessionValue="+sessionValue);
			in.close();
			result = sb.toString(); 
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					Log.e(TAG, ioe.toString());
				}
			}
		}
		return result;
	}
	 
	public static boolean isNetworkConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	public static void Toast(Context context){
		Toast.makeText(context, "亲，网络连接失败！", Toast.LENGTH_SHORT).show();
	}
	
	public static void ToastTimeOut(Context context){
		Toast.makeText(context, "亲，网络不给力，请查看网络！", Toast.LENGTH_SHORT).show();
	}

	public static void Toast(Context context, String tips){
		Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
	}
}
