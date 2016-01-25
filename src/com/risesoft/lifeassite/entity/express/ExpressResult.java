package com.risesoft.lifeassite.entity.express;

import java.io.Serializable;
import java.util.List;

public class ExpressResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	"company":"Ë³·á",
//	"com":"sf",
//	"no":"575677355677",
//	"list":[
	
	String company;
	
	String no;
	
	List<ExpressResultList> list;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public List<ExpressResultList> getList() {
		return list;
	}

	public void setList(List<ExpressResultList> list) {
		this.list = list;
	}
	
}
