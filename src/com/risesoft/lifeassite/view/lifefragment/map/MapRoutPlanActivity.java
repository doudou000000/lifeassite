package com.risesoft.lifeassite.view.lifefragment.map;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.MapRouteListAdapter;
import com.risesoft.lifeassite.entity.map.MyMapRouteLine;
import com.risesoft.lifeassite.util.MapPlaceData;
import com.risesoft.lifeassite.util.MyApp;
import com.risesoft.lifeassite.util.ProgressDialogUtil;
import com.risesoft.lifeassite.util.StringToDate;

public class MapRoutPlanActivity extends Activity implements
		OnGetRoutePlanResultListener, OnClickListener {
	// 浏览路线节点相关
	RouteLine route = null;

	private static String routePlan;

	ListView mapRouteListView;
	MapRouteListAdapter mapRouteListAdapter;
	List<WalkingRouteLine> walkingRouteLines;
	List<DrivingRouteLine> drivingRouteLines;
	List<TransitRouteLine> transitRouteLines;

	List<MyMapRouteLine> myMapRouteLines;

	List<ArrayList<String>> myMapRouteLineStepLists;

	ProgressDialog dialog;
	
//	ImageView busRouteIv, carRouteIv, walkRouteIv;
	RelativeLayout busRouteRl, carRouteRl, walkRouteRl,backRl;
	// 搜索相关
	RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	String currentcity;

	String currentPlace;

	EditText editSt, editEn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去除自带title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.map_route_plan_layout);

		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		currentcity = getIntent().getStringExtra("city").replace("市", "");
		currentPlace = getIntent().getStringExtra("place");
