package com.risesoft.lifeassite.view.lifefragment.weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.weather.Weather;
import com.risesoft.lifeassite.entity.weather.WeatherFutrue;
import com.risesoft.lifeassite.util.JuHeRequest;
import com.risesoft.lifeassite.util.MyApp;
import com.risesoft.lifeassite.util.OpenNetWork;
import com.risesoft.lifeassite.util.PreferencesSaveList;
import com.risesoft.lifeassite.view.SlideMenuActivity;
import com.risesoft.lifeassite.view.cityactivity.CitySearchLocationActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

public class WeatherFragment extends Fragment implements OnClickListener {

	View weatherView;
	TextView cityTv, temperatureTv, airTv, humidityTv, visibilyTv, windTv,
			chuangyiMakTv, chuanyiAdviceTv, yundongMarkTv, yundongAdaviceTv,
			ganmaoMarkTv, ganmaoAdaviceTv, wuranMarkTv, wuranAdaviceTv,
			ziwaixianMarkTv, ziyaixianAdaviceTv, kongtiaoMarkTv,
			kongtiaoAdaviceTv, xicheMarkTv, xicheAdaviceTv,tomrrowDateTv,tomrrowTemTv,tomrrowDuTv,secondDateTv,secondTemTv,secondDuTv,thirdDateTv,thirdTemTv,thirdDuTv,fourthDateTv,fourthTemTv,fourthDuTv;
	LinearLayout weatherBgLl;
	
	PullToRefreshScrollView weatherPrs;
	
	List<String> xLineData, yLineData;
	LineChart chart;
	SharedPreferences cityIdPreferences;
	SharedPreferences cityWeatherPreferences;
	ImageView weatherSearchCityIv;
	char symbol = 176;

	List<String> weatherFutrueDate;
	List<String> weatherFutrueDayWeather;
	List<String> weatherFutrueNightWeather;
	List<String> weatherFutrueDayTemplate;
	List<String> weatherFutrueNightTemplate;

	private LinearLayout weatherAdviceLayout;// 天气建议项
	private LinearLayout weatherContentLayout;// 天气详情项

	private LayoutParams weatherAdviceParams;// 建议项目的参数
	private LayoutParams weatherContentParams;// 内容项目的参数contentLayout的宽度值

	private int disPlayWidth;// 手机屏幕分辨率 宽
	private int disPlayHeight;// 手机屏幕分辨率 高

	Map<String, Integer> weatherPic;
	
