package com.risesoft.lifeassite.entity.news;

import java.io.Serializable;

public class NewsResult implements Serializable{

	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String title;
	
	private String content;
	
	private String pdate;
	
	private String img;
	
	private String url;
	
	private String full_title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFull_title() {
		return full_title;
	}

	public void setFull_title(String full_title) {
		this.full_title = full_title;
	}
	

}
