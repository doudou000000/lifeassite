package com.risesoft.lifeassite.entity.movie;

import java.io.Serializable;

public class MovieStoryData implements Serializable{

	/**
	 * "showname":"剧情",
							"data":{
								"storyBrief":"上世纪90代初，探墓高手胡八一决定金盆洗手。他与未婚妻Shirley杨移居美国，婚礼前，发现20年..",
								"storyMoreLink":"http:\/\/movie.mtime.com\/196282\/plots.html"
							}
	 */
	private static final long serialVersionUID = 1L;

	private String storyBrief;

	public String getStoryBrief() {
		return storyBrief;
	}

	public void setStoryBrief(String storyBrief) {
		this.storyBrief = storyBrief;
	}
	
}
