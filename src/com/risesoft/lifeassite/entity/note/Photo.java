package com.risesoft.lifeassite.entity.note;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="Photo")
public class Photo implements Serializable{

	/**
	 * photoId varchar(64) not null primary key,url varchar(40)
	 */
	private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
	private String photoId;
    @DatabaseField
	private String url;    
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private ZiLiao ziLiao;
    
	public Photo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Photo(String photoId, String url) {
		super();
		this.photoId = photoId;
		this.url = url;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ZiLiao getZiLiao() {
		return ziLiao;
	}

	public void setZiLiao(ZiLiao ziLiao) {
		this.ziLiao = ziLiao;
	}
	
	
}
