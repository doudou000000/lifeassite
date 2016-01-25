package com.risesoft.lifeassite.view.morefragment;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.NewsListAdapter;
import com.risesoft.lifeassite.entity.news.NewsResult;
import com.risesoft.lifeassite.entity.news.NewsSearch;
import com.risesoft.lifeassite.entity.news.RealTimeNews;
import com.risesoft.lifeassite.util.JuHeRequest;
import com.risesoft.lifeassite.util.OpenNetWork;
import com.risesoft.lifeassite.util.PreferencesSaveList;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class NewsActivity extends Activity {

	
	PullToRefreshListView newsListView;
	List<NewsResult> newsSearchs1;
	NewsListAdapter newsListAdapter;
	SharedPreferences preferences;
	Editor editor;
	RelativeLayout newsNoNetWorkRl;
	List<String> realTimeNewLists;
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			try {
				switch (msg.what) {
				case 0:
					String realtimeJsonResult=msg.obj.toString();
					if(realtimeJsonResult!=null){
						RealTimeNews realTimeNews = JSON
								.parseObject(realtimeJsonResult, RealTimeNews.class);
						if(realTimeNews.getResult()!=null&&realTimeNews.getResult().size()>0){
							realTimeNewLists=new ArrayList<String>();
							newsSearchs1=new ArrayList<NewsResult>();
							realTimeNewLists=realTimeNews.getResult();
							getNewsListDatas(realTimeNewLists);

						}else{
							Toast.makeText(NewsActivity.this, "查询数据失败，请稍后重试", 500).show();							
						}
					}else{
						Toast.makeText(NewsActivity.this, "数据加载失败，请检查网络！！！", 500).show();
					}
					break;
				case 1:
					String newsDataJsonResult=msg.obj.toString();
					setNewsDataToListView(newsDataJsonResult);
					if(newsSearchs1!=null&&newsSearchs1.size()>0){
						newsListAdapter.setNewsResults(newsSearchs1);
						newsListAdapter.notifyDataSetChanged();
						editor.putString("newsdata",
								PreferencesSaveList
										.SceneList2String(newsSearchs1));
						editor.commit();
					}
					break;
				case -1:
					Toast.makeText(NewsActivity.this, "数据加载失败，请检查网络！！！", 500).show();
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			newsListView.postDelayed(new Runnable() {
				@Override
				public void run() {
					newsListView.onRefreshComplete();
				}
			}, 1000);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去除自带title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.news_layout);	
		try {
			initView();
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		preferences=getSharedPreferences("news", 13);
		editor=preferences.edit();
		newsNoNetWorkRl = (RelativeLayout)this
				.findViewById(R.id.no_net_work_rl);
		newsListView=(PullToRefreshListView)this.findViewById(R.id.news_list_view);
		newsSearchs1=new ArrayList<NewsResult>();
		newsListAdapter=new NewsListAdapter(this,newsSearchs1);
		newsListView.setMode(Mode.PULL_FROM_START);
		newsListView.setAdapter(newsListAdapter);
	}
	private void initListener() {
		// TODO Auto-generated method stub
		newsListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				try {					
					getNewsRealTimeDatas();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			newsListView.postDelayed(new Runnable() {
				@Override
				public void run() {
					newsListView.setRefreshing(true);
				}
			}, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getNewsRealTimeDatas() {
		// TODO Auto-generated method stub
		if(OpenNetWork.getConn()){
        	editor.clear();
        	editor.commit();
			SendRequstToJuHeRealTime();
		}else{
			String newsJson=preferences.getString("newsdata", null);
			if(newsJson!=null){
				List<NewsResult> saveNewsResults=PreferencesSaveList.String2SceneList(newsJson);
				newsListAdapter.setNewsResults(saveNewsResults);
				newsListAdapter.notifyDataSetChanged();
			}else{
				newsNoNetWorkRl.setVisibility(View.VISIBLE);
				newsListView.setVisibility(View.GONE);
			}
		}
	}
	
	private void getNewsListDatas(List<String> realTimeNewLists) {
		// TODO Auto-generated method stub
		newsNoNetWorkRl.setVisibility(View.GONE);
		newsListView.setVisibility(View.VISIBLE);
		if(OpenNetWork.getConn()){
			for(String realTimeNew:realTimeNewLists){
				SendRequstToJuHeNewsDatas(realTimeNew);			
			}
		}else{
				newsNoNetWorkRl.setVisibility(View.VISIBLE);
				newsListView.setVisibility(View.GONE);
		}
	}
	

	private void SendRequstToJuHeNewsDatas(String realTimeNew) {
		// TODO Auto-generated method stub
		try {	
			Parameters params=new Parameters();
			params.add("q", realTimeNew);
			params.add("dtype", "json");
			JuHeRequest.getJuHeData(params, this, 138, "http://op.juhe.cn/onebox/news/query", JuheData.GET, handler, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void SendRequstToJuHeRealTime() {
		// TODO Auto-generated method stub
		try {	
			Parameters params=new Parameters();
			params.add("dtype", "json");
			JuHeRequest.getJuHeData(params, this, 138, "http://op.juhe.cn/onebox/news/words", JuheData.GET, handler, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void setNewsDataToListView(String newsJson) {
		// TODO Auto-generated method stub
		if(newsJson!=null){
			NewsSearch newsSearch = JSON
					.parseObject(newsJson, NewsSearch.class);
			if(newsSearch.getResult()!=null){
				List<NewsResult> newsResults=newsSearch.getResult();
				for(NewsResult newsResult:newsResults){
					if(newsResult.getImg()!=null){
						newsSearchs1.add(newsResult);
					}
				}
			}else{
				Toast.makeText(NewsActivity.this, "查询数据失败，请稍后重试", 500).show();							
			}
		}else{
			Toast.makeText(NewsActivity.this, "数据加载失败，请检查网络！！！", 500).show();
		}
	}
}
