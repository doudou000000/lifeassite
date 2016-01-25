package com.risesoft.lifeassite.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {

	public static String getDate(int time){  
		long hours = time/ (60 * 60);  
	    long minutes = (time % (60 * 60)) / (60);  
	    if(hours!=0){
	    	return hours + "Ê±" + minutes + "·Ö";  	    	
	    }else{	    	
	    	return minutes + "·Ö";  	    	
	    }
	} 

	public static String getDistance(int distance){  

		String dis=null;
		
        if(distance>0){
        	
        	if((distance/1000)>0&&(distance%1000)==0){
        		
        		dis=(distance/1000)+"km";
        		
        	}else if((distance/1000)==0&&(distance%1000)>=0){
        		dis=(distance%1000)+"m";
        	}else if((distance/1000)>0&&(distance%1000)>0){
        		 BigDecimal a = BigDecimal.valueOf(distance).divide(BigDecimal.valueOf(1000),1,BigDecimal.ROUND_HALF_UP);
        		 dis=a.toString()+"km";
        	}
        	
        }

	    return  dis;
	} 
	
}
