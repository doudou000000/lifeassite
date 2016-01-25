package com.risesoft.lifeassite.entity.dictionary;

import java.io.Serializable;

public class HanZiDictionary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private String zi;
	
	private String py;
	
	private String wubi;
	
	private String pinyin;
	
	private String bushou;
	
	private String bihua;
	
	private String jijie;
	
	private String xiangjie;

	public String getZi() {
		return zi;
	}

	public void setZi(String zi) {
		this.zi = zi;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getWubi() {
		return wubi;
	}

	public void setWubi(String wubi) {
		this.wubi = wubi;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getBushou() {
		return bushou;
	}

	public void setBushou(String bushou) {
		this.bushou = bushou;
	}

	public String getBihua() {
		return bihua;
	}

	public void setBihua(String bihua) {
		this.bihua = bihua;
	}

	public String getJijie() {
		return jijie;
	}

	public void setJijie(String jijie) {
		this.jijie = jijie;
	}

	public String getXiangjie() {
		return xiangjie;
	}

	public void setXiangjie(String xiangjie) {
		this.xiangjie = xiangjie;
	}
	
}
