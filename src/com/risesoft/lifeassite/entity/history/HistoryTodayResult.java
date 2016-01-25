package com.risesoft.lifeassite.entity.history;

import java.io.Serializable;

public class HistoryTodayResult implements Serializable{

	/**
	 */
	private static final long serialVersionUID = 1L;

	private String day;
	
	private String des;
	
	private String month;
	
	private String pic;
	
	private String title;
	
	private String year;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
