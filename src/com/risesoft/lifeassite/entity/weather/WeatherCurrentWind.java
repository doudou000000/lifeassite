package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;

public class WeatherCurrentWind implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String direct;
	
	private String power;

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}
	
}
