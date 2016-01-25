package com.risesoft.lifeassite.entity.dictionary;

import java.io.Serializable;

import com.risesoft.lifeassite.entity.BaseEntity;

public class Dictionary extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private HanZiDictionary result;

	public HanZiDictionary getResult() {
		return result;
	}

	public void setResult(HanZiDictionary result) {
		this.result = result;
	}	
}
