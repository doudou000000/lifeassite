package com.risesoft.lifeassite.entity.movie;

import java.io.Serializable;

import com.risesoft.lifeassite.entity.BaseEntity;

public class Movie extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MovieResult result;

	public MovieResult getResult() {
		return result;
	}

	public void setResult(MovieResult result) {
		this.result = result;
	}
	
}