//		busRouteIv = (ImageView) findViewById(R.id.map_route_plan_bus_iv);
//		carRouteIv = (ImageView) findViewById(R.id.map_route_plan_car_iv);
//		walkRouteIv = (ImageView) findViewById(R.id.map_route_plan_walk_iv);
		backRl = (RelativeLayout) findViewById(R.id.map_route_plan_back_rl);
		busRouteRl = (RelativeLayout) findViewById(R.id.map_route_plan_bus_rl);
		carRouteRl = (RelativeLayout) findViewById(R.id.map_route_plan_car_rl);
		walkRouteRl = (RelativeLayout) findViewById(R.id.map_route_plan_walk_rl);
		editSt = (EditText) findViewById(R.id.start);
		editEn = (EditText) findViewById(R.id.end);
		mapRouteListView = (ListView) findViewById(R.id.map_route_list_view);

		myMapRouteLines = new ArrayList<MyMapRouteLine>();
		mapRouteListAdapter = new MapRouteListAdapter(this, myMapRouteLines);
		mapRouteListView.setAdapter(mapRouteListAdapter);
		// 初始化搜索模块
		mSearch = RoutePlanSearch.newInstance();
		if (currentPlace != null) {
			MyApp.currentPlaceMap = new HashMap<String, String>();
			MyApp.currentPlaceMap.put("我的位置", currentPlace);
			editSt.setText("我的位置");
		}
	}

	private void initListener() {
		// TODO Auto-generated method stub
//		busRouteIv.setOnClickListener(this);
//		carRouteIv.setOnClickListener(this);
//		walkRouteIv.setOnClickListener(this);
		backRl.setOnClickListener(this);
		busRouteRl.setOnClickListener(this);
		carRouteRl.setOnClickListener(this);
		walkRouteRl.setOnClickListener(this);
		// 初始化搜索模块，注册事件监听
		mSearch.setOnGetRoutePlanResultListener(this);
		mapRouteListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MapRoutPlanActivity.this,
						MapRoutePlanMapActivity.class);
				intent.putStringArrayListExtra("mapRoutPlanTextList",
						myMapRouteLineStepLists.get(position));
				intent.putExtra("route_plan_title",
						myMapRouteLines.get(position).getTitle());
				intent.putExtra("route_plan_time", myMapRouteLines
						.get(position).getDuration());
				intent.putExtra("route_plan_dis", myMapRouteLines.get(position)
						.getDistance());
				intent.putExtra("route", position);
				intent.putExtra("st", editSt.getText().toString());
				intent.putExtra("en", editEn.getText().toString());
				intent.putExtra("routePlan", routePlan);
				intent.putExtra("city", currentcity);
				MapRoutPlanActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			MapPlaceData.getStartEndData(this, result.getSuggestAddrInfo(),
					editSt, editEn);
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			routePlan = "WALK";
			walkingRouteLines = result.getRouteLines();
			myMapRouteLines = new ArrayList<MyMapRouteLine>();
			myMapRouteLineStepLists = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < walkingRouteLines.size(); i++) {
				MyMapRouteLine myMapRouteLine = new MyMapRouteLine();
				ArrayList<String> myMapRouteLineSteps = new ArrayList<String>();
				myMapRouteLine.setDistance(StringToDate
						.getDistance(walkingRouteLines.get(i).getDistance()));
				myMapRouteLine.setDuration(StringToDate
						.getDate(walkingRouteLines.get(i).getDuration()));
				myMapRouteLine.setTitle("步行线路推荐" + (i + 1));
				List<WalkingStep> walkingSteps = walkingRouteLines.get(i)
						.getAllStep();
				for (WalkingStep walkingStep : walkingSteps) {
					myMapRouteLineSteps.add(walkingStep.getInstructions());
				}
				myMapRouteLines.add(myMapRouteLine);
				myMapRouteLineStepLists.add(myMapRouteLineSteps);
			}
			mapRouteListAdapter.setMyMapRoutLine(myMapRouteLines);
			mapRouteListAdapter.notifyDataSetChanged();
		}
		dialog.dismiss();
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {

		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			MapPlaceData.getStartEndData(this, result.getSuggestAddrInfo(),
					editSt, editEn);
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			routePlan = "TRANSIT";
			transitRouteLines = result.getRouteLines();
			transitRouteLines.get(0);
			myMapRouteLines = new ArrayList<MyMapRouteLine>();
			myMapRouteLineStepLists = new ArrayList<ArrayList<String>>();
			List<Integer> timeCompareList = new ArrayList<Integer>();
			List<Integer> disCompareList = new ArrayList<Integer>();
			Map<Integer, String> timeCompareMap = new HashMap<Integer, String>();
			for (int i = 0; i < transitRouteLines.size(); i++) {

				int dis = transitRouteLines.get(i).getDistance();
				int time = transitRouteLines.get(i).getDuration();
				timeCompareList.add(time);
				disCompareList.add(dis);
				timeCompareMap = getTimeMap(timeCompareList);
			}

			for (int i = 0; i < transitRouteLines.size(); i++) {
				List<TransitStep> transitSteps = transitRouteLines.get(i)
						.getAllStep();
				String transation = getTransStation(transitSteps);
				int dis = transitRouteLines.get(i).getDistance();
				int time = transitRouteLines.get(i).getDuration();
				if (transation != null) {
					MyMapRouteLine myMapRouteLine = new MyMapRouteLine();
					ArrayList<String> myMapRouteLineSteps = new ArrayList<String>();
					myMapRouteLine.setDistance(StringToDate.getDistance(dis));
					myMapRouteLine.setDuration(StringToDate.getDate(time));
					myMapRouteLine.setTitle(transation);
					myMapRouteLine.setCompare(timeCompareMap.get(time));
					for (TransitStep transitStep : transitSteps) {
						myMapRouteLineSteps.add(transitStep.getInstructions());
					}
					myMapRouteLines.add(myMapRouteLine);
					myMapRouteLineStepLists.add(myMapRouteLineSteps);
				}

			}

			mapRouteListAdapter.setMyMapRoutLine(myMapRouteLines);
			mapRouteListAdapter.notifyDataSetChanged();
		}
		dialog.dismiss();
	}

	// private Map<Integer, String> getDisMap(List<Integer> disCompareList) {
	// Map<Integer, String> newDisCompareMap=new HashMap<Integer, String>();
	// Collections.sort(disCompareList);
	// if(disCompareList.size()==1){
	// newDisCompareMap.put(disCompareList.get(0), "");
	// }else{
	// for(int i=0;i<disCompareList.size();i++){
	// if(i==0){
	// newDisCompareMap.put(disCompareList.get(i), "距离最短");
	// }else if(i==(disCompareList.size()-1)){
	// newDisCompareMap.put(disCompareList.get(i), "距离最长");
	// }else{
	// newDisCompareMap.put(disCompareList.get(i), "");
	// }
	// }
	// }
	// return newDisCompareMap;
	// }

	private Map<Integer, String> getTimeMap(List<Integer> timeCompareList) {
		Map<Integer, String> newTimeCompareMap = new HashMap<Integer, String>();
		Collections.sort(timeCompareList);
		if (timeCompareList.size() == 1) {
			newTimeCompareMap.put(timeCompareList.get(0), "");
		} else {
			for (int i = 0; i < timeCompareList.size(); i++) {
				if (i == 0) {
					newTimeCompareMap.put(timeCompareList.get(i), "用时最少");
				} else if (i == (timeCompareList.size() - 1)) {
					newTimeCompareMap.put(timeCompareList.get(i), "用时最长");
				} else {
					newTimeCompareMap.put(timeCompareList.get(i), "");
				}
			}
		}

		return newTimeCompareMap;
	}

	private String getTransStation(List<TransitStep> transitSteps) {
		// TODO Auto-generated method stub
		StringBuffer transStationBuffer = new StringBuffer();
		for (int i = 0; i < transitSteps.size(); i++) {
			VehicleInfo vehicleInfo = transitSteps.get(i).getVehicleInfo();
			if (vehicleInfo != null) {
				transStationBuffer.append(vehicleInfo.getTitle() + "->");
			}

		}

		return transStationBuffer.toString().substring(0,
				transStationBuffer.toString().lastIndexOf("->"));
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			MapPlaceData.getStartEndData(this, result.getSuggestAddrInfo(),
					editSt, editEn);
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			routePlan = "DRIVING";
			drivingRouteLines = result.getRouteLines();
			myMapRouteLines = new ArrayList<MyMapRouteLine>();
			myMapRouteLineStepLists = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < drivingRouteLines.size(); i++) {
				MyMapRouteLine myMapRouteLine = new MyMapRouteLine();
				ArrayList<String> myMapRouteLineSteps = new ArrayList<String>();
				List<DrivingStep> drivingSteps = drivingRouteLines.get(i)
						.getAllStep();
				myMapRouteLine.setDistance(StringToDate
						.getDistance(drivingRouteLines.get(i).getDistance()));
				myMapRouteLine.setDuration(StringToDate
						.getDate(drivingRouteLines.get(i).getDuration()));
				myMapRouteLine.setTitle("驾车线路推荐" + (i + 1));
				for (DrivingStep drivingStep : drivingSteps) {
					myMapRouteLineSteps.add(drivingStep.getInstructions());
				}
				myMapRouteLines.add(myMapRouteLine);
				myMapRouteLineStepLists.add(myMapRouteLineSteps);
			}
			mapRouteListAdapter.setMyMapRoutLine(myMapRouteLines);
			mapRouteListAdapter.notifyDataSetChanged();
		}
		dialog.dismiss();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		route = null;

		// 处理搜索按钮响应
		if (currentcity != null) {
			// 设置起终点信息，对于tranist search 来说，城市名无意义
			PlanNode stNode;
			if (MyApp.currentPlaceMap.get(editSt.getText().toString()) != null) {
				stNode = PlanNode.withCityNameAndPlaceName(currentcity,
						MyApp.currentPlaceMap.get(editSt.getText().toString()));

			} else {
				stNode = PlanNode.withCityNameAndPlaceName(currentcity, editSt
						.getText().toString());

			}
			PlanNode enNode = PlanNode.withCityNameAndPlaceName(currentcity,
					editEn.getText().toString());

			// 实际使用中请对起点终点城市进行正确的设定
			if (v.getId() == R.id.map_route_plan_car_rl) {
				dialog=ProgressDialogUtil.getProgress(MapRoutPlanActivity.this,"加载中...");
				dialog.show();
				mSearch.drivingSearch((new DrivingRoutePlanOption()).from(
						stNode).to(enNode));
			} else if (v.getId() == R.id.map_route_plan_bus_rl) {
				dialog=ProgressDialogUtil.getProgress(MapRoutPlanActivity.this,"加载中...");
				dialog.show();
				mSearch.transitSearch((new TransitRoutePlanOption())
						.from(stNode).city(currentcity).to(enNode));
			} else if (v.getId() == R.id.map_route_plan_walk_rl) {
				dialog=ProgressDialogUtil.getProgress(MapRoutPlanActivity.this,"加载中...");
				dialog.show();
				mSearch.walkingSearch((new WalkingRoutePlanOption()).from(
						stNode).to(enNode));
			}else if (v.getId() == R.id.map_route_plan_back_rl) {
				finish();
			}
		}
	}

}
