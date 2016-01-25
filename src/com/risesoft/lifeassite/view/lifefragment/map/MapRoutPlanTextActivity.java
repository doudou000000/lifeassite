package com.risesoft.lifeassite.view.lifefragment.map;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.core.RouteLine;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.MapRoutPlanTextListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;


public class MapRoutPlanTextActivity extends Activity implements OnClickListener{

	ListView mapRoutPlanTextListView;
	MapRoutPlanTextListAdapter mapRoutPlanTextListAdapter;
	List<String> mapRoutPlanTextLists=new ArrayList<String>();
	String title;
	String time;
	String distence;
	String routePlan;
	RouteLine route = null;

	TextView titleTv,timeTv,disTv,mapTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去除自带title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.map_route_plan_text_layout);
		try {
			initView();	
			initLisener();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}




	private void initView() {
		// TODO Auto-generated method stub

		routePlan=getIntent().getStringExtra("routePlan");
		title=getIntent().getStringExtra("route_plan_title");
		time=getIntent().getStringExtra("route_plan_time");
		distence=getIntent().getStringExtra("route_plan_dis");
		mapRoutPlanTextLists=getIntent().getStringArrayListExtra("mapRoutPlanTextList");
			
		titleTv=(TextView)findViewById(R.id.map_route_plan_text_title_tv);
		timeTv=(TextView)findViewById(R.id.map_route_plan__text_dur_tv);
		disTv=(TextView)findViewById(R.id.map_route_plan__text_dic_tv);
		mapTv=(TextView)findViewById(R.id.map_route_plan_text_map_tv);
		
		mapRoutPlanTextListView=(ListView)findViewById(R.id.map_route_plan_list__text_lv);

		mapRoutPlanTextListAdapter=new MapRoutPlanTextListAdapter(this,mapRoutPlanTextLists);
		mapRoutPlanTextListView.setAdapter(mapRoutPlanTextListAdapter);
		titleTv.setText(title);
		timeTv.setText(time);
		disTv.setText(distence);
		mapTv.setText("进入地图查看");
	}

	private void initLisener() {
		// TODO Auto-generated method stub
		mapTv.setOnClickListener(this);
	}




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.map_route_plan_text_map_tv:
			Intent intent=new Intent(this, MapRoutePlanMapActivity.class);
			intent.putExtra("route", getIntent().getIntExtra("route",-1));
			intent.putExtra("st", getIntent().getStringExtra("st"));
			intent.putExtra("en", getIntent().getStringExtra("en"));
			intent.putExtra("city", getIntent().getStringExtra("city"));
			intent.putExtra("routePlan", routePlan);
			this.startActivity(intent);
			break;

		default:
			break;
		}
	}	
	
	
}
