package com.risesoft.lifeassite.entity.translation;

import java.io.Serializable;

import com.risesoft.lifeassite.entity.BaseEntity;

public class TransLation extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	TransResult result;

	public TransResult getResult() {
		return result;
	}

	public void setResult(TransResult result) {
		this.result = result;
	}

}
