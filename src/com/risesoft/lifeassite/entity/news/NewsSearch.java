package com.risesoft.lifeassite.entity.news;

import java.util.List;

import com.risesoft.lifeassite.entity.BaseEntity;

public class NewsSearch extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<NewsResult> result;

	public List<NewsResult> getResult() {
		return result;
	}

	public void setResult(List<NewsResult> result) {
		this.result = result;
	}
	
}
