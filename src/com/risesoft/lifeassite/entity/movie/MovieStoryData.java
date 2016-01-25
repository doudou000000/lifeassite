package com.risesoft.lifeassite.entity.movie;

import java.io.Serializable;

public class MovieStoryData implements Serializable{

	/**
	 * "showname":"����",
							"data":{
								"storyBrief":"������90������̽Ĺ���ֺ���һ��������ϴ�֡�����δ����Shirley���ƾ�����������ǰ������20��..",
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
