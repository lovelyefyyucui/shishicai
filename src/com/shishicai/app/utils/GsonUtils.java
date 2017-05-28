package com.shishicai.app.utils;

import java.lang.reflect.Type;
import com.google.gson.Gson;

/**
 * 使用Gson进行json的转换
 * 
 * @author Lee
 */
public class GsonUtils {

	public static <T> T parseJSON(String json, Class<T> clazz) {
		Gson gson = new Gson();
		T info = gson.fromJson(json, clazz);
		return info;
	}
	
	public static <T> T parseJSON(String json, Type type) {
		Gson gson = new Gson();
		T info = gson.fromJson(json, type);
		return info;
	}
	
	/**
	 * Type type = new 
			TypeToken&lt;ArrayList&lt;TypeInfo>>(){}.getType();
	   <br>Type所在的包：java.lang.reflect
	   <br>TypeToken所在的包：com.google.gson.reflect.TypeToken	
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T parseJSONArray(String jsonArr, Type type) {
		Gson gson = new Gson();
		T infos = gson.fromJson(jsonArr, type);
		return infos;
	}
	
	
	
	private GsonUtils(){}

}
