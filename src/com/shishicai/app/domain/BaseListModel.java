/*
 * Base_Model.java
 * @author Andrew Lee
 * 2014-10-20 上午11:02:04
 */
package com.shishicai.app.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Base_Model.java description:
 * 
 * @author Andrew Lee version 2014-10-20 上午11:02:04
 */
public class BaseListModel<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3676969309433142938L;
	private int code;
	private String url;
	private ArrayList<T> msg;
	private int status;

	public BaseListModel() {
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


	public ArrayList<T> getMsg()
	{
		return msg;
	}

	public void setMsg(ArrayList<T> msg)
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
