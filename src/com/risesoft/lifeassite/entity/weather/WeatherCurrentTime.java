package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;

public class WeatherCurrentTime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WeatherCurrentWind wind;
	
	private WeatherCurrent weather;
	
	private String week;

	public WeatherCurrentWind getWind() {
		return wind;
	}

	public void setWind(WeatherCurrentWind wind) {
		this.wind = wind;
	}

	public WeatherCurrent getWeather() {
		return weather;
	}

	public void setWeather(WeatherCurrent weather) {
		this.weather = weather;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	
}
