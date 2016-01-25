package com.risesoft.lifeassite.entity.starsign;

import java.io.Serializable;

import com.risesoft.lifeassite.entity.BaseEntity;

public class MonthSatrSign extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String date;
	private String all;
	
	private String health;

	private String love;
	
	private String money;
	
	private String work;
	
	private String resultcode;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getLove() {
		return love;
	}

	public void setLove(String love) {
		this.love = love;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

}
