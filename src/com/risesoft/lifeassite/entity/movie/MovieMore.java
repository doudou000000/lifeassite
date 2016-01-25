package com.risesoft.lifeassite.entity.movie;

import java.io.Serializable;
import java.util.List;

public class MovieMore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<MovieMoreData> data;

	public List<MovieMoreData> getData() {
		return data;
	}

	public void setData(List<MovieMoreData> data) {
		this.data = data;
	}
	
}
