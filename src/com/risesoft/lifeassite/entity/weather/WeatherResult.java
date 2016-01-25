package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;

public class WeatherResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WeatherAllData data;

	public WeatherAllData getData() {
		return data;
	}

	public void setData(WeatherAllData data) {
		this.data = data;
	}
	
	
	
}
