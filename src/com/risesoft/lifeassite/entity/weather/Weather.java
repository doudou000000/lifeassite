package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;

import com.risesoft.lifeassite.entity.BaseEntity;

public class Weather extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WeatherResult result;

	public WeatherResult getResult() {
		return result;
	}

	public void setResult(WeatherResult result) {
		this.result = result;
	}
	
}
