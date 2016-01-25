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
		 * 请不要添加key参数.
		 */
//		Parameters params = new Parameters();
//		params.add(name, value);
//		params.add("dtype", dtype);
		/**
		 * 请求的方法 参数: 第一个参数 当前请求的context 第二个参数 接口id 第三二个参数 接口请求的url 第四个参数 接口请求的方式
		 * 第五个参数 接口请求的参数,键值对com.thinkland.sdk.android.Parameters类型; 第六个参数
		 * 请求的回调方法,com.thinkland.sdk.android.DataCallBack;
		 * 
		 */
		try {
			JuheData.executeWithAPI(context, id, url,
					method, params, new DataCallBack() {
						/**
						 * 请求成功时调用的方法 statusCode为http状态码,responseString为请求返回数据.
						 * @return 
						 */
						@Override
						public void onSuccess(int statusCode, String responseString) {
							// TODO Auto-generated method stub
							result.append(responseString + "\n");
							handler.sendMessage(handler.obtainMessage(okwhat, result.toString()));
						}

						/**
						 * 请求完成时调用的方法,无论成功或者失败都会调用.
						 */
						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
						}

						/**
						 * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
						 * statusCode:30002 没有检测到当前网络. 30003 没有进行初始化. 0
						 * 未明异常,具体查看Throwable信息. 其他异常请参照http状态码.
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
