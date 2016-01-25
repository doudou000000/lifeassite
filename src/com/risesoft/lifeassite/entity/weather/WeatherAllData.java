package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;
import java.util.List;

public class WeatherAllData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WeatherCurrentTime realtime;
	
	private WeatherLiftAdvice life;
	
	private WeatherPM pm25;
	
	private List<WeatherFutrue> weather;

	public List<WeatherFutrue> getWeather() {
		return weather;
	}

	public void setWeather(List<WeatherFutrue> weather) {
		this.weather = weather;
	}

	public WeatherCurrentTime getRealtime() {
		return realtime;
	}

	public void setRealtime(WeatherCurrentTime realtime) {
		this.realtime = realtime;
	}

	public WeatherLiftAdvice getLife() {
		return life;
	}

	public void setLife(WeatherLiftAdvice life) {
		this.life = life;
	}

	public WeatherPM getPm25() {
		return pm25;
	}

	public void setPm25(WeatherPM pm25) {
		this.pm25 = pm25;
	}

}
