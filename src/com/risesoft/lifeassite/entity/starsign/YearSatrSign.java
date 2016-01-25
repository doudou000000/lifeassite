package com.risesoft.lifeassite.entity.starsign;

import java.io.Serializable;
import java.util.List;

import com.risesoft.lifeassite.entity.BaseEntity;

public class YearSatrSign extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

    private String date;
    
    private StarSignDescribe mima;
    
    private List<String> career;
    
    private List<String> love;
    
    private List<String> health;
    
    private List<String> finance;
    
    private String luckyStone;

    private String resultcode;
    
	public String getResultcode() {
		return resultcode;
	}



	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public StarSignDescribe getMima() {
		return mima;
	}



	public void setMima(StarSignDescribe mima) {
		this.mima = mima;
	}



	public List<String> getCareer() {
		return career;
	}



	public void setCareer(List<String> career) {
		this.career = career;
	}



	public List<String> getLove() {
		return love;
	}



	public void setLove(List<String> love) {
		this.love = love;
	}



	public List<String> getHealth() {
		return health;
	}



	public void setHealth(List<String> health) {
		this.health = health;
	}



	public List<String> getFinance() {
		return finance;
	}



	public void setFinance(List<String> finance) {
		this.finance = finance;
	}



	public String getLuckyStone() {
		return luckyStone;
	}



	public void setLuckyStone(String luckyStone) {
		this.luckyStone = luckyStone;
	}

}
