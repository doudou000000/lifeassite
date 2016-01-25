package com.risesoft.lifeassite.view.disportfragment.movie;

import java.util.ArrayList;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.MovieListAdapter;
import com.risesoft.lifeassite.entity.movie.Movie;
import com.risesoft.lifeassite.entity.movie.MovieAllData;
import com.risesoft.lifeassite.entity.movie.MovieInfoData;
import com.risesoft.lifeassite.util.JuHeRequest;
import com.risesoft.lifeassite.util.MyApp;
import com.risesoft.lifeassite.util.OpenNetWork;
import com.risesoft.lifeassite.util.PreferencesSaveList;
import com.risesoft.lifeassite.view.SlideMenuActivity;
import com.risesoft.lifeassite.view.cityactivity.CitySearchLocationActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MovieFragment extends Fragment implements OnClickListener {

	View movieView;
	List<MovieInfoData> movieInfoDatas = new ArrayList<MovieInfoData>();
	List<String> movieStatusList = new ArrayList<String>();
	PullToRefreshListView movieListView;
	MovieListAdapter movieListAdapter;

	SharedPreferences movieInfoPreferences;
	Editor movieInfoEditor;

	SharedPreferences movieStatusPreferences;
	Editor movieStatusEditor;

	SharedPreferences cityMoviePreferences;

	RelativeLayout movieNoNetWorkRl;

	Button noNetBtn;
	ImageButton leftMenuBtn;

	TextView cityNameTv;

	ImageView movieSearchCityIv;

	String cityName;

	boolean isAutoRefresh=true;
	
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			try {
				switch (msg.what) {
				case 0:
					String movieResult = msg.obj.toString();
					if (movieResult != null) {
						Movie movie = JSON
								.parseObject(movieResult, Movie.class);
						movieNoNetWorkRl.setVisibility(View.GONE);
						movieListView.setVisibility(View.VISIBLE);
						if (movie.getResult() != null) {
							movieStatusList = getMovieStatus(movie.getResult()
									.getData());
							movieInfoDatas = getMovieInfoData(movie.getResult()
									.getData());
							movieListAdapter.getMovieListData(movieInfoDatas,
									movieStatusList);
							movieListAdapter.notifyDataSetChanged();
							MyApp.movieInfoDatas = movieInfoDatas;
							MyApp.movieStatusList = movieStatusList;
							movieInfoEditor.putString("movieInfoData",
									PreferencesSaveList
											.SceneList2String(movieInfoDatas));
							movieStatusEditor.putString("movieStatusData",
									PreferencesSaveList
											.SceneList2String(movieStatusList));
							movieInfoEditor.commit();
							movieStatusEditor.commit();
						} else {
							movieListAdapter.getMovieListData(new ArrayList<MovieInfoData>(),
									new ArrayList<String>());
							movieListAdapter.notifyDataSetChanged();
							Toast.makeText(getActivity(),
									"该城市没有影讯信息，请更换城市后继续查询", 1000).show();

						}
					}
					break;
				case -1:
					refreshList();
					break;

				default:
					break;
				}

			} catch (Exception e) {
				Toast.makeText(getActivity(), "服务器请求失败，请检查网络！！！", 500).show();
			}
			movieListView.postDelayed(new Runnable() {
				@Override
				public void run() {
					movieListView.onRefreshComplete();
				}
			}, 1000);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		movieView = LayoutInflater.from(getActivity()).inflate(
				R.layout.movie_layout, null);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		movieInfoPreferences = getActivity().getSharedPreferences("movieInfo",
				6);
		movieInfoEditor = movieInfoPreferences.edit();
		movieStatusPreferences = getActivity().getSharedPreferences(
				"movieStatus", 8);
		movieStatusEditor = movieStatusPreferences.edit();

		cityMoviePreferences = getActivity().getSharedPreferences("citymovie",
				11);

		cityNameTv = (TextView) movieView.findViewById(R.id.movie_city_name_tv);
		movieNoNetWorkRl = (RelativeLayout) movieView
				.findViewById(R.id.no_net_work_rl);
		movieSearchCityIv = (ImageView) movieView
				.findViewById(R.id.movie_search_city_iv);
		noNetBtn = (Button) movieView.findViewById(R.id.no_net_loading_btn);
		leftMenuBtn = (ImageButton) movieView
				.findViewById(R.id.movie_menu_left);
		movieListView = (PullToRefreshListView) movieView
				.findViewById(R.id.movie_list_view);
		movieListView.setMode(Mode.PULL_FROM_START);
		movieListAdapter = new MovieListAdapter(movieInfoDatas, getActivity(),
				movieStatusList);
		movieListView.setAdapter(movieListAdapter);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		noNetBtn.setOnClickListener(this);
		movieSearchCityIv.setOnClickListener(this);
		leftMenuBtn.setOnClickListener(this);
		movieListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				try {
					autoRefreshList();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		movieListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try {
					Uri uri = null;
					if (movieInfoDatas.get(position-1).getMore().getData().get(0)
							.getName().equals("选座购票")) {
						uri = Uri.parse(movieInfoDatas.get(position-1).getMore()
								.getData().get(0).getLink());
					} else {
						uri = Uri.parse(movieInfoDatas.get(position-1)
								.getIconlinkUrl());
					}
					Intent it = new Intent(Intent.ACTION_VIEW, uri);
					getActivity().startActivity(it);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.no_net_loading_btn:

				if (OpenNetWork.getConn()) {
					if (movieInfoPreferences.getString("movieInfoData", null) != null) {
						movieInfoEditor.clear();
						movieInfoEditor.commit();
					}
					if (movieStatusPreferences.getString("movieStatusData",
							null) != null) {
						movieStatusEditor.clear();
						movieStatusEditor.commit();
					}

					SendRequstToJuHe(cityName);
					
				} else {
					movieNoNetWorkRl.setVisibility(View.VISIBLE);
					movieListView.setVisibility(View.GONE);
				}
			

			break;
		case R.id.movie_menu_left:

			try {
				SlideMenuActivity.menu.toggle();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.movie_search_city_iv:

			try {
				Intent intent = new Intent(getActivity(),
						CitySearchLocationActivity.class);
				intent.putExtra("cityclass", 2);
				getActivity().startActivity(intent);
				isAutoRefresh=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			if (isAutoRefresh) {
				movieListView.postDelayed(new Runnable() {
					@Override
					public void run() {
						movieListView.setRefreshing(true);
					}
				}, 500);
			} else {
				refreshList();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void autoRefreshList() {
		cityName = cityMoviePreferences.getString("citymoviename", null);
		if (cityName != null) {
			cityName = cityName.substring(cityName.indexOf("-") + 1);
			cityNameTv.setText(cityName + "热映影片");
			if (OpenNetWork.getConn()) {
				if (movieInfoPreferences.getString("movieInfoData", null) != null) {
					movieInfoEditor.clear();
					movieInfoEditor.commit();
				}
				if (movieStatusPreferences.getString("movieStatusData", null) != null) {
					movieStatusEditor.clear();
					movieStatusEditor.commit();
				}
				SendRequstToJuHe(cityName);
			} else {
				noNetWorkRefereshView();
			}
			isAutoRefresh=false;
		} else {

			Intent intent = new Intent(getActivity(),
					CitySearchLocationActivity.class);
			intent.putExtra("cityclass", 2);
			getActivity().startActivity(intent);
		}
		movieListView.postDelayed(new Runnable() {
			@Override
			public void run() {
				movieListView.onRefreshComplete();
			}
		}, 500);
	}

	private void noNetWorkRefereshView() {
		// TODO Auto-generated method stub
		String movieInfoData = movieInfoPreferences.getString("movieInfoData",
				null);
		String movieStatusData = movieStatusPreferences.getString(
				"movieStatusData", null);
		if (movieInfoData != null && movieStatusData != null) {
			getPreferceMovieData(movieInfoData, movieStatusData);
			Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 500).show();
		} else {
			movieNoNetWorkRl.setVisibility(View.VISIBLE);
			movieListView.setVisibility(View.GONE);
		}
	}


	private void refreshList() {
		cityName = cityMoviePreferences.getString("citymoviename", null);
		if (cityName != null) {
			cityName = cityName.substring(cityName.indexOf("-") + 1);
			cityNameTv.setText(cityName + "热映影片");
		}
		movieNoNetWorkRl.setVisibility(View.GONE);
		movieListView.setVisibility(View.VISIBLE);
		String movieInfoData = movieInfoPreferences.getString("movieInfoData",
				null);
		String movieStatusData = movieStatusPreferences.getString(
				"movieStatusData", null);
		if (MyApp.movieInfoDatas != null && MyApp.movieInfoDatas.size() > 0) {
			getMyAppMovieData();
		} else if (movieInfoData != null && movieStatusData != null) {
			getPreferceMovieData(movieInfoData, movieStatusData);

		} else {
			if(!OpenNetWork.getConn()){
				movieNoNetWorkRl.setVisibility(View.VISIBLE);
				movieListView.setVisibility(View.GONE);
			}else{
				Toast.makeText(getActivity(), "没有查询到数据，请重新选择城市或检查网络", 500).show();
			}
		}
		movieListView.postDelayed(new Runnable() {
			@Override
			public void run() {
				movieListView.onRefreshComplete();
			}
		}, 500);
	}

	private void SendRequstToJuHe(String cityName) {
		// TODO Auto-generated method stub
		Parameters params = new Parameters();
		params.add("city", cityName);
		params.add("dtype", "json");
		try {
			JuHeRequest.getJuHeData(params, getActivity(), 94,
					"http://op.juhe.cn/onebox/movie/pmovie", JuheData.GET,
					handler, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<MovieInfoData> getMovieInfoData(List<MovieAllData> movieAllDatas) {

		List<MovieInfoData> newMovieInfoDatas1 = movieAllDatas.get(0).getData();
		newMovieInfoDatas1.addAll(movieAllDatas.get(1).getData());
		return newMovieInfoDatas1;

	}

	public List<String> getMovieStatus(List<MovieAllData> movieAllDatas) {

		List<String> movieStatusList = new ArrayList<String>();
		List<MovieInfoData> newMovieInfoDatas1 = movieAllDatas.get(0).getData();
		List<MovieInfoData> newMovieInfoDatas2 = movieAllDatas.get(1).getData();

		for (int i = 0; i < newMovieInfoDatas1.size(); i++) {
			movieStatusList.add("正在上映");
		}
		for (int i = 0; i < newMovieInfoDatas2.size(); i++) {
			movieStatusList.add("即将上映");
		}
		return movieStatusList;

	}

	public void getMyAppMovieData() {
		List<MovieInfoData> appMovieInfoDatas = MyApp.movieInfoDatas;
		List<String> appMovieStatusList = MyApp.movieStatusList;
		movieListAdapter
				.getMovieListData(appMovieInfoDatas, appMovieStatusList);
		movieListAdapter.notifyDataSetChanged();
	}

	public void getPreferceMovieData(String movieInfoData,
			String movieStatusData) {

		List<MovieInfoData> showSceneMovieInfoList = PreferencesSaveList
				.String2SceneList(movieInfoData);
		List<String> showSceneMovieStatusList = PreferencesSaveList
				.String2SceneList(movieStatusData);
		movieListAdapter.getMovieListData(showSceneMovieInfoList,
				showSceneMovieStatusList);
		movieListAdapter.notifyDataSetChanged();

	}

}