	SharedPreferences weatherPreferences;
	Editor weatherEditor;
	ImageButton weatherMenuLeft;
	String temperatureStr;
	String airInfoStr;
	String airPMStr;
	String humidityStr;
	String visibilyStr;
	String windStr;
	String chuangyiMakStr;
	String chuanyiAdviceStr;
	String yundongMarkStr;
	String yundongAdaviceStr;
	String ganmaoMarkStr;
	String ganmaoAdaviceStr;
	String wuranMarkStr;
	String wuranAdaviceStr;
	String ziwaixianMarkStr;
	String ziyaixianAdaviceStr;
	String kongtiaoMarkStr;
	String kongtiaoAdaviceStr;
	String xicheMarkStr;
	String xicheAdaviceStr;
	List<WeatherFutrue> weatherFutrues;
	boolean isAutoRefresh=true;
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			try {
				switch (msg.what) {
				case 0:
					String weatherResult = msg.obj.toString();
					if(weatherResult!=null){
						Weather weather = JSON
								.parseObject(weatherResult, Weather.class);
						if(weather.getResult()!=null){
							temperatureStr=weather.getResult().getData()
									.getRealtime().getWeather().getTemperature()
									+ String.valueOf(symbol);
							airInfoStr=weather.getResult().getData().getRealtime()
									.getWeather().getInfo();
							airPMStr=weather.getResult().getData().getPm25().getPm25()
									.getQuality();
							humidityStr="湿度\n\n"
									+ weather.getResult().getData().getRealtime()
									.getWeather().getHumidity();
							visibilyStr="紫外线\n\n"
									+ weather.getResult().getData().getLife().getInfo()
									.getZiwaixian().get(0);
							windStr=weather.getResult().getData().getRealtime()
									.getWind().getDirect()
									+ "\n\n"
									+ weather.getResult().getData().getRealtime().getWind()
											.getPower();
							chuangyiMakStr="穿衣指数："
									+ weather.getResult().getData().getLife().getInfo()
									.getChuanyi().get(0);
							chuanyiAdviceStr=weather.getResult().getData().getLife()
									.getInfo().getChuanyi().get(1);
							yundongMarkStr="运动指数："
									+ weather.getResult().getData().getLife().getInfo()
									.getYundong().get(0);
							yundongAdaviceStr=weather.getResult().getData()
									.getLife().getInfo().getYundong().get(1);
							ganmaoMarkStr="感冒指数："
									+ weather.getResult().getData().getLife().getInfo()
									.getGanmao().get(0);
							ganmaoAdaviceStr=weather.getResult().getData().getLife()
									.getInfo().getGanmao().get(1);
						    wuranMarkStr="污染指数："
									+ weather.getResult().getData().getLife().getInfo()
									.getWuran().get(0);
							wuranAdaviceStr=weather.getResult().getData().getLife()
									.getInfo().getWuran().get(1);
							ziwaixianMarkStr="紫外线指数："
									+ weather.getResult().getData().getLife().getInfo()
									.getZiwaixian().get(0);
							ziyaixianAdaviceStr=weather.getResult().getData()
									.getLife().getInfo().getZiwaixian().get(1);
							kongtiaoMarkStr="空调指数："
									+ weather.getResult().getData().getLife().getInfo()
									.getKongtiao().get(0);
							kongtiaoAdaviceStr=weather.getResult().getData()
									.getLife().getInfo().getKongtiao().get(1);
							xicheMarkStr="洗车指数："
									+ weather.getResult().getData().getLife().getInfo()
									.getXiche().get(0);
							xicheAdaviceStr=weather.getResult().getData().getLife()
									.getInfo().getXiche().get(1);
							weatherFutrues = weather.getResult()
									.getData().getWeather();
							setDataToFragment(temperatureStr,airInfoStr,airPMStr,humidityStr,visibilyStr,windStr,
									chuangyiMakStr,chuanyiAdviceStr,yundongMarkStr,yundongAdaviceStr,
									ganmaoMarkStr,ganmaoAdaviceStr,wuranMarkStr,wuranAdaviceStr,
									ziwaixianMarkStr,ziyaixianAdaviceStr,kongtiaoMarkStr,kongtiaoAdaviceStr,
									xicheMarkStr,xicheAdaviceStr,weatherFutrues);
							weatherEditor.putString("temperatureStr", temperatureStr);
							weatherEditor.putString("airInfoStr", airInfoStr);
							weatherEditor.putString("airPMStr", airPMStr);
							weatherEditor.putString("humidityStr", humidityStr);
							weatherEditor.putString("visibilyStr", visibilyStr);
							weatherEditor.putString("windStr", windStr);
							weatherEditor.putString("chuangyiMakStr", chuangyiMakStr);
							weatherEditor.putString("chuanyiAdviceStr", chuanyiAdviceStr);
							weatherEditor.putString("yundongMarkStr", yundongMarkStr);
							weatherEditor.putString("yundongAdaviceStr", yundongAdaviceStr);
							weatherEditor.putString("ganmaoMarkStr", ganmaoMarkStr);
							weatherEditor.putString("ganmaoAdaviceStr", ganmaoAdaviceStr);
							weatherEditor.putString("wuranMarkStr", wuranMarkStr);
							weatherEditor.putString("wuranAdaviceStr", wuranAdaviceStr);
							weatherEditor.putString("ziwaixianMarkStr", ziwaixianMarkStr);
							weatherEditor.putString("ziyaixianAdaviceStr", ziyaixianAdaviceStr);
							weatherEditor.putString("kongtiaoMarkStr", kongtiaoMarkStr);
							weatherEditor.putString("kongtiaoAdaviceStr", kongtiaoAdaviceStr);
							weatherEditor.putString("xicheMarkStr", xicheMarkStr);
							weatherEditor.putString("xicheAdaviceStr", xicheAdaviceStr);
							weatherEditor.putString("weatherFutrues", PreferencesSaveList.SceneList2String(weatherFutrues));
							weatherEditor.commit();
						}else{
							Toast.makeText(getActivity(), "没有查询到结果，请更换其他城市", 1000).show();
						}
					}else{
						Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 500).show();
						refreshList();
					}					
					break;
				case -1:
					refreshList();
					break;
				default:
					break;
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			weatherPrs.postDelayed(new Runnable() {
		        @Override
		        public void run() {
		        	weatherPrs.onRefreshComplete();
		        }
		    }, 1000);
		}



	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		weatherView = LayoutInflater.from(getActivity()).inflate(
				R.layout.weather_layout, null);
		try {
			initLayoutParams();
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return weatherView;
	}

