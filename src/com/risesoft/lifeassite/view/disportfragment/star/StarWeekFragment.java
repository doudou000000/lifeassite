package com.risesoft.lifeassite.view.disportfragment.star;

import com.alibaba.fastjson.JSON;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.starsign.WeekSatrSign;
import com.risesoft.lifeassite.util.JuHeRequest;
import com.risesoft.lifeassite.util.MyApp;
import com.risesoft.lifeassite.util.ProgressDialogUtil;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class StarWeekFragment extends Fragment {

	View starWeekView;
	
	TextView weekfortuneResultTv;
	TextView weekfortuneDateTv;
	boolean isCreated=true;
	ProgressDialog dialog;
	Handler handler=new Handler(){
		
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			
			try {
				switch (msg.what) {
				case 0:
					String weekfortuneResult=msg.obj.toString();
					if(weekfortuneResult!=null){
					    WeekSatrSign weekSatrSign=JSON.parseObject(weekfortuneResult, WeekSatrSign.class);
					    if(weekSatrSign.getResultcode().equals("200")){
					    	weekfortuneDateTv.setText(weekSatrSign.getDate().replace("年", "/").replace("月", "/").replace("日", ""));
					    	String health=weekSatrSign.getHealth().substring(0,weekSatrSign.getHealth().indexOf("作者："));
					    	String love=weekSatrSign.getLove();
					    	String money=weekSatrSign.getMoney();
					    	String work=weekSatrSign.getWork();
					    	String job=weekSatrSign.getJob();
					    	weekfortuneResultTv.setText(health
					    			+"\n\n"+love
					    			+"\n\n"+money
					    			+"\n\n"+work
					    			+"\n\n"+job);
					    }else{
					       
					    	Toast.makeText(getActivity(), "没有查询到该星座的信息，请稍后重试", 1000).show();
					    	
					    }
					}else{
						Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 1000).show();
					}

					break;
				case -1:
				    Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 1000).show();
					break;
				default:
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			}, 500);
		}
		
	};
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		starWeekView=LayoutInflater.from(getActivity()).inflate(R.layout.star_sign_fortune_layout, null);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return starWeekView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		weekfortuneResultTv=(TextView)starWeekView.findViewById(R.id.star_sign_fortune_tv);
		weekfortuneDateTv=(TextView)starWeekView.findViewById(R.id.star_sign_fortune_date);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isCreated){
			getStarWeekData();
			isCreated=false;
		}	
	}
	public void getStarWeekData(){
		try {
			dialog=ProgressDialogUtil.getProgress(getActivity(),"加载中...");
			dialog.show();
			Parameters params=new Parameters();
			params.add("consName", MyApp.starSignName);
			params.add("type", "week");
			JuHeRequest.getJuHeData(params, getActivity(), 58, "http://web.juhe.cn:8080/constellation/getAll", JuheData.GET, handler, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
