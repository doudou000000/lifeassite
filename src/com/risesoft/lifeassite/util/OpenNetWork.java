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
		    builder.setTitle("�������")            //  
		            .setMessage("   û�м�⵽����,�Ƿ�򿪣�").setPositiveButton("��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent=null;
							if(android.os.Build.VERSION.SDK_INT>10){//�жϵ�ǰϵͳ�汾3.0����
								intent=new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
							}else{
								
								intent = new Intent();  
		                        intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings"); 
								
							}
							
							context.startActivity(intent);
							
						}
					}).setNegativeButton("��", null).create().show();  
	}
	
}
