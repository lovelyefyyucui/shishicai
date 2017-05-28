package com.shishicai.app.domain;

public class Province {

	private int id;
	
	private String region_id;
	
	private String name;

	public Province(String region_id, String name) {
		super();
		this.region_id = region_id;
		this.name = name;
	}

	public Province(int id,String region_id,String name) {
		super();
		this.id = id;
		this.region_id = region_id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
