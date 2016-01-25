package com.risesoft.lifeassite.entity.express;

import java.io.Serializable;

import com.risesoft.lifeassite.entity.BaseEntity;

public class Express extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ExpressResult result;

	public ExpressResult getResult() {
		return result;
	}

	public void setResult(ExpressResult result) {
		this.result = result;
	}	
}
