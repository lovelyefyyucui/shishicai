package com.shishicai.app.image;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebImage {
	public static byte[] getImage(String path) {

		URL url;
		byte[] b = null;
		try {
			url = new URL(path); // 设置URL
			HttpURLConnection con;

			con = (HttpURLConnection) url.openConnection(); // 打开连接

			con.setRequestMethod("GET"); // 设置请求方法
			// 设置连接超时时间为5s
			con.setConnectTimeout(5000);
			InputStream in = con.getInputStream(); // 取得字节输入流

			b = StreamTool.readInputStream(in);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b; // 返回byte数组

	}

}