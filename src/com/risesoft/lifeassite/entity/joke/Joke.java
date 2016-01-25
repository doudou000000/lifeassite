package com.risesoft.lifeassite.entity.joke;

import java.io.Serializable;

import com.risesoft.lifeassite.entity.BaseEntity;

public class Joke extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JokeResult result;

	public JokeResult getResult() {
		return result;
	}

	public void setResult(JokeResult result) {
		this.result = result;
	}
	
}
