package com.risesoft.lifeassite.view.lifefragment.map;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.MapRoutPlanTextListAdapter;

/**
 * 此demo用来展示如何进行驾车、步行、公交路线搜索并在地图使用RouteOverlay、TransitOverlay绘制
 * 同时展示如何进行节点浏览并弹出泡泡
 */
public class MapRoutePlanMapActivity extends Activity implements
		BaiduMap.OnMapClickListener, OnGetRoutePlanResultListener,
		OnClickListener {

	int route;
	OverlayManager routeOverlay = null;
	boolean useDefaultIcon = false;

	// 地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
	// 如果不处理touch事件，则无需继承，直接使用MapView即可
	MapView mMapView = null; // 地图View
	BaiduMap mBaidumap = null;
	// 搜索相关
	RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	String routePlan;

	String city;

	String title;
	String time;
	String distence;

	TextView titleTv, timeTv, disTv;
	List<String> mapRoutPlanTextLists=new ArrayList<String>();
	LinearLayout showPopLl;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去除自带title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.map_route_plan_map_layout);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		// 初始化地图
		route = getIntent().getIntExtra("route", -1);
		city = getIntent().getStringExtra("city");
		mapRoutPlanTextLists=getIntent().getStringArrayListExtra("mapRoutPlanTextList");
		routePlan = getIntent().getStringExtra("routePlan");
		title = getIntent().getStringExtra("route_plan_title");
		time = getIntent().getStringExtra("route_plan_time");
		distence = getIntent().getStringExtra("route_plan_dis");
		titleTv = (TextView) findViewById(R.id.map_route_plan_map_title_tv);
		timeTv = (TextView) findViewById(R.id.map_route_plan_map_dur_tv);
		disTv = (TextView) findViewById(R.id.map_route_plan_map_dic_tv);
		showPopLl = (LinearLayout) findViewById(R.id.map_route_plan_map_ll);
		mMapView = (MapView) findViewById(R.id.map_route_plan_map_view);
		mBaidumap = mMapView.getMap();
		titleTv.setText(title);
		timeTv.setText(time);
		disTv.setText(distence);
		// 初始化搜索模块，注册事件监听
		mSearch = RoutePlanSearch.newInstance();

		PlanNode stNode = PlanNode.withCityNameAndPlaceName(city, getIntent()
				.getStringExtra("st"));
		PlanNode enNode = PlanNode.withCityNameAndPlaceName(city, getIntent()
				.getStringExtra("en"));
		if (routePlan.equals("WALK")) {
			mBaidumap.clear();
			mSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode)
					.to(enNode));
		}
		if (routePlan.equals("TRANSIT")) {
			mBaidumap.clear();
			mSearch.transitSearch((new TransitRoutePlanOption()).from(stNode)
					.city(city).to(enNode));
		}
		if (routePlan.equals("DRIVING")) {
			mBaidumap.clear();

			mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode)
					.to(enNode));
		}
	}

	private void initListener() {
		// TODO Auto-generated method stub
		// 地图点击事件处理
		mBaidumap.setOnMapClickListener(this);
		// 初始化搜索模块，注册事件监听
		mSearch.setOnGetRoutePlanResultListener(this);

		showPopLl.setOnClickListener(this);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapRoutePlanMapActivity.this, "抱歉，未找到结果",
					Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaidumap);
			mBaidumap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(route));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {

		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapRoutePlanMapActivity.this, "抱歉，未找到结果",
					Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidumap);
			mBaidumap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(route));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapRoutePlanMapActivity.this, "抱歉，未找到结果",
					Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
			routeOverlay = overlay;
			mBaidumap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(route));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	// 定制RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory
						.fromResource(R.drawable.map_route_icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory
						.fromResource(R.drawable.map_route_icon_en);
			}
			return null;
		}
	}

	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

		public MyWalkingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory
						.fromResource(R.drawable.map_route_icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory
						.fromResource(R.drawable.map_route_icon_en);
			}
			return null;
		}
	}

	private class MyTransitRouteOverlay extends TransitRouteOverlay {

		public MyTransitRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory
						.fromResource(R.drawable.map_route_icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory
						.fromResource(R.drawable.map_route_icon_en);
			}
			return null;
		}
	}

	@Override
	public void onMapClick(LatLng point) {
		mBaidumap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi poi) {
		return false;
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mSearch.destroy();
		mMapView.onDestroy();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.map_route_plan_map_ll:
			showPopView();
			break;

		default:
			break;
		}
	}

	private void showPopView() {
		// TODO Auto-generated method stub
		View mapTextPopView = LayoutInflater.from(this).inflate(
				R.layout.map_route_plan_text_layout, null);
		TextView mapTextTitle=(TextView)mapTextPopView.findViewById(R.id.map_route_plan_text_title_tv);
		TextView mapTextDur=(TextView)mapTextPopView.findViewById(R.id.map_route_plan__text_dur_tv);
		TextView mapTextDis=(TextView)mapTextPopView.findViewById(R.id.map_route_plan__text_dic_tv);
		LinearLayout hidePopll=(LinearLayout)mapTextPopView.findViewById(R.id.map_route_plan_text_ll);
		ListView mapRoutPlanTextListView=(ListView)mapTextPopView.findViewById(R.id.map_route_plan_list__text_lv);

		MapRoutPlanTextListAdapter mapRoutPlanTextListAdapter=new MapRoutPlanTextListAdapter(this,mapRoutPlanTextLists);
		mapRoutPlanTextListView.setAdapter(mapRoutPlanTextListAdapter);
		mapTextTitle.setText(title);
		mapTextDur.setText(time);
		mapTextDis.setText(distence);
		final PopupWindow window = new PopupWindow(mapTextPopView,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT);

		// 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
		window.setFocusable(true);

		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		window.setBackgroundDrawable(dw);
		window.setAnimationStyle(R.style.map_popWindow_animation);
		// 设置popWindow的显示和消失动画
		// 在底部显示
		window.showAtLocation(this.findViewById(R.id.map_route_plan_map_ll),
				Gravity.BOTTOM, 0, 0);
		
		hidePopll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				window.dismiss();
			}
		});
		
	}

}