	private void initLayoutParams() {
		// TODO Auto-generated method stub
		// 得到屏幕的大小
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		disPlayWidth = dm.widthPixels;
		disPlayHeight = dm.heightPixels;

		// 获得控件
		weatherAdviceLayout = (LinearLayout) weatherView
				.findViewById(R.id.weather_advice_ll);
		weatherContentLayout = (LinearLayout) weatherView
				.findViewById(R.id.weather_content_ll);

		// 获得控件参数
		weatherAdviceParams = (LinearLayout.LayoutParams) weatherAdviceLayout
				.getLayoutParams();
		weatherContentParams = (LinearLayout.LayoutParams) weatherContentLayout
				.getLayoutParams();

		// 初始化菜单和内容的宽和边距
		weatherAdviceParams.width = disPlayWidth;
		weatherAdviceParams.height = 0 - disPlayHeight;
		weatherContentParams.width = disPlayWidth;
		weatherContentParams.height = disPlayHeight;

		// 设置参数
		weatherAdviceLayout.setLayoutParams(weatherAdviceParams);
		weatherContentLayout.setLayoutParams(weatherContentParams);
	}

	private void initView() {
		// TODO Auto-generated method stub
		getWeatherPic();
		weatherPreferences=getActivity().getSharedPreferences(
				"weather", 10);
		weatherEditor=weatherPreferences.edit();
		cityIdPreferences = getActivity().getSharedPreferences("cityid", 1);
		cityWeatherPreferences = getActivity().getSharedPreferences(
				"cityweather", 2);
		weatherMenuLeft=(ImageButton)weatherView.findViewById(R.id.weather_menu_left);
		weatherPrs=(PullToRefreshScrollView)weatherView.findViewById(R.id.weather_layout_prs);
		weatherBgLl=(LinearLayout)weatherView.findViewById(R.id.weather_layout_bg_ll);
		weatherSearchCityIv = (ImageView) weatherView
				.findViewById(R.id.wearther_search_city_iv);
		cityTv = (TextView) weatherView
				.findViewById(R.id.weather_current_city_tv);
		temperatureTv = (TextView) weatherView
				.findViewById(R.id.weather_temperature_tv);
		airTv = (TextView) weatherView.findViewById(R.id.weather_air_tv);
		humidityTv = (TextView) weatherView
				.findViewById(R.id.weather_humidity_tv);
		visibilyTv = (TextView) weatherView
				.findViewById(R.id.weather_visibily_tv);
		windTv = (TextView) weatherView.findViewById(R.id.weather_wind_tv);
		chart = (LineChart) weatherView.findViewById(R.id.weather_line_chart);
		chuangyiMakTv = (TextView) weatherView
				.findViewById(R.id.weather_chuan_yi_mark_tv);
		chuanyiAdviceTv = (TextView) weatherView
				.findViewById(R.id.weather_chuan_yi_advice_tv);
		yundongMarkTv = (TextView) weatherView
				.findViewById(R.id.weather_yun_dong_mark_tv);
		yundongAdaviceTv = (TextView) weatherView
				.findViewById(R.id.weather_yun_dong_advice_tv);
		ganmaoMarkTv = (TextView) weatherView
				.findViewById(R.id.weather_gan_mao_mark_tv);
		ganmaoAdaviceTv = (TextView) weatherView
				.findViewById(R.id.weather_gan_mao_advice_tv);
		wuranMarkTv = (TextView) weatherView
				.findViewById(R.id.weather_wu_ran_mark_tv);
		wuranAdaviceTv = (TextView) weatherView
				.findViewById(R.id.weather_wu_ran_advice_tv);
		ziwaixianMarkTv = (TextView) weatherView
				.findViewById(R.id.weather_zi_wai_xian_mark_tv);
		ziyaixianAdaviceTv = (TextView) weatherView
				.findViewById(R.id.weather_zi_wai_xian_advice_tv);
		kongtiaoMarkTv = (TextView) weatherView
				.findViewById(R.id.weather_kong_tiao_mark_tv);
		kongtiaoAdaviceTv = (TextView) weatherView
				.findViewById(R.id.weather_kong_tiao_advice_tv);
		xicheMarkTv = (TextView) weatherView
				.findViewById(R.id.weather_xi_che_mark_tv);
		xicheAdaviceTv = (TextView) weatherView
				.findViewById(R.id.weather_xi_che_advice_tv);
		tomrrowDateTv=(TextView) weatherView
				.findViewById(R.id.tomorrow_tv);
		tomrrowTemTv=(TextView) weatherView
				.findViewById(R.id.tomorrow_weather_tv);
		tomrrowDuTv=(TextView) weatherView
				.findViewById(R.id.tomorrow_weather_du_tv);
		secondDateTv=(TextView) weatherView
				.findViewById(R.id.second_tv);
		secondTemTv=(TextView) weatherView
				.findViewById(R.id.second_weather_tv);
		secondDuTv=(TextView) weatherView
				.findViewById(R.id.second_weather_du_tv);
		thirdDateTv=(TextView) weatherView
				.findViewById(R.id.third_tv);
		thirdTemTv=(TextView) weatherView
				.findViewById(R.id.third_weather_tv);
		thirdDuTv=(TextView) weatherView
				.findViewById(R.id.third_weather_du_tv);
		fourthDateTv=(TextView) weatherView
				.findViewById(R.id.fouth_tv);
		fourthTemTv=(TextView) weatherView
				.findViewById(R.id.fouth_weather_tv);
		fourthDuTv=(TextView) weatherView
				.findViewById(R.id.fouth_weather_du_tv);
	
	}

