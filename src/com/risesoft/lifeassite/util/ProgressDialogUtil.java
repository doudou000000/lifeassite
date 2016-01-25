package com.risesoft.lifeassite.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {

	public static ProgressDialog getProgress(Context context,String str) {
		ProgressDialog mpDialog = new ProgressDialog(context);  
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//���÷��ΪԲ�ν�����    
		mpDialog.setMessage(str);   
		mpDialog.setIndeterminate(false);//���ý������Ƿ�Ϊ����ȷ   
		mpDialog.setCancelable(true);//���ý������Ƿ���԰��˻ؼ�ȡ�� 
		//���õ�����ȶԻ����������Ի�����ʧ 
		mpDialog.setCanceledOnTouchOutside(false);
		return mpDialog;
	}
}
