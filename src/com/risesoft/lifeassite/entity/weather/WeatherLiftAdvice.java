package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;

public class WeatherLiftAdvice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WeatherLiftAdviceInfo info;

	public WeatherLiftAdviceInfo getInfo() {
		return info;
	}

	public void setInfo(WeatherLiftAdviceInfo info) {
		this.info = info;
	}
	
}
