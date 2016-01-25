package com.risesoft.lifeassite.view.morefragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.HistoryTodayAdapter;
import com.risesoft.lifeassite.entity.history.HistoryToday;
import com.risesoft.lifeassite.entity.history.HistoryTodayResult;
import com.risesoft.lifeassite.util.JuHeRequest;
import com.risesoft.lifeassite.util.OpenNetWork;
import com.risesoft.lifeassite.util.ProgressDialogUtil;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HistoryTodayActivity extends Activity {

	List<HistoryTodayResult> historyTodays;
	HistoryTodayAdapter historyTodayAdapter;
	ListView historyListView;
	SharedPreferences preferences;
	Editor editor;
	String month;
	String today;
	boolean IfListNull=true;
	RelativeLayout todayNoNetWorkRl;
	ImageButton backIb;
	Button refereceBtn;	
	ProgressDialog dialog;
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			try {
				switch (msg.what) {
				case 0:
					String historyResult=msg.obj.toString();
					HistoryToday historyToday=JSON.parseObject(historyResult, HistoryToday.class);	
					historyTodays=historyToday.getResult();
					if(historyTodays!=null){
						historyTodayAdapter.getHistoryData(historyTodays);
						historyTodayAdapter.notifyDataSetChanged();
			            editor.putString("today", today);	
			            editor.putInt("size", historyTodays.size());	
			            for(int i=0;i<historyTodays.size();i++){
			                editor.putString("title"+i, historyTodays.get(i).getTitle());	
			                editor.putString("content"+i, historyTodays.get(i).getDes());	
			                editor.putString("year"+i, historyTodays.get(i).getYear());
			                editor.putString("month"+i, historyTodays.get(i).getMonth());
			                editor.putString("day"+i, historyTodays.get(i).getDay());
			            }	
			            editor.commit();
			            dialog.dismiss();
					}else{
						Toast.makeText(HistoryTodayActivity.this, "数据加载失败，请检查网络！！！", 1000).show();
					}

					break;
				case -1:
					todayNoNetWorkRl.setVisibility(View.VISIBLE);
					historyListView.setVisibility(View.GONE);
					break;
				default:
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去除自带title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.history_today_layout);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initListener() {
		// TODO Auto-generated method stub
		backIb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HistoryTodayActivity.this.finish();
			}
		});
		refereceBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getTodayData();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		preferences=getPreferences(5);
		editor=preferences.edit();
		Date date=new Date();
		SimpleDateFormat monthDateFormat=new SimpleDateFormat("MM/dd");
		String dateStr=monthDateFormat.format(date);
		month=dateStr.split("/")[0];
		today=dateStr.split("/")[1];
		refereceBtn=(Button)this.findViewById(R.id.no_net_loading_btn);
		backIb=(ImageButton)this.findViewById(R.id.history_today_back_ib);
		todayNoNetWorkRl=(RelativeLayout)this.findViewById(R.id.no_net_work_rl);
		historyListView=(ListView)this.findViewById(R.id.history_today_list_view);
		historyTodays=new ArrayList<HistoryTodayResult>();
		historyTodayAdapter=new HistoryTodayAdapter(this,historyTodays);
		historyListView.setAdapter(historyTodayAdapter);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			getTodayData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getTodayData() {
		// TODO Auto-generated method stub

		if(OpenNetWork.getConn()){

			todayNoNetWorkRl.setVisibility(View.GONE);
			historyListView.setVisibility(View.VISIBLE);
			String saveDay=preferences.getString("today", null);
			int size=preferences.getInt("size", 0);
			if(saveDay==null){
				SendRequstToJuHe();
			}else{
		        if(!(saveDay.equals(today))){
		        	editor.clear();
		        	editor.commit();
		        	SendRequstToJuHe();
		        }else{
		        	List<HistoryTodayResult> newHistoryTodayResults=new ArrayList<HistoryTodayResult>();
		        	for(int i=0;i<size;i++){
		        		HistoryTodayResult saveHistoryTodayResult=new HistoryTodayResult();
		        		saveHistoryTodayResult.setTitle(preferences.getString("title"+i, null));
		        		saveHistoryTodayResult.setDes(preferences.getString("content"+i, null));
		        		saveHistoryTodayResult.setYear(preferences.getString("year"+i, null));
		        		saveHistoryTodayResult.setMonth(preferences.getString("month"+i, null));
		        		saveHistoryTodayResult.setDay(preferences.getString("day"+i, null));
		        		newHistoryTodayResults.add(saveHistoryTodayResult);
		            }
					historyTodayAdapter.getHistoryData(newHistoryTodayResults);
					historyTodayAdapter.notifyDataSetChanged();
		        }
			}
		}else{
			todayNoNetWorkRl.setVisibility(View.VISIBLE);
			historyListView.setVisibility(View.GONE);
		}
		
	}

	private void SendRequstToJuHe() {
		// TODO Auto-generated method stub
		try {
			dialog=ProgressDialogUtil.getProgress(HistoryTodayActivity.this, "加载中...");
			dialog.show();
			Parameters params=new Parameters();
			params.add("v", "1.0");
			params.add("month", month);
			params.add("day", today);
			JuHeRequest.getJuHeData(params, this, 63, "http://api.juheapi.com/japi/toh", JuheData.GET, handler, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}
	
}
