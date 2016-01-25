package com.risesoft.lifeassite.view.cityactivity;

import java.util.ArrayList;

import java.util.List;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.CityAdapter;

import android.app.Activity;
import android.app.SearchManager.OnCancelListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class CitySearchActivity extends Activity implements OnClickListener{

	EditText cityNameEt;

	SharedPreferences cityPreferences;
	SharedPreferences cityWeatherPreferences;
	Editor weatherEditor;
	SharedPreferences cityMoviePreferences;
	Editor movieEditor;

	ImageView citySearchBackIv;
	
	List<String> saveCityList = new ArrayList<String>();
	List<String> cityList = new ArrayList<String>();
	CityAdapter cityAdapter;

	ListView cityListView;

	int cityClass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //È¥³ý×Ô´øtitle
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		setContentView(R.layout.city_search);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		cityClass=getIntent().getIntExtra("cityclass", -1);
		cityPreferences = getSharedPreferences("city", 0);
		
		cityWeatherPreferences = getSharedPreferences("cityweather", 2);
		weatherEditor = cityWeatherPreferences.edit();
		
		cityMoviePreferences = getSharedPreferences("citymovie", 11);
		movieEditor = cityMoviePreferences.edit();
		
		citySearchBackIv = (ImageView) this.findViewById(R.id.city_search_back);
		cityNameEt = (EditText) this.findViewById(R.id.city_search_name_et);
		cityNameEt.addTextChangedListener(new MyTextWatcher());
		cityListView = (ListView) this.findViewById(R.id.city_search_list_view);
		cityAdapter = new CityAdapter(this, cityList);
		cityListView.setAdapter(cityAdapter);
		cityNameEt.setText(getIntent().getStringExtra("cityname"));
		int size = cityPreferences.getInt("city_size", 0);
		for (int i = 0; i < size; i++) {
			saveCityList.add(cityPreferences.getString("city_" + i, null));

		}
	}

	private void initListener() {
		// TODO Auto-generated method stub
		citySearchBackIv.setOnClickListener(this);
		cityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(cityClass==1){
					if (cityWeatherPreferences.getString("cityweathername", null) != null) {
						weatherEditor.remove("cityweathername");
					}
					weatherEditor.putString("cityweathername",
							cityList.get(position));
					weatherEditor.commit();
				}

				if(cityClass==2){
					if (cityMoviePreferences.getString("citymoviename", null) != null) {
						movieEditor.remove("citymoviename");
					}
					movieEditor.putString("citymoviename",
							cityList.get(position));
					movieEditor.commit();
				}
				CitySearchActivity.this.finish();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String cityname=getIntent().getStringExtra("cityname");
		if(cityname!=null&&!("".equals(cityname))){
			getSearchCityList(cityname);
		}
	}
	
	class MyTextWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (s.toString() == null || "".equals(s.toString())) {
				cityAdapter.getCity(new ArrayList<String>());
				cityAdapter.notifyDataSetChanged();
			} else {
				getSearchCityList(s.toString());
			}

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

	}

	private void getSearchCityList(String cityname) {
		// TODO Auto-generated method stub
		cityList = new ArrayList<String>();

		for (int i = 0; i < saveCityList.size(); i++) {

			if (saveCityList.get(i).contains(cityname)) {
				cityList.add(saveCityList.get(i));
			}

		}
		cityAdapter.getCity(cityList);
		cityAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.city_search_back:
			CitySearchActivity.this.finish();
			break;

		default:
			break;
		}
	}
}
