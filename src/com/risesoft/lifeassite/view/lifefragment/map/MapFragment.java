package com.risesoft.lifeassite.view.lifefragment.map;

import com.baidu.location.BDLocation;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.util.DataFeedbackListener;
import com.risesoft.lifeassite.util.LocationRequest;
import com.risesoft.lifeassite.view.SlideMenuActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MapFragment extends Fragment implements OnClickListener{

	View mapView;
	MapView mMapView;
	ImageButton mapSlideTb;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ
	LocationRequest locationRequest;
	BitmapDescriptor mCurrentMarker;
	String currentCity;
	String currentPlace;
	ImageButton mapRoutPlanIb;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mapView = LayoutInflater.from(getActivity()).inflate(
				R.layout.map_layout, null);
		try {
			initView();
			initLisener();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return mapView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		mapRoutPlanIb = (ImageButton) mapView.findViewById(R.id.map_rote_plan_ib);
		mapSlideTb = (ImageButton) mapView.findViewById(R.id.map_menu_left);
		mMapView = (MapView) mapView.findViewById(R.id.map_mv);
		mBaiduMap = mMapView.getMap();
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		// ��λ��ʼ��
        locationRequest=new LocationRequest();
        locationRequest.setDataFeedbackListener(new DataFeedbackListener() {
			
			@Override
			public void onReceiver(BDLocation location) {
				// TODO Auto-generated method stub
				if (location == null || mMapView == null) {
	                return;
	            }
	            MyLocationData locData = new MyLocationData.Builder()
	                    .accuracy(location.getRadius())
	                            // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
	                    .direction(100).latitude(location.getLatitude())
	                    .longitude(location.getLongitude()).build();
	            mBaiduMap.setMyLocationData(locData);
	            if (isFirstLoc) {
	                isFirstLoc = false;
	                LatLng ll = new LatLng(location.getLatitude(),
	                        location.getLongitude());
	                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
	                mBaiduMap.animateMapStatus(u);
	                Button button = new Button(getActivity());  
	                button.setBackgroundResource(R.drawable.pop); 
	                button.setText("�ҵ�ǰ��λ��");
	                button.setGravity(Gravity.CENTER);
	                mBaiduMap.showInfoWindow(new InfoWindow(button, ll, -80));
	                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.poi_red);  
	                //����MarkerOption�������ڵ�ͼ�����Marker  
	                OverlayOptions option = new MarkerOptions()  
	                	    .position(ll)  
	                	    .icon(bitmap);  
	                //�ڵ�ͼ�����Marker������ʾ  
	                mBaiduMap.addOverlay(option);
	                currentCity=location.getCity();
	                currentPlace=location.getAddrStr();
	            }
			}
		});
        locationRequest.startLoc();
	}

	
	
	private void initLisener() {
		// TODO Auto-generated method stub
		mapRoutPlanIb.setOnClickListener(this);
		mapSlideTb.setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		// �˳�ʱ���ٶ�λ
		locationRequest.stopLoc();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
	}
	
	@Override
	public void onResume() {
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    mMapView.setVisibility(View.VISIBLE);
            }
        },1000) ;  
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		mMapView.setVisibility(View.GONE); 
		mMapView.onPause();
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.map_rote_plan_ib:
			Intent intent=new Intent(getActivity(), MapRoutPlanActivity.class);
			intent.putExtra("city", currentCity);
			intent.putExtra("place", currentPlace);
			getActivity().startActivity(intent);
			break;
		case R.id.map_menu_left:
			SlideMenuActivity.menu.toggle();
			break;
		default:
			break;
		}
	}
	
}
