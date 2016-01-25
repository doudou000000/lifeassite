package com.risesoft.lifeassite.entity.city;

import java.io.Serializable;

public class CityResultData implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String province;
	
	private String city;
	
	private String district;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
}
