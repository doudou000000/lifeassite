package com.risesoft.lifeassite.entity.weather;

import java.io.Serializable;
import java.util.List;

public class WeatherLiftAdviceInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> kongtiao;
	private List<String> yundong;
	private List<String> ziwaixian;
	private List<String> ganmao;
	private List<String> xiche;
	private List<String> xianxing;
	private List<String> wuran;
	private List<String> chuanyi;
	public List<String> getKongtiao() {
		return kongtiao;
	}
	public void setKongtiao(List<String> kongtiao) {
		this.kongtiao = kongtiao;
	}
	public List<String> getYundong() {
		return yundong;
	}
	public void setYundong(List<String> yundong) {
		this.yundong = yundong;
	}
	public List<String> getZiwaixian() {
		return ziwaixian;
	}
	public void setZiwaixian(List<String> ziwaixian) {
		this.ziwaixian = ziwaixian;
	}
	public List<String> getGanmao() {
		return ganmao;
	}
	public void setGanmao(List<String> ganmao) {
		this.ganmao = ganmao;
	}
	public List<String> getXiche() {
		return xiche;
	}
	public void setXiche(List<String> xiche) {
		this.xiche = xiche;
	}
	public List<String> getXianxing() {
		return xianxing;
	}
	public void setXianxing(List<String> xianxing) {
		this.xianxing = xianxing;
	}
	public List<String> getWuran() {
		return wuran;
	}
	public void setWuran(List<String> wuran) {
		this.wuran = wuran;
	}
	public List<String> getChuanyi() {
		return chuanyi;
	}
	public void setChuanyi(List<String> chuanyi) {
		this.chuanyi = chuanyi;
	}
	
}
