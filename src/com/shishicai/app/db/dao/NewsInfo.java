package com.shishicai.app.db.dao;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
* 资讯消息
*/
public class NewsInfo extends DataSupport{

	private String title;
	private String summary;
	private String logofile;
	private String url;
	private String publishdate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLogofile() {
		return logofile;
	}

	public void setLogofile(String logofile) {
		this.logofile = logofile;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(String publishdate) {
		this.publishdate = publishdate;
	}
}

