/*
 * Base_Model.java
 * @author Andrew Lee
 * 2014-10-20 上午11:02:04
 */
package com.shishicai.app.domain;

import java.io.Serializable;

/**
 * Base_Model.java description:
 * 
 * @author Andrew Lee version 2014-10-20 上午11:02:04
 */
public class BaseModel<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3676969309433142938L;
	private int code;
	private String url;
	private T msg;
	private int status;

	public BaseModel() {
		super();
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public T getMsg()
	{
		return msg;
	}

	public void setMsg(T msg)
	{
		this.msg = msg;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		if (this.getStatus() == 1) {

			return true;

		} else {

			return false;

		}
	}

}
