package com.risesoft.lifeassite.entity.history;

import java.io.Serializable;
import java.util.List;

import com.risesoft.lifeassite.entity.BaseEntity;

public class HistoryToday extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<HistoryTodayResult> result;

	public List<HistoryTodayResult> getResult() {
		return result;
	}

	public void setResult(List<HistoryTodayResult> result) {
		this.result = result;
	}
	
	

}
