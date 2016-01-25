package com.risesoft.lifeassite.entity.note;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="Classes")
public class Classes implements Serializable{

	/**
	 * photoId varchar(64) not null primary key,url varchar(40)
	 */
	private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
	private String classesId;
    @DatabaseField
	private String name;   
    
	public Classes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    	
}
