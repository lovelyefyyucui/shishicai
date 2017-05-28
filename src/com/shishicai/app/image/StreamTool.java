package com.shishicai.app.image;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

	public static byte[] readInputStream(InputStream in) throws Exception {
		int len;
		byte buf[] = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len); // 把数据写入内存
		}
		out.close(); // 关闭内存输出流
		return out.toByteArray(); // 把内存输出流转换成byte数组
	}
}