	private void getWeatherPic() {
		// TODO Auto-generated method stub
		weatherPic=new HashMap<String, Integer>();
		weatherPic.put("晴", R.drawable.weather_qing_tian);
		weatherPic.put("多云", R.drawable.weather_yin_tian);
		weatherPic.put("阴", R.drawable.weather_yin_tian);
		weatherPic.put("阵雨", R.drawable.weather_yu_tian);
		weatherPic.put("雷阵雨", R.drawable.weather_yu_tian);
		weatherPic.put("雷阵雨伴有冰雹", R.drawable.weather_bing_tian);
		weatherPic.put("雨夹雪", R.drawable.weather_yu_tian);
		weatherPic.put("小雨", R.drawable.weather_yu_tian);
		weatherPic.put("中雨", R.drawable.weather_yu_tian);
		weatherPic.put("大雨", R.drawable.weather_yu_tian);
		weatherPic.put("暴雨", R.drawable.weather_yu_tian);
		weatherPic.put("大暴雨", R.drawable.weather_yu_tian);
		weatherPic.put("特大暴雨", R.drawable.weather_yu_tian);
		weatherPic.put("阵雪", R.drawable.weather_xuetian);
		weatherPic.put("小雪", R.drawable.weather_xuetian);
		weatherPic.put("中雪", R.drawable.weather_xuetian);
		weatherPic.put("大雪", R.drawable.weather_xuetian);
		weatherPic.put("暴雪", R.drawable.weather_xuetian);
		weatherPic.put("雾", R.drawable.weather_wu_tian);
		weatherPic.put("冻雨", R.drawable.weather_bing_tian);
		weatherPic.put("沙尘暴", R.drawable.weather_sha_chen_tian);
		weatherPic.put("小雨-中雨", R.drawable.weather_yu_tian);
		weatherPic.put("中雨-大雨", R.drawable.weather_yu_tian);
		weatherPic.put("大雨-暴雨", R.drawable.weather_yu_tian);
		weatherPic.put("暴雨-大暴雨", R.drawable.weather_yu_tian);
		weatherPic.put("大暴雨-特大暴雨", R.drawable.weather_yu_tian);
		weatherPic.put("小雪-中雪", R.drawable.weather_xuetian);
		weatherPic.put("中雪-大雪", R.drawable.weather_xuetian);
		weatherPic.put("大雪-暴雪", R.drawable.weather_xuetian);
		weatherPic.put("浮尘", R.drawable.weather_wu_tian);
		weatherPic.put("扬沙", R.drawable.weather_wu_tian);
		weatherPic.put("强沙尘暴", R.drawable.weather_sha_chen_tian);
		weatherPic.put("霾", R.drawable.weather_wu_mai_tian);
	}

