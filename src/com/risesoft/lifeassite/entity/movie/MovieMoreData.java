package com.risesoft.lifeassite.entity.movie;

import java.io.Serializable;

public class MovieMoreData implements Serializable{

	/**
	 * 						{
									"name":"Ñ¡×ù¹ºÆ±",
									"link":"http:\/\/theater.mtime.com\/China_Beijing\/movie\/196282\/"
								},

	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String link;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
