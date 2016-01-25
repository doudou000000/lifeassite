package com.risesoft.lifeassite.entity.city;

import java.io.Serializable;
import java.util.List;

import com.risesoft.lifeassite.entity.BaseEntity;

public class City extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CityResultData> result;

	public List<CityResultData> getResult() {
		return result;
	}

	public void setResult(List<CityResultData> result) {
		this.result = result;
	}

}
