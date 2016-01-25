package com.risesoft.lifeassite.entity.movie;

import java.io.Serializable;

public class MovieStory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MovieStoryData data;

	public MovieStoryData getData() {
		return data;
	}

	public void setData(MovieStoryData data) {
		this.data = data;
	}
	
}
