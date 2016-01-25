package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;
import java.util.List;

public class WeatherFutrueInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> night;
	private List<String> day;
	public List<String> getNight() {
		return night;
	}
	public void setNight(List<String> night) {
		this.night = night;
	}
	public List<String> getDay() {
		return day;
	}
	public void setDay(List<String> day) {
		this.day = day;
	}
	
}
