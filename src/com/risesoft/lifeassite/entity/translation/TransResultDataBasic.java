package com.risesoft.lifeassite.entity.translation;

import java.io.Serializable;
import java.util.List;

public class TransResultDataBasic implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String phonetic;
   
    List<String> explains;

	public String getPhonetic() {
		return phonetic;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	public List<String> getExplains() {
		return explains;
	}

	public void setExplains(List<String> explains) {
		this.explains = explains;
	}
	
    
    
}
