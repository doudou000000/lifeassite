package com.risesoft.lifeassite.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {

	public static ProgressDialog getProgress(Context context,String str) {
		ProgressDialog mpDialog = new ProgressDialog(context);  
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条    
		mpDialog.setMessage(str);   
		mpDialog.setIndeterminate(false);//设置进度条是否为不明确   
		mpDialog.setCancelable(true);//设置进度条是否可以按退回键取消 
		//设置点击进度对话框外的区域对话框不消失 
		mpDialog.setCanceledOnTouchOutside(false);
		return mpDialog;
	}
}
