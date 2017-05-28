package com.shishicai.app.domain;

import java.io.Serializable;

/**
 * 课程列表
 */
public class Lesson implements Serializable{

	private static final long serialVersionUID = -5923792855015581676L;
	
	private String id;
	private String cate_id;
	private String group_id;
	private String course_name;
	private String course_type;
	private String course_text;
	private String app_pic;
	private String sort;
	private String ifshow;
	private String company_id;

	//..
	private String favorite;
	private String iflearning;
	private String examlock;
	private String lock;
	private String lastreadtime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCate_id() {
		return cate_id;
	}

	public void setCate_id(String cate_id) {
		this.cate_id = cate_id;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_type() {
		return course_type;
	}

	public void setCourse_type(String course_type) {
		this.course_type = course_type;
	}

	public String getCourse_text() {
		return course_text;
	}

	public void setCourse_text(String course_text) {
		this.course_text = course_text;
	}

	public String getApp_pic() {
		return app_pic;
	}

	public void setApp_pic(String app_pic) {
		this.app_pic = app_pic;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getIfshow() {
		return ifshow;
	}

	public void setIfshow(String ifshow) {
		this.ifshow = ifshow;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getIflearning() {
		return iflearning;
	}

	public void setIflearning(String iflearning) {
		this.iflearning = iflearning;
	}

	public String getExamlock() {
		return examlock;
	}

	public void setExamlock(String examlock) {
		this.examlock = examlock;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public String getLastreadtime() {
		return lastreadtime;
	}

	public void setLastreadtime(String lastreadtime) {
		this.lastreadtime = lastreadtime;
	}

}
