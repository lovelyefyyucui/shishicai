package com.shishicai.app.domain;

import java.io.Serializable;

public class Comments implements Serializable {

	/**
	 * 评论
	 */
	private static final long serialVersionUID = -8911271145666172244L;

	private String id;

	private String comment_type;

	private String content; // 评论内容

	private String cmt_name;

	// ---------------------------------------//

	private String email;
	private String username;
	private String rank;
	private String add_time;

	public String getComment_type() {
		return comment_type;
	}

	public void setComment_type(String comment_type) {
		this.comment_type = comment_type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCmt_name() {
		return cmt_name;
	}

	public void setCmt_name(String cmt_name) {
		this.cmt_name = cmt_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

}
