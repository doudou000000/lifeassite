package com.risesoft.lifeassite.entity.express;

import java.io.Serializable;

public class ExpressResultList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	"datetime":"2013-06-25 10:44:05",	/*时间*/
//	"remark":"已收件",					/*描述*/
//	"zone":"台州市"
	
	String datetime;
	
	String remark;
	
	String zone;

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
	
	
	
}
