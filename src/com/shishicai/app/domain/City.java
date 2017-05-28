package com.shishicai.app.domain;

public class City {

	private String region_id;
	
	private String province_name;
	
	private String city_region;		//city_region_id
	
	private String name;

	public City(String region_id, String province_name, String city_region, String name) {
		super();
		this.region_id = region_id;
		this.province_name = province_name;
		this.city_region = city_region;
		this.name = name;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getCity_region() {
		return city_region;
	}

	public void setCity_region(String city_region) {
		this.city_region = city_region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
