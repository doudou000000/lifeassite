package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;

public class PmDescribe implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String quality;
	
	private String des;

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
		
}