	private void initListener() {
		weatherSearchCityIv.setOnClickListener(this);
		weatherMenuLeft.setOnClickListener(this);
		weatherPrs.setOnRefreshListener(new OnRefreshListener<ScrollView>() {  
			  
            @Override  
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {  
            	try {
            		autoSrocllRefreshView();					
				} catch (Exception e) {
					e.printStackTrace();
				}
            }  
        });  
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			if(isAutoRefresh){
				weatherPrs.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	weatherPrs.setRefreshing(true);
			        }
			    }, 500);
			}else{
				refreshList();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private void autoSrocllRefreshView() {
		// TODO Auto-generated method stub
		String cityName = cityWeatherPreferences.getString("cityweathername",
				null);
		try {
			if (cityName != null) {
				String cityId = cityIdPreferences.getString(cityName, null);
				cityTv.setText(cityName.substring(cityName.indexOf("-") + 1));
				if(OpenNetWork.getConn()){
					Parameters params = new Parameters();
					params.add("cityname", cityId);
					params.add("dtype", "json");
					JuHeRequest.getJuHeData(params, getActivity(), 73,
							"http://op.juhe.cn/onebox/weather/query", JuheData.GET,
							handler, 0);
				}else{	
					refreshList();
					Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 500).show();

				}
				isAutoRefresh=false;
			} else {
				Intent intent = new Intent(getActivity(),
						CitySearchLocationActivity.class);
				intent.putExtra("cityclass", 1);
				getActivity().startActivity(intent);
			}
			weatherPrs.postDelayed(new Runnable() {
		        @Override
		        public void run() {
		        	weatherPrs.onRefreshComplete();
		        }
		    }, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wearther_search_city_iv:
			Intent intent = new Intent(getActivity(),
					CitySearchLocationActivity.class);
			intent.putExtra("cityclass", 1);
			getActivity().startActivity(intent);
			isAutoRefresh=true;
			break;
		case R.id.weather_menu_left:
            SlideMenuActivity.menu.toggle();
			break;

		default:
			break;
		}
	}

	private void showChart(LineChart mChart, LineData mLineData, int rgb) {
		// TODO Auto-generated method stub
		// if enabled, the chart will always start at zero on the y-axis
		mChart.setDrawBorders(false); // 是否在折线图上添加边框
		mChart.fitScreen();
		// no description text
		mChart.setDescription("");// 数据描述
		// 如果没有数据的时候，会显示这个，类似listview的emtpyview
		mChart.setNoDataTextDescription("You need to provide data for the chart.");

		// enable / disable grid background
		mChart.setDrawGridBackground(false); // 是否显示表格颜色

		mChart.getAxisLeft().setEnabled(false);
		mChart.getAxisRight().setEnabled(false);
		mChart.getAxisRight().setDrawGridLines(false);
		mChart.getAxisRight().setTextColor(R.color.transparent_background);
		
		mChart.getXAxis().setPosition(XAxisPosition.BOTTOM); // 让x轴在下面
		mChart.getXAxis().setTextColor(Color.WHITE);
		mChart.getXAxis().setDrawAxisLine(false);
		mChart.getXAxis().setDrawGridLines(false);
		

		
		mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

	    
		mChart.getLegend().setEnabled(false);
		
		mChart.setData(mLineData);

	}

	private LineData getLineData(List<String> weatherFutrueDate,
			List<String> weatherFutrueDayWeather,
			List<String> weatherFutrueNightWeather,
			List<String> weatherFutrueDayTemplate,
			List<String> weatherFutrueNightTemplate) {
		
		weatherFutrueDate.remove(0);
		weatherFutrueDayWeather.remove(0);
		weatherFutrueDayTemplate.remove(0);
		weatherFutrueNightTemplate.remove(0);
		
		
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<String> futureWeather = new ArrayList<String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String nowStr = sdf.format(now);
		Date dateNow;
		Long n = null;
		try {
			dateNow = sdf.parse(nowStr);
			n = dateNow.getTime();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < weatherFutrueDate.size(); i++) {
			try {
				Date date = sdf.parse(weatherFutrueDate.get(i));
				Long d = date.getTime();
				System.out.println((d - n) / (24 * 60 * 60 * 1000));
				int xcts = (int) ((d - n) / (24 * 60 * 60 * 1000));
				if (xcts <= 2) {
					switch (xcts) {
//					case 0:
//					//	xVals.add("今天");
//						break;
					case 1:
						futureWeather.add("明天");
						xVals.add("");
						break;
					case 2:
						futureWeather.add("后天");
						xVals.add("");
						break;

					default:
						break;
					}
				} else {
					String strDate = weatherFutrueDate.get(i);
					futureWeather.add(strDate.substring(strDate.indexOf("-") + 1)
							.replace("-", "/"));
					xVals.add("");
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		ArrayList<Entry> yDayVals = new ArrayList<Entry>();

		for (int i = 0; i < weatherFutrueDayTemplate.size(); i++) {
	
				yDayVals.add(new Entry(Integer.parseInt(weatherFutrueDayTemplate
						.get(i)), i));
		

		}
		ArrayList<Entry> yNightVals = new ArrayList<Entry>();

		for (int i = 0; i < weatherFutrueNightTemplate.size(); i++) {		
				yNightVals.add(new Entry(Integer
						.parseInt(weatherFutrueNightTemplate.get(i)), i));

		}

		// create a dataset and give it a type
		LineDataSet set1 = new LineDataSet(yDayVals, "");
		LineDataSet set2 = new LineDataSet(yNightVals, "");
		// set1.setFillAlpha(110);
		// set1.setFillColor(Color.RED);

		// set the line to be drawn like this "- - - - - -"
		set1.setDrawCircles(false);
		set1.setDrawCubic(true);
		set2.setDrawCircles(false);
		set2.setDrawCubic(true);
		set1.setDrawValues(false);
		set2.setDrawValues(false);
		// set1.setDrawFilled(true);
		// set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
		// Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1); // add the datasets
		dataSets.add(set2); // add the datasets

		// create a data object with the datasets
		LineData data = new LineData(xVals, dataSets);

		tomrrowDateTv.setText(futureWeather.get(0));
		tomrrowTemTv.setText(weatherFutrueDayWeather.get(0));
        tomrrowDuTv.setText(weatherFutrueNightTemplate.get(0)+String.valueOf(symbol)+"~"+weatherFutrueDayTemplate.get(0)+String.valueOf(symbol));
        secondDateTv.setText(futureWeather.get(1));
        secondTemTv.setText(weatherFutrueDayWeather.get(1));
        secondDuTv.setText(weatherFutrueNightTemplate.get(1)+String.valueOf(symbol)+"~"+weatherFutrueDayTemplate.get(1)+String.valueOf(symbol));
        thirdDateTv.setText(futureWeather.get(2));
        thirdTemTv.setText(weatherFutrueDayWeather.get(2));
        thirdDuTv.setText(weatherFutrueNightTemplate.get(2)+String.valueOf(symbol)+"~"+weatherFutrueDayTemplate.get(2)+String.valueOf(symbol));
        fourthDateTv.setText(futureWeather.get(3));
        fourthTemTv.setText(weatherFutrueDayWeather.get(3));
        fourthDuTv.setText(weatherFutrueNightTemplate.get(3)+String.valueOf(symbol)+"~"+weatherFutrueDayTemplate.get(3)+String.valueOf(symbol));
		
		
		return data;

	}

	private void setDataToFragment(String temperatureStr,
			String airInfoStr, String airPMStr, String humidityStr,
			String visibilyStr, String windStr, String chuangyiMakStr,
			String chuanyiAdviceStr, String yundongMarkStr,
			String yundongAdaviceStr, String ganmaoMarkStr,
			String ganmaoAdaviceStr, String wuranMarkStr,
			String wuranAdaviceStr, String ziwaixianMarkStr,
			String ziyaixianAdaviceStr, String kongtiaoMarkStr,
			String kongtiaoAdaviceStr, String xicheMarkStr,
			String xicheAdaviceStr, List<WeatherFutrue> weatherFutrues) {
		// TODO Auto-generated method stub
		temperatureTv.setText(temperatureStr);
		airTv.setText(airInfoStr
				+ "|"
				+ airPMStr);
		weatherBgLl.setBackgroundResource(weatherPic.get(airInfoStr));
		humidityTv.setText(humidityStr);
		visibilyTv.setText(visibilyStr);
		windTv.setText(windStr);

		chuangyiMakTv.setText(chuangyiMakStr);
		chuanyiAdviceTv.setText(chuanyiAdviceStr);
		
		yundongMarkTv.setText(yundongMarkStr);
		yundongAdaviceTv.setText(yundongAdaviceStr);
		
		ganmaoMarkTv.setText(ganmaoMarkStr);
		ganmaoAdaviceTv.setText(ganmaoAdaviceStr);
		
		wuranMarkTv.setText(wuranMarkStr);
		wuranAdaviceTv.setText(wuranAdaviceStr);
		
		ziwaixianMarkTv.setText(ziwaixianMarkStr);
		ziyaixianAdaviceTv.setText(ziyaixianAdaviceStr);
		
		kongtiaoMarkTv.setText(kongtiaoMarkStr);
		kongtiaoAdaviceTv.setText(kongtiaoAdaviceStr);
		
		xicheMarkTv.setText(xicheMarkStr);
		xicheAdaviceTv.setText(xicheAdaviceStr);

		
		weatherFutrueDate = new ArrayList<String>();
		weatherFutrueDayWeather = new ArrayList<String>();
		weatherFutrueNightWeather = new ArrayList<String>();
		weatherFutrueDayTemplate = new ArrayList<String>();
		weatherFutrueNightTemplate = new ArrayList<String>();

		for (WeatherFutrue weatherFutrue : weatherFutrues) {

			weatherFutrueDate.add(weatherFutrue.getDate());

			weatherFutrueDayWeather.add(weatherFutrue.getInfo()
					.getDay().get(1));
			weatherFutrueNightWeather.add(weatherFutrue.getInfo()
					.getNight().get(1));
			weatherFutrueDayTemplate.add(weatherFutrue.getInfo()
					.getDay().get(2));
			weatherFutrueNightTemplate.add(weatherFutrue.getInfo()
					.getNight().get(2));
			if (weatherFutrueDate.size() == 5
					&& weatherFutrueDayWeather.size() == 5
					&& weatherFutrueNightWeather.size() == 5
					&& weatherFutrueDayTemplate.size() == 5
					&& weatherFutrueNightTemplate.size() == 5) {

				break;
				
			}
		}
		LineData mLineData = getLineData(weatherFutrueDate,
				weatherFutrueDayWeather, weatherFutrueNightWeather,
				weatherFutrueDayTemplate, weatherFutrueNightTemplate);
		showChart(chart, mLineData, Color.rgb(114, 188, 223));
	}
	private void refreshList() {
		// TODO Auto-generated method stub
		String cityName = cityWeatherPreferences.getString("cityweathername",
				null);
		if (cityName != null) {		
			cityTv.setText(cityName.substring(cityName.indexOf("-") + 1));
		}
		String tempStr=weatherPreferences.getString("temperatureStr", null);
		String airIfStr=weatherPreferences.getString("airInfoStr", null);
		String airPmStr=weatherPreferences.getString("airPMStr", null);
		String humiStr=weatherPreferences.getString("humidityStr", null);
		String visiStr=weatherPreferences.getString("visibilyStr", null);
		String winStr=weatherPreferences.getString("windStr", null);
		String chyMakStr=weatherPreferences.getString("chuangyiMakStr", null);
		String chyiAdviceStr=weatherPreferences.getString("chuanyiAdviceStr", null);
		String ydMarkStr=weatherPreferences.getString("yundongMarkStr", null);
		String ydAdaviceStr=weatherPreferences.getString("yundongAdaviceStr", null);
		String gmMarkStr=weatherPreferences.getString("ganmaoMarkStr", null);
		String gmAdaviceStr=weatherPreferences.getString("ganmaoAdaviceStr", null);
		String wrMarkStr=weatherPreferences.getString("wuranMarkStr", null);
		String wrAdaviceStr=weatherPreferences.getString("wuranAdaviceStr", null);
		String zwxMarkStr=weatherPreferences.getString("ziwaixianMarkStr", null);
		String zwxAdaviceStr=weatherPreferences.getString("ziyaixianAdaviceStr", null);
		String ktMarkStr=weatherPreferences.getString("kongtiaoMarkStr", null);
		String ktAdaviceStr=weatherPreferences.getString("kongtiaoAdaviceStr", null);
		String xchMarkStr=weatherPreferences.getString("xicheMarkStr", null);
		String xchAdaviceStr=weatherPreferences.getString("xicheAdaviceStr", null);
		String wf=weatherPreferences.getString("weatherFutrues", null);
		if(tempStr!=null&&airIfStr!=null&&airPmStr!=null&&humiStr!=null&&visiStr!=null&&winStr!=null&&wf!=null){
			setDataToFragment(tempStr,airIfStr,airPmStr,humiStr,visiStr,winStr,
					chyMakStr,chyiAdviceStr,ydMarkStr,ydAdaviceStr,
					gmMarkStr,gmAdaviceStr,wrMarkStr,wrAdaviceStr,
					zwxMarkStr,zwxAdaviceStr,ktMarkStr,ktAdaviceStr,
					xchMarkStr,xchAdaviceStr,PreferencesSaveList.String2SceneList(wf));
		}
	}
	
	
	
}
