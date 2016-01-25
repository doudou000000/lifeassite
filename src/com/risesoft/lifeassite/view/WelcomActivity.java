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

	// ����ViewPager����
		private ViewPager viewPager;
		// ����ViewPager������
		private WelcomeViewPagerAdapter vpAdapter;
		// ����һ��ArrayList�����View
		private ArrayList<View> views;
		// ����ͼƬ��Դ
//		private static final int[] pics = { R.drawable.life_welcome_bg, R.drawable.study_welcome_bg,
//				R.drawable.disport_welcome_bg};
		// �ײ�С���ͼƬ
		private ImageView[] points;
		// ��¼��ǰѡ��λ��
		private int currentIndex;
		Button welcomeToMainBtn;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE); //ȥ���Դ�title
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//����
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
		 * ��ʼ�����
		 */
		private void initView() {
			// ʵ����ArrayList����
			views = new ArrayList<View>();
			// ʵ����ViewPager
			viewPager = (ViewPager) findViewById(R.id.welcome_viewpager);
			// ʵ����ViewPager������
			vpAdapter = new WelcomeViewPagerAdapter(views);
		}

		/**
		 * ��ʼ������
		 */
		private void initData() {

             View lifeView=LayoutInflater.from(WelcomActivity.this).inflate(R.layout.welcome_view_life, null);
             View studyView=LayoutInflater.from(WelcomActivity.this).inflate(R.layout.welcome_view_study, null);
             View disportView=LayoutInflater.from(WelcomActivity.this).inflate(R.layout.welcome_view_disport, null);
			// ��ʼ������ͼƬ�б�

             views.add(lifeView);
             views.add(studyView);
             views.add(disportView);
			// ��������
			viewPager.setAdapter(vpAdapter);
			// ���ü���
			viewPager.setOnPageChangeListener(this);

			// ��ʼ���ײ�С��
			initPoint();
			welcomeToMainBtn=(Button)disportView.findViewById(R.id.welcome_to_main_btn);
		}

		/**
		 * ��ʼ���ײ�С��
		 */
		private void initPoint() {
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.welcome_ll);

			points = new ImageView[3];

			// ѭ��ȡ��С��ͼƬ
			for (int i = 0; i < 3; i++) {
				// �õ�һ��LinearLayout�����ÿһ����Ԫ��
				points[i] = (ImageView) linearLayout.getChildAt(i);
				// Ĭ�϶���Ϊ��ɫ
				points[i].setEnabled(true);
				// ��ÿ��С�����ü���
				//points[i].setOnClickListener(this);
				// ����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
				points[i].setTag(i);
			}

			// ���õ���Ĭ�ϵ�λ��
			currentIndex = 0;
			// ����Ϊ��ɫ����ѡ��״̬
			points[currentIndex].setEnabled(false);
		}

		/**
		 * ����״̬�ı�ʱ����
		 */
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		/**
		 * ��ǰҳ�滬��ʱ����
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		/**
		 * �µ�ҳ�汻ѡ��ʱ����
		 */
		@Override
		public void onPageSelected(int arg0) {
			// ���õײ�С��ѡ��״̬
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
		 * ���õ�ǰҳ���λ��
		 */
		private void setCurView(int position) {
			if (position < 0 || position >= 3) {
				return;
			}
			viewPager.setCurrentItem(position);
		}

		/**
		 * ���õ�ǰ��С���λ��
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
