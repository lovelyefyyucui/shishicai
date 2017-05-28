package com.shishicai.app.service;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

public class HttpUser {
	 private static     AsyncHttpClient client =new AsyncHttpClient();
//	private static  SyncHttpClient client = new SyncHttpClient(); //实例化对象
	    static
	    {
	        client.setTimeout(11000);   //设置链接超时，如果不设置，默认为10s
	    }
	    public static void get(String urlString,AsyncHttpResponseHandler res)    //用一个完整url获取一个string对象
	    {
	        client.get(urlString, res);
	    }
	    public static void get(String urlString,RequestParams params,AsyncHttpResponseHandler res)   //url里面带参数
	    {
	        client.get(urlString, params,res);
	    }
	    public static void get(String urlString,JsonHttpResponseHandler res)   //不带参数，获取json对象或者数组
	    {
	        client.get(urlString, res);
	    }
	    public static void get(String urlString,RequestParams params,JsonHttpResponseHandler res)   //带参数，获取json对象或者数组
	    {
	        client.get(urlString, params,res);
	    }
	    public static void get(String uString, BinaryHttpResponseHandler bHandler)   //下载数据使用，会返回byte数据
	    {
	        client.get(uString, bHandler);
	    }
	    public static AsyncHttpClient getClient()
	    {
	        return client;
	    }
	    
	    public static void post(String urlString,RequestParams params,JsonHttpResponseHandler res){
	    	client.post(urlString, params, res);
	    }
	    
	    public static void post(String urlString,RequestParams params,AsyncHttpResponseHandler res){
	    	client.post(urlString, params, res);
	    }

	public static void cancel(Context context){
		client.cancelRequests(context, true);
	}
}
