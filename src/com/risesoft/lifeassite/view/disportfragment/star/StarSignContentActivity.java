package com.risesoft.lifeassite.view.disportfragment.star;

import java.util.ArrayList;
import java.util.List;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.util.MyApp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class StarSignContentActivity extends FragmentActivity implements OnClickListener{

	List<Fragment> fragmentList;
	
	ViewPager mViewPager;
	
	RadioButton weekFortuneRb,monthFortuneRb,yearFortuneRb;
	
	StarWeekFragment starWeekFragment;
	StarMonthFragment starMonthFragment;
	StarYearFragment starYearFragment;
	int currenttab=-1;
	TextView starSignNameTv;
    LinearLayout backLl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去除自带title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.star_sign_content_layout);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		MyApp.starSignName=getIntent().getStringExtra("starSignName");
		fragmentList=new ArrayList<Fragment>();
		starWeekFragment=new StarWeekFragment();
		starMonthFragment=new StarMonthFragment();
		starYearFragment=new StarYearFragment();
		fragmentList.add(starWeekFragment);
		fragmentList.add(starMonthFragment);
		fragmentList.add(starYearFragment);
		
		backLl=(LinearLayout)findViewById(R.id.star_sign_content_back_ll);
		starSignNameTv=(TextView)findViewById(R.id.star_sign_name_tv);
		weekFortuneRb=(RadioButton)findViewById(R.id.star_sign_cotent_tb_week);
		monthFortuneRb=(RadioButton)findViewById(R.id.star_sign_cotent_tb_month);
		yearFortuneRb=(RadioButton)findViewById(R.id.star_sign_cotent_tb_year);
		mViewPager=(ViewPager) findViewById(R.id.star_sign_cotent_view_pager);
		
		starSignNameTv.setText(MyApp.starSignName);
		mViewPager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		backLl.setOnClickListener(this);
		weekFortuneRb.setOnClickListener(this);
		monthFortuneRb.setOnClickListener(this);
		yearFortuneRb.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int page) {
				// TODO Auto-generated method stub
				try {
					switch (page) {  
	                case 0:  
	                	weekFortuneRb.setChecked(true); 
	                	starWeekFragment.getStarWeekData();
	                    break;  
	                case 1:  
	                	monthFortuneRb.setChecked(true); 
	                	starMonthFragment.getStarMonthData();
	                    break;  
	                case 2:  
	                	yearFortuneRb.setChecked(true);  
	                	starYearFragment.getStarYearData();
	                    break;    
	                default:  
	                    break;  
	                } 
				} catch (Exception e) {
					e.printStackTrace();
				}
 
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.star_sign_cotent_tb_week:
			changeView(0);
			break;
		case R.id.star_sign_cotent_tb_month:
			changeView(1);
			break;
		case R.id.star_sign_cotent_tb_year:
			changeView(2);
			break;
		case R.id.star_sign_content_back_ll:
			finish();
			break;
		default:
			break;
		}
	}
	private void changeView(int desTab)
	{
		mViewPager.setCurrentItem(desTab, true);
	}
	class MyFrageStatePagerAdapter extends FragmentPagerAdapter
	{

		public MyFrageStatePagerAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.finish();
	}
	
}
