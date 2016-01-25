package com.risesoft.lifeassite.util;

import com.baidu.location.BDLocation;

public abstract interface DataFeedbackListener {

	public abstract void onReceiver(BDLocation location);
	
}
