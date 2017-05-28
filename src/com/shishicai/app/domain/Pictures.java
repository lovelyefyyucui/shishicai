package com.shishicai.app.domain;

public class Pictures {

	private String img_id;
	
	private String img_url;
	
	private String thumb_url;

	public Pictures(String img_id, String img_url, String thumb_url) {
		super();
		this.img_id = img_id;
		this.img_url = img_url;
		this.thumb_url = thumb_url;
	}

	public String getImg_id() {
		return img_id;
	}

	public void setImg_id(String img_id) {
		this.img_id = img_id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

}
