package com.risesoft.lifeassite.view.cityactivity;

import java.util.ArrayList;

import java.util.List;

import com.baidu.location.BDLocation;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.UserCityAdapter;
import com.risesoft.lifeassite.util.DataFeedbackListener;
import com.risesoft.lifeassite.util.LocationRequest;
import com.risesoft.lifeassite.util.ProgressDialogUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class CitySearchLocationActivity extends Activity implements
		OnClickListener {

	ImageView citySearchLocationBack;

	EditText cityNameSearchET;

	GridView cityUseGv;

	List<String> useCity;

	UserCityAdapter userCityAdapter;

	SharedPreferences cityWeatherPreferences;
	Editor weatherEditor;
	
	SharedPreferences cityMoviePreferences;
	Editor movieEditor;

	LocationRequest locationRequest;
	
	int cityClass;
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //ȥ���Դ�title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//����
		setContentView(R.layout.city_search_location);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		useCity = new ArrayList<String>();
		getUseCities();
		cityClass=getIntent().getIntExtra("cityclass", -1);
		cityWeatherPreferences = getSharedPreferences("cityweather", 2);
		weatherEditor = cityWeatherPreferences.edit();
		cityMoviePreferences = getSharedPreferences("citymovie", 11);
		movieEditor = cityMoviePreferences.edit();
		citySearchLocationBack = (ImageView) this
				.findViewById(R.id.city_search_location_back);
		cityNameSearchET = (EditText) this
				.findViewById(R.id.city_search_name_location_et);
		cityUseGv = (GridView) this.findViewById(R.id.city_use_gv);
		userCityAdapter = new UserCityAdapter(useCity, this);
		cityUseGv.setAdapter(userCityAdapter);
	}

	private void initListener() {
		// TODO Auto-generated method stub

		citySearchLocationBack.setOnClickListener(this);
		cityNameSearchET.addTextChangedListener(new MyTextWatcher());
		cityUseGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(cityClass==1){
					if (cityWeatherPreferences.getString("cityweathername",
							null) != null) {
						weatherEditor.remove("cityweathername");
					}
				}
                if(cityClass==2){
    				if (cityMoviePreferences.getString("citymoviename",
    						null) != null) {
    					movieEditor.remove("citymoviename");
    				}
                }

				if (useCity.get(position).equals("��λ")) {
					dialog=ProgressDialogUtil.getProgress(CitySearchLocationActivity.this,"��λ��...");
					dialog.show();
					locationRequest=new LocationRequest();
					locationRequest.setDataFeedbackListener(new DataFeedbackListener() {
						
						@Override
						public void onReceiver(BDLocation location) {
							// TODO Auto-generated method stub
							if(location!=null){
								if(cityClass==1){
									weatherEditor.putString("cityweathername",
											location.getCity().replace("��", "")+"-"+location.getCity().replace("��", ""));
									weatherEditor.commit();
								}
				                if(cityClass==2){
									movieEditor.putString("citymoviename",
											location.getCity().replace("��", "")+"-"+location.getCity().replace("��", ""));
									movieEditor.commit();
				                }
								finish();
							}else{
								Toast.makeText(CitySearchLocationActivity.this, "�޷���ȡ����ǰλ��...", 500).show();
							}
							dialog.dismiss();
						}
					});
					locationRequest.startLoc();

				} else {
					if(cityClass==1){
						weatherEditor.putString("cityweathername",
								useCity.get(position)+"-"+useCity.get(position));
						weatherEditor.commit();
					}
	                if(cityClass==2){
						movieEditor.putString("citymoviename",
								useCity.get(position)+"-"+useCity.get(position));
						movieEditor.commit();
	                }
					finish();
				}

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.city_search_location_back:
			finish();
			break;
		default:
			break;
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
			// TODO Auto-generated method stub
			if (s.toString() != null) {

				Intent intent = new Intent(CitySearchLocationActivity.this,
						CitySearchActivity.class);
				intent.putExtra("cityname", s.toString());
				intent.putExtra("cityclass", cityClass);
				CitySearchLocationActivity.this.startActivity(intent);
				CitySearchLocationActivity.this.finish();
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

	}

	private void getUseCities() {
		// TODO Auto-generated method stub

		useCity.add("��λ");
		useCity.add("����");
		useCity.add("�Ϻ�");
		useCity.add("����");
		useCity.add("����");
		useCity.add("�麣");
		useCity.add("��ɽ");
		useCity.add("�Ͼ�");
		useCity.add("����");
		useCity.add("����");
		useCity.add("����");
		useCity.add("�ൺ");
		useCity.add("֣��");
		useCity.add("ʯ��ׯ");
		useCity.add("����");
		useCity.add("����");
		useCity.add("�人");
		useCity.add("��ɳ");
		useCity.add("�ɶ�");
		useCity.add("����");
		useCity.add("̫ԭ");
		useCity.add("����");
		useCity.add("����");
		useCity.add("����");

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
	}

	
}
