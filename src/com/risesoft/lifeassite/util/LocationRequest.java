package com.risesoft.lifeassite.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class LocationRequest {

	private LocationClient locationClient = null;

	private MyLocationListenner myListener = new MyLocationListenner();

	private DataFeedbackListener dataFeedbackListener;
	
	public LocationRequest() {

		locationClient = new LocationClient(MyApp.myApp);
		locationClient.registerLocationListener(myListener);
		setLocationOption();

	}

	private void setLocationOption() {
		// TODO Auto-generated method stub
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//
		option.setCoorType("bd09ll");//
		option.setScanSpan(1000);//
		option.setIsNeedAddress(true);//
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//
		option.setIsNeedLocationPoiList(true);//
		locationClient.setLocOption(option);
	}

	public void setDataFeedbackListener(DataFeedbackListener dataFeedbackListener){
		this.dataFeedbackListener=dataFeedbackListener;
	}
	
	public void startLoc(){
		if(locationClient.isStarted()){
			locationClient.requestLocation();
		}else{
			locationClient.start();
		}
	}
	
	public void stopLoc(){
		
		if(locationClient.isStarted()){
			locationClient.stop();
		}
		
	}
	
	class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub

			if(locationClient==null){
				return;
			}
			
			if (location.getLatitude() > 0 || location.getLongitude() > 0) {
				dataFeedbackListener.onReceiver(location);
				locationClient.stop();
			} else {
				locationClient.requestLocation();
			}
			
		}

	}

}
