package com.shishicai.app.domain;

public class News {

	private String id;

	private String bTop;
	
	private String title;

	private String author;

	private String time;

	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getbTop()
	{
		return bTop;
	}

	public void setbTop(String bTop)
	{
		this.bTop = bTop;
	}
	
	
	
}
