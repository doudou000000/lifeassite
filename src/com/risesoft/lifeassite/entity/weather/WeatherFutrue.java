package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;
import java.util.List;

public class WeatherFutrue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	
	private String week;
	
	private WeatherFutrueInfo info;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public WeatherFutrueInfo getInfo() {
		return info;
	}

	public void setInfo(WeatherFutrueInfo info) {
		this.info = info;
	}
	
}
