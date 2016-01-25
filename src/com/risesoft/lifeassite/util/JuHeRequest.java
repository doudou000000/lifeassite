package com.risesoft.lifeassite.util;

import android.content.Context;
import android.os.Handler;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

public class JuHeRequest {

	public static StringBuilder result;
	
	public static void getJuHeData(Parameters params,Context context,int id,String url,String method,final Handler handler,final int okwhat) {
		
		result=new StringBuilder();
		/**
		 * �벻Ҫ���key����.
		 */
//		Parameters params = new Parameters();
//		params.add(name, value);
//		params.add("dtype", dtype);
		/**
		 * ����ķ��� ����: ��һ������ ��ǰ�����context �ڶ������� �ӿ�id ������������ �ӿ������url ���ĸ����� �ӿ�����ķ�ʽ
		 * ��������� �ӿ�����Ĳ���,��ֵ��com.thinkland.sdk.android.Parameters����; ����������
		 * ����Ļص�����,com.thinkland.sdk.android.DataCallBack;
		 * 
		 */
		try {
			JuheData.executeWithAPI(context, id, url,
					method, params, new DataCallBack() {
						/**
						 * ����ɹ�ʱ���õķ��� statusCodeΪhttp״̬��,responseStringΪ���󷵻�����.
						 * @return 
						 */
						@Override
						public void onSuccess(int statusCode, String responseString) {
							// TODO Auto-generated method stub
							result.append(responseString + "\n");
							handler.sendMessage(handler.obtainMessage(okwhat, result.toString()));
						}

						/**
						 * �������ʱ���õķ���,���۳ɹ�����ʧ�ܶ������.
						 */
						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
						}

						/**
						 * ����ʧ��ʱ���õķ���,statusCodeΪhttp״̬��,throwableΪ���񵽵��쳣
						 * statusCode:30002 û�м�⵽��ǰ����. 30003 û�н��г�ʼ��. 0
						 * δ���쳣,����鿴Throwable��Ϣ. �����쳣�����http״̬��.
						 */
						@Override
						public void onFailure(int statusCode,
								String responseString, Throwable throwable) {
							// TODO Auto-generated method stub
							result.append(responseString + "\n");
							handler.sendMessage(handler.obtainMessage(-1, result.toString()));
						}
						
					});
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	
}
