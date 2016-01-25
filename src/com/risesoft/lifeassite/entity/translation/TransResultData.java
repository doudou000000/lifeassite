package com.risesoft.lifeassite.entity.translation;

import java.io.Serializable;
import java.util.List;

public class TransResultData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<String> translation;
	
	TransResultDataBasic basic;
	
	String query;
	
	List<TransResultDataWeb> web;

	public List<String> getTranslation() {
		return translation;
	}

	public void setTranslation(List<String> translation) {
		this.translation = translation;
	}

	public TransResultDataBasic getBasic() {
		return basic;
	}

	public void setBasic(TransResultDataBasic basic) {
		this.basic = basic;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<TransResultDataWeb> getWeb() {
		return web;
	}

	public void setWeb(List<TransResultDataWeb> web) {
		this.web = web;
	}

}
