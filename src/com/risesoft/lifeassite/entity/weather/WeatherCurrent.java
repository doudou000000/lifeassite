package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;

public class WeatherCurrent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String humidity;
	
	private String info;
	
	private String temperature;

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
}
