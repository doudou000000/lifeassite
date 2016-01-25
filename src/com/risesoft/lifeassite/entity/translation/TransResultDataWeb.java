package com.risesoft.lifeassite.entity.translation;

import java.io.Serializable;
import java.util.List;

public class TransResultDataWeb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
				
	List<String> value;
	
	String key;

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
