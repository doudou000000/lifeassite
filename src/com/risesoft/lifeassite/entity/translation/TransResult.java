package com.risesoft.lifeassite.entity.translation;

import java.io.Serializable;

public class TransResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TransResultData data;

	public TransResultData getData() {
		return data;
	}

	public void setData(TransResultData data) {
		this.data = data;
	}
	
}
