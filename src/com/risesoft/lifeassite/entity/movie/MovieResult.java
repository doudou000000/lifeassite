package com.risesoft.lifeassite.entity.movie;

import java.io.Serializable;
import java.util.List;

public class MovieResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	
	private List<MovieAllData> data;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MovieAllData> getData() {
		return data;
	}

	public void setData(List<MovieAllData> data) {
		this.data = data;
	}
	
}
