package com.risesoft.lifeassite.view.lifefragment.express;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.ExpressListAdapter;
import com.risesoft.lifeassite.entity.express.Express;
import com.risesoft.lifeassite.entity.express.ExpressResultList;
import com.risesoft.lifeassite.util.JuHeRequest;
import com.risesoft.lifeassite.util.OpenNetWork;
import com.risesoft.lifeassite.util.PreferencesSaveList;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ExpressContentActivity extends Activity implements OnClickListener{

	PullToRefreshListView expressListView;
	ExpressListAdapter expressListAdapter;
	List<ExpressResultList> expressResultLists=new ArrayList<ExpressResultList>();
	String companyName;
	String expressNo;
	List<Integer> resourceIds=new ArrayList<Integer>();
	SharedPreferences expressPreferences;
	Editor expressEditor;
	RelativeLayout ecpressNoNetWorkRl;
	String expressData;
	Button noNetBtn;
	//ImageButton backBtn;
	LinearLayout backLl;
	boolean isAutoRefresh=true;
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			try {
				switch (msg.what) {
				case 0:
					String expressResult=msg.obj.toString();
					if(expressResult!=null){
						Express express=JSON.parseObject(expressResult, Express.class);
						ecpressNoNetWorkRl.setVisibility(View.GONE);
						expressListView.setVisibility(View.VISIBLE);
						if(express.getResult()!=null){
							List<ExpressResultList> newExpressResultLists=getExpressList(express);
							refreshView(newExpressResultLists);
							expressEditor.putLong(expressNo+"date", new Date().getTime());
							expressEditor.putString(expressNo, PreferencesSaveList.SceneList2String(newExpressResultLists));
							expressEditor.commit();
							writeExpressNoToFile(expressNo);
						}else{
							Toast.makeText(ExpressContentActivity.this, "没有查询到快件信息,请核对订单号！！！", 500).show();			
						}
					}else{	
						Toast.makeText(ExpressContentActivity.this, "数据查询失败，请检查单号或网络！！！", 500).show();			
					}
					
					break;
				case -1:
					Toast.makeText(ExpressContentActivity.this, "数据查询失败，请检查单号或网络！！！", 500).show();		
					break;
				default:
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			expressListView.postDelayed(new Runnable() {
				@Override
				public void run() {
					expressListView.onRefreshComplete();
				}
			}, 1000);
		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去除自带title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.express_time_list);
		try {
			initView();	
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	   public void writeExpressNoToFile(String expressNo){ 
		   expressNo=expressNo+",";
	       try{ 

	        FileOutputStream fout =openFileOutput("express.txt", MODE_APPEND);

	        byte [] bytes = expressNo.getBytes(); 

	        fout.write(bytes); 

	         fout.close(); 

	        } 

	       catch(Exception e){ 

	        e.printStackTrace(); 

	       } 

	   } 


	protected void refreshView(List<ExpressResultList> newExpressResultLists) {
		// TODO Auto-generated method stub
		resourceIds=new ArrayList<Integer>();
		for(int i=0;i<newExpressResultLists.size();i++){
			if(i==0){
				resourceIds.add(R.drawable.timeline_content_red);
			}else{						
				resourceIds.add(R.drawable.timeline_content);
			}
		}
		expressListAdapter.getExpresses(newExpressResultLists,resourceIds);
		expressListAdapter.notifyDataSetChanged();
	}
	protected void getDataFiailView(List<ExpressResultList> newExpressResultLists) {
		// TODO Auto-generated method stub
		resourceIds=new ArrayList<Integer>();
		if(newExpressResultLists!=null&&newExpressResultLists.size()>0){
			for(int i=0;i<newExpressResultLists.size();i++){
				if(i==0){
					resourceIds.add(R.drawable.timeline_content_red);
				}else{						
					resourceIds.add(R.drawable.timeline_content);
				}
			}
			expressListAdapter.getExpresses(newExpressResultLists,resourceIds);
			expressListAdapter.notifyDataSetChanged();
		}else{
			if(!OpenNetWork.getConn()){
				ecpressNoNetWorkRl.setVisibility(View.VISIBLE);
				expressListView.setVisibility(View.GONE);
			}else{
				Toast.makeText(ExpressContentActivity.this, "数据查询失败，请检查单号或网络！！！", 500).show();
			}
		}
		expressListView.postDelayed(new Runnable() {
			@Override
			public void run() {
				expressListView.onRefreshComplete();
			}
		}, 500);
	}
	private void initView() {
		// TODO Auto-generated method stub
		expressPreferences=getSharedPreferences("expressId", 12);
		expressEditor=expressPreferences.edit();
		companyName=getIntent().getStringExtra("companyName");
		expressNo=getIntent().getStringExtra("expressNo");
		
		backLl=(LinearLayout)findViewById(R.id.express_content_list_back_ll);
		noNetBtn=(Button)findViewById(R.id.no_net_loading_btn);
		//backBtn=(ImageButton)findViewById(R.id.express_content_list_ib);
		ecpressNoNetWorkRl=(RelativeLayout)findViewById(R.id.no_net_work_rl);
		expressListView=(PullToRefreshListView)findViewById(R.id.express_list_view);
		expressListView.setMode(Mode.PULL_FROM_START);
		expressListAdapter=new ExpressListAdapter(expressResultLists,resourceIds,this);
		expressListView.setAdapter(expressListAdapter);
	}
	
	private void initListener() {
		// TODO Auto-generated method stub
		noNetBtn.setOnClickListener(this);
		backLl.setOnClickListener(this);
		expressListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				try {
					refreshExpressData();				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			if(isAutoRefresh){
				expressListView.postDelayed(new Runnable() {
					@Override
					public void run() {
						expressListView.setRefreshing(true);
					}
				}, 500);
			}else{
				getDataFiailView(PreferencesSaveList.String2SceneList(expressPreferences.getString(expressNo, null)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	protected void refreshExpressData() {
		// TODO Auto-generated method stub
		Long currentDate=new Date().getTime();
		Long saveDate=expressPreferences.getLong(expressNo+"date", -1);
		expressData=expressPreferences.getString(expressNo, null);

		try {
			if(saveDate!=-1&&expressData!=null){
				Long timeDifference=currentDate-saveDate;
				if(timeDifference>14400000){
					getExpressData();
				}else{
					refreshView(PreferencesSaveList.String2SceneList(expressData));
					expressListView.postDelayed(new Runnable() {
						@Override
						public void run() {
							expressListView.onRefreshComplete();
						}
					}, 500);
					if(!OpenNetWork.getConn()){
						Toast.makeText(ExpressContentActivity.this, "没有检测到网络连接", 500).show();
					}
				}
			}else{
				getExpressData();
			}
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		isAutoRefresh=false;
	}
	private void getExpressData() {
		// TODO Auto-generated method stub
		if(OpenNetWork.getConn()){
			ecpressNoNetWorkRl.setVisibility(View.GONE);
			expressListView.setVisibility(View.VISIBLE);
			Parameters params = new Parameters();
			params.add("com", companyName);
			params.add("no", expressNo.substring(0, expressNo.indexOf("(")));
			params.add("dtype", "json");
			JuHeRequest.getJuHeData(params,ExpressContentActivity.this, 43, "http://v.juhe.cn/exp/index", JuheData.GET, handler, 0);
		}else{
			ecpressNoNetWorkRl.setVisibility(View.VISIBLE);
			expressListView.setVisibility(View.GONE);
		}
	}

	private List<ExpressResultList> getExpressList(Express express) {
		List<ExpressResultList> newExpressResultLists=express.getResult().getList();
		Collections.reverse(newExpressResultLists);
		return newExpressResultLists;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.no_net_loading_btn:
			getExpressData();
			break;
		case R.id.express_content_list_back_ll:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}
	
}
