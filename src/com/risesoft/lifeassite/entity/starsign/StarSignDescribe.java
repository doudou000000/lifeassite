package com.risesoft.lifeassite.entity.starsign;

import java.io.Serializable;
import java.util.List;

public class StarSignDescribe implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String info;
	
	private List<String> text;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(List<String> text) {
		this.text = text;
	}
}
