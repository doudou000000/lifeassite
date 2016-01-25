package com.risesoft.lifeassite.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class OpenNetWork {

	public static boolean getConn() {
		// TODO Auto-generated method stub
		boolean bisConnFlag=false;  
	    ConnectivityManager conManager = (ConnectivityManager)MyApp.myApp.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);  
	    NetworkInfo network = conManager.getActiveNetworkInfo();  
	    if(network!=null){  
	        bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();  
	    }  
	    return bisConnFlag;  
	}

	public static void showDialog(final Context context) {
		// TODO Auto-generated method stub
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);  
		    builder.setTitle("联网检查")            //  
		            .setMessage("   没有检测到网络,是否打开？").setPositiveButton("是", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent=null;
							if(android.os.Build.VERSION.SDK_INT>10){//判断当前系统版本3.0以上
								intent=new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
							}else{
								
								intent = new Intent();  
		                        intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings"); 
								
							}
							
							context.startActivity(intent);
							
						}
					}).setNegativeButton("否", null).create().show();  
	}
	
}
