package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;


public class WeatherPM implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PmDescribe pm25;

	public PmDescribe getPm25() {
		return pm25;
	}

	public void setPm25(PmDescribe pm25) {
		this.pm25 = pm25;
	}
	
	
	
}
