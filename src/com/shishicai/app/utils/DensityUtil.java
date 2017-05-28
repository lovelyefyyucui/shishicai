package com.shishicai.app.utils;

import android.content.Context;
/*
 * 屏幕分辨率px跟dip互相转化
 */
public class DensityUtil {

	/*
	 * 将dip转化为px像素
	 */
	public static int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}
	/*
	 * 将px像素转化为dip
	 */
	public static int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}

	public static String xml2JSON(String xml) {
		try {
			XmlToJson xmlToJson = new XmlToJson.Builder(xml).build();
			return xmlToJson.toJson().toString();
		} catch (Exception e) {
			LogUtil.e("xmlToJson", "err=" + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
}
