package com.shishicai.app.domain;

public class District {

	private String region_id;
	
	private String city_name;
	
	private String district_region;		//district_region_id
	
	private String name;

	public District(String region_id, String city_name, String district_region, String name) {
		super();
		this.region_id = region_id;
		this.city_name = city_name;
		this.district_region = district_region;
		this.name = name;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getDistrict_region() {
		return district_region;
	}

	public void setDistrict_region(String district_region) {
		this.district_region = district_region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
