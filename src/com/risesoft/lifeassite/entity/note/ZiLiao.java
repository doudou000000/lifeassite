package com.risesoft.lifeassite.entity.note;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="ZiLiao")
public class ZiLiao {

	@DatabaseField(id=true,canBeNull=false,columnName="uuid")	
	private String uuid;
	@DatabaseField(canBeNull=false,columnName="title")	
	private String title;
	@DatabaseField(canBeNull=false,columnName="desc")		
	private String desc;
	@DatabaseField(canBeNull=false,columnName="time")		
	private String time;
//	@DatabaseField(columnName="modes")	
//	private String modes;
	@ForeignCollectionField
	private ForeignCollection<Photo> photos;

	@DatabaseField(foreign=true, canBeNull=false,foreignAutoRefresh=true)		
	private Classes classes;
	
	public ZiLiao(String uuid) {
		super();
		this.uuid = uuid;
	}

	public ZiLiao(String uuid,String time) {
		super();
		this.uuid = uuid;
		this.time = time;
	}

	public ZiLiao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

//	public String getModes() {
//		return modes;
//	}
//
//	public void setModes(String modes) {
//		this.modes = modes;
//	}

	public ForeignCollection<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(ForeignCollection<Photo> photos) {
		this.photos = photos;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
		
	
	
}
