package com.shishicai.app.domain;


public class LoginEntity extends ReturnInfo{
	private String userId;
	private String session_id;
	
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getSession_id()
	{
		return session_id;
	}
	public void setSession_id(String session_id)
	{
		this.session_id = session_id;
	}
	
	
}
