package com.risesoft.lifeassite.entity.movie;

import java.io.Serializable;

public class MovieInfoData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String grade;
	
	private String iconaddress;
	
	private String iconlinkUrl;
	
	private MovieMore more;
	
	private String tvTitle;
	
	private MovieStory story;
	
	
	public String getIconlinkUrl() {
		return iconlinkUrl;
	}

	public void setIconlinkUrl(String iconlinkUrl) {
		this.iconlinkUrl = iconlinkUrl;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getIconaddress() {
		return iconaddress;
	}

	public void setIconaddress(String iconaddress) {
		this.iconaddress = iconaddress;
	}

	public MovieMore getMore() {
		return more;
	}

	public void setMore(MovieMore more) {
		this.more = more;
	}

	public String getTvTitle() {
		return tvTitle;
	}

	public void setTvTitle(String tvTitle) {
		this.tvTitle = tvTitle;
	}

	public MovieStory getStory() {
		return story;
	}

	public void setStory(MovieStory story) {
		this.story = story;
	}
	
}
