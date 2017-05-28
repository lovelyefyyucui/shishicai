package com.shishicai.app.db.dao;

import java.util.Date;

import org.litepal.crud.DataSupport;

/**
* 接收的推送消息
*/
public class MsgInfo extends DataSupport{
	
	private String msgId;
	
	private String title;

	private String content;

	private Date time;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}

