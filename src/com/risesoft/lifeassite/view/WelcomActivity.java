package com.risesoft.lifeassite.view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.WelcomeViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class WelcomActivity extends Activity implements OnClickListener,
OnPageChangeListener{

	// 定义ViewPager对象
		private ViewPager viewPager;
		// 定义ViewPager适配器
		private WelcomeViewPagerAdapter vpAdapter;
		// 定义一个ArrayList来存放View
		private ArrayList<View> views;
		// 引导图片资源
//		private static final int[] pics = { R.drawable.life_welcome_bg, R.drawable.study_welcome_bg,
//				R.drawable.disport_welcome_bg};
		// 底部小点的图片
		private ImageView[] points;
		// 记录当前选中位置
		private int currentIndex;
		Button welcomeToMainBtn;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE); //去除自带title
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
			setContentView(R.layout.welcome_layout);
			try {
				initView();
				initData();
				initListener();				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void initListener() {
			// TODO Auto-generated method stub
			welcomeToMainBtn.setOnClickListener(this);
		}

		/**
		 * 初始化组件
		 */
		private void initView() {
			// 实例化ArrayList对象
			views = new ArrayList<View>();
			// 实例化ViewPager
			viewPager = (ViewPager) findViewById(R.id.welcome_viewpager);
			// 实例化ViewPager适配器
			vpAdapter = new WelcomeViewPagerAdapter(views);
		}

		/**
		 * 初始化数据
		 */
		private void initData() {

             View lifeView=LayoutInflater.from(WelcomActivity.this).inflate(R.layout.welcome_view_life, null);
             View studyView=LayoutInflater.from(WelcomActivity.this).inflate(R.layout.welcome_view_study, null);
             View disportView=LayoutInflater.from(WelcomActivity.this).inflate(R.layout.welcome_view_disport, null);
			// 初始化引导图片列表

             views.add(lifeView);
             views.add(studyView);
             views.add(disportView);
			// 设置数据
			viewPager.setAdapter(vpAdapter);
			// 设置监听
			viewPager.setOnPageChangeListener(this);

			// 初始化底部小点
			initPoint();
			welcomeToMainBtn=(Button)disportView.findViewById(R.id.welcome_to_main_btn);
		}

		/**
		 * 初始化底部小点
		 */
		private void initPoint() {
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.welcome_ll);

			points = new ImageView[3];

			// 循环取得小点图片
			for (int i = 0; i < 3; i++) {
				// 得到一个LinearLayout下面的每一个子元素
				points[i] = (ImageView) linearLayout.getChildAt(i);
				// 默认都设为灰色
				points[i].setEnabled(true);
				// 给每个小点设置监听
				//points[i].setOnClickListener(this);
				// 设置位置tag，方便取出与当前位置对应
				points[i].setTag(i);
			}

			// 设置当面默认的位置
			currentIndex = 0;
			// 设置为白色，即选中状态
			points[currentIndex].setEnabled(false);
		}

		/**
		 * 滑动状态改变时调用
		 */
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		/**
		 * 当前页面滑动时调用
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		/**
		 * 新的页面被选中时调用
		 */
		@Override
		public void onPageSelected(int arg0) {
			// 设置底部小点选中状态
			setCurDot(arg0);
		}

		@Override
		public void onClick(View v) {
//			int position = (Integer) v.getTag();
//			setCurView(position);
//			setCurDot(position);
			switch (v.getId()) {
			case R.id.welcome_to_main_btn:

						Intent intent = new Intent(WelcomActivity.this,
								SlideMenuActivity.class);
						WelcomActivity.this.startActivity(intent);
						WelcomActivity.this.finish();
				break;

			default:
				break;
			}

		}

		/**
		 * 设置当前页面的位置
		 */
		private void setCurView(int position) {
			if (position < 0 || position >= 3) {
				return;
			}
			viewPager.setCurrentItem(position);
		}

		/**
		 * 设置当前的小点的位置
		 */
		private void setCurDot(int positon) {
			if (positon < 0 || positon > 3 - 1 || currentIndex == positon) {
				return;
			}
			points[positon].setEnabled(false);
			points[currentIndex].setEnabled(true);

			currentIndex = positon;
		}
		
}
