package com.risesoft.lifeassite.view.disportfragment.joke;

import java.util.ArrayList;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.JokeListAdapter;
import com.risesoft.lifeassite.entity.joke.Joke;
import com.risesoft.lifeassite.entity.joke.JokeResultData;
import com.risesoft.lifeassite.util.JuHeRequest;
import com.risesoft.lifeassite.util.OpenNetWork;
import com.risesoft.lifeassite.view.SlideMenuActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class JokeFragment extends Fragment implements OnClickListener {

	View JokeView;
	View JokeBottomView;
	RelativeLayout JokeBottomRl;
	TextView JokeBottomTv;
	List<JokeResultData> jokeResultDatas = new ArrayList<JokeResultData>();
	JokeListAdapter jokeListAdapter;
	PullToRefreshListView jokeListView;
	int page = 0;
	int pageSize = 20;
	RelativeLayout jokeNoNetWorkRl;
	Button noNetBtn;
	ImageButton leftMenuBtn;
	SharedPreferences jokePreferences;
	Editor jokeEditor;

	ListView lv;

	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			try {
				switch (msg.what) {
				case 0:
					String jokeResult = msg.obj.toString();
					if (jokeResult != null) {
						Joke joke = JSON.parseObject(jokeResult, Joke.class);
						jokeNoNetWorkRl.setVisibility(View.GONE);
						jokeListView.setVisibility(View.VISIBLE);
						if (joke.getResult() != null) {
							List<JokeResultData> newJokeResultDatas = joke
									.getResult().getData();
							jokeResultDatas.addAll(newJokeResultDatas);
							jokeListAdapter.getJokeResultDatas(jokeResultDatas);
							jokeListAdapter.notifyDataSetChanged();

						} else {

							Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！",
									1000).show();
						}
					} else {
						jokeNoNetWorkRl.setVisibility(View.VISIBLE);
						jokeListView.setVisibility(View.GONE);
					}
					break;

				default:
					break;
				}

			} catch (Exception e) {
				Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 500);
			}
			jokeListView.postDelayed(new Runnable() {
				@Override
				public void run() {
					jokeListView.onRefreshComplete();
				}
			}, 500);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		JokeView = LayoutInflater.from(getActivity()).inflate(
				R.layout.joke_layout, null);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return JokeView;
	}

	private void initListener() {
		// TODO Auto-generated method stub
		// JokeBottomRl.setOnClickListener(this);

		noNetBtn.setOnClickListener(this);
		leftMenuBtn.setOnClickListener(this);

		jokeListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				try {
					page = 0;
					jokeResultDatas = new ArrayList<JokeResultData>();
					refreshJokeData();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				try {
					refreshJokeData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		// lv.setOnScrollListener(new OnScrollListener(){
		//
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState) {
		// // TODO Auto-generated method stub
		// // 当不滚动时
		// if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
		// // 判断是否滚动到底部
		// if (view.getLastVisiblePosition() == view.getCount() - 1) {
		// //加载更多功能的代码
		// JokeBottomRl.setVisibility(View.VISIBLE);
		// JokeBottomTv.setText("点击加载更多");
		// }
		// }
		// }
		//
		// @Override
		// public void onScroll(AbsListView view, int firstVisibleItem,
		// int visibleItemCount, int totalItemCount) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// });
	}

	private void initView() {
		// TODO Auto-generated method stub
		jokePreferences = getActivity().getSharedPreferences("joke", 7);
		jokeEditor = jokePreferences.edit();

		jokeNoNetWorkRl = (RelativeLayout) JokeView
				.findViewById(R.id.no_net_work_rl);
		noNetBtn = (Button) JokeView.findViewById(R.id.no_net_loading_btn);
		leftMenuBtn = (ImageButton) JokeView.findViewById(R.id.joke_menu_left);
		// JokeBottomView=LayoutInflater.from(getActivity()).inflate(R.layout.joke_list_bottom,
		// null);
		// JokeBottomRl=(RelativeLayout)JokeBottomView.findViewById(R.id.joke_list_bottom_rl);
		// JokeBottomTv=(TextView)JokeBottomView.findViewById(R.id.joke_list_bottom_tv);
		jokeListView = (PullToRefreshListView) JokeView
				.findViewById(R.id.joke_all_list_view);
		jokeListView.setMode(Mode.BOTH);

		jokeListAdapter = new JokeListAdapter(jokeResultDatas, getActivity());

		jokeListView.setAdapter(jokeListAdapter);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		try {

			jokeListView.postDelayed(new Runnable() {
				@Override
				public void run() {
					jokeListView.setRefreshing(true);
				}
			}, 500);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void refreshJokeData() {
		// TODO Auto-generated method stub
		page++;
		if (OpenNetWork.getConn()) {
			Parameters params = new Parameters();
			params.add("page", page);
			params.add("pagesize", pageSize);
			params.add("dtype", "json");
			JuHeRequest.getJuHeData(params, getActivity(), 95,
					"http://japi.juhe.cn/joke/content/text.from", JuheData.GET,
					handler, 0);
		} else {
			if (jokeResultDatas != null && jokeResultDatas.size() > 0) {
				jokeListAdapter.getJokeResultDatas(jokeResultDatas);
				jokeListAdapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 500);
				jokeListView.postDelayed(new Runnable() {
					@Override
					public void run() {
						jokeListView.onRefreshComplete();
					}
				}, 500);
			} else {
				jokeNoNetWorkRl.setVisibility(View.VISIBLE);
				jokeListView.setVisibility(View.GONE);

			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// case R.id.joke_list_bottom_rl:
		// JokeBottomTv.setText("正在加载......");
		// page++;
		// try {
		// Parameters params=new Parameters();
		// params.add("page", page);
		// params.add("pagesize", pageSize);
		// params.add("dtype", "json");
		// JuHeRequest.getJuHeData(params, getActivity(), 95,
		// "http://japi.juhe.cn/joke/content/text.from", JuheData.GET, handler,
		// 0);
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		// break;
		case R.id.no_net_loading_btn:
			page = 0;
			try {
				refreshJokeData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.joke_menu_left:
			
			try {
				SlideMenuActivity.menu.toggle();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

}
