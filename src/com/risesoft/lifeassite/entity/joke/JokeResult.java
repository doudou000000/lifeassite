package com.risesoft.lifeassite.entity.joke;

import java.io.Serializable;
import java.util.List;

public class JokeResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<JokeResultData> data;

	public List<JokeResultData> getData() {
		return data;
	}

	public void setData(List<JokeResultData> data) {
		this.data = data;
	}
	
}
