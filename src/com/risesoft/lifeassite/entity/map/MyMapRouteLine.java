package com.risesoft.lifeassite.entity.map;

import java.io.Serializable;

public class MyMapRouteLine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String duration;
	
	private String distance;
	
	private String title;
	
	private String compare;

	public String getCompare() {
		return compare;
	}

	public void setCompare(String compare) {
		this.compare = compare;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
