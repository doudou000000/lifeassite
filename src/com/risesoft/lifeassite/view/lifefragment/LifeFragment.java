package com.risesoft.lifeassite.view.lifefragment;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.view.lifefragment.express.ExpressFragment;
import com.risesoft.lifeassite.view.lifefragment.map.MapFragment;
import com.risesoft.lifeassite.view.lifefragment.weather.WeatherFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class LifeFragment extends Fragment implements OnClickListener{

	View lifeView;
	RadioButton mapRb,expressRb,weatherRb;
	Fragment lifeChildFragment;
	MapFragment mapFragment=new MapFragment();
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		lifeView=LayoutInflater.from(getActivity()).inflate(R.layout.life_layout, null);
		initView();
		initListener();
		return lifeView;
	}
	private void initView() {
		// TODO Auto-generated method stub
		getChildFragmentManager().beginTransaction().add(R.id.life_fragment, new WeatherFragment()).commit();
		mapRb=(RadioButton)lifeView.findViewById(R.id.life_tab_map);
		weatherRb=(RadioButton)lifeView.findViewById(R.id.life_tab_weather);
		expressRb=(RadioButton)lifeView.findViewById(R.id.life_tab_express);
	
	}
	private void initListener() {
		// TODO Auto-generated method stub
		weatherRb.setOnClickListener(this);
		expressRb.setOnClickListener(this);
		mapRb.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.life_tab_weather:
			getChildFragmentManager().beginTransaction().replace(R.id.life_fragment, new WeatherFragment()).commit();
			break;
		case R.id.life_tab_express:
			getChildFragmentManager().beginTransaction().replace(R.id.life_fragment, new ExpressFragment()).commit();
			break;
		case R.id.life_tab_map: 
			getChildFragmentManager().beginTransaction().replace(R.id.life_fragment, new MapFragment()).commit();
			break;

		default:
			break;
		}
	}
	
}
