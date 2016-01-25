package com.risesoft.lifeassite.entity.news;

import java.util.List;

import com.risesoft.lifeassite.entity.BaseEntity;

public class RealTimeNews extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<String> result;

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}
	
}
