package com.risesoft.lifeassite.view;

import java.util.Timer;
import java.util.TimerTask;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.view.disportfragment.DisportFragment;
import com.risesoft.lifeassite.view.lifefragment.LifeFragment;
import com.risesoft.lifeassite.view.morefragment.MoreFragment;
import com.risesoft.lifeassite.view.studyfragment.SdutyFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

public class SlideMenuActivity extends SlidingFragmentActivity implements OnClickListener{

	Fragment mContext;
	//RelativeLayout studyRl,lifeRl,disportRl,moreRl;
	RadioButton studyRb,lifeRb,disportRb,moreRb;
	public static SlidingMenu menu;
	private static boolean FINISH = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去除自带title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.slide_menu_content);
		initSlideMenu();
		initView();
		initListener();
	}


	private void initView() {
		// TODO Auto-generated method stub
//		studyRl=(RelativeLayout)findViewById(R.id.slide_rl_study);
//		lifeRl=(RelativeLayout)findViewById(R.id.slide_rl_life);
//		disportRl=(RelativeLayout)findViewById(R.id.slide_rl_disport);
//		moreRl=(RelativeLayout)findViewById(R.id.slide_rl_more);
		studyRb=(RadioButton)findViewById(R.id.slide_rb_study);
		lifeRb=(RadioButton)findViewById(R.id.slide_rb_life);
		disportRb=(RadioButton)findViewById(R.id.slide_rb_disport);
		moreRb=(RadioButton)findViewById(R.id.slide_rb_more);
	}

	private void initSlideMenu() {
		// TODO Auto-generated method stub 
        setBehindContentView(R.layout.slide_menu_left);  
        getSupportFragmentManager().beginTransaction()  
                .replace(R.id.content_frame, new LifeFragment()).commit(); 
		menu = getSlidingMenu();  			
        menu.setMode(SlidingMenu.LEFT);  
        // 设置触摸屏幕的模式  
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);  
        menu.setShadowWidthRes(R.dimen.shadow_width);  
        menu.setShadowDrawable(R.drawable.shadow);  
        // 设置滑动菜单视图的宽度  
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);  
        // 设置渐入渐出效果的值  
        menu.setFadeDegree(0.35f);  
	}

	private void initListener() {
		// TODO Auto-generated method stub
//		studyRl.setOnClickListener(this);
//		lifeRl.setOnClickListener(this);
//		disportRl.setOnClickListener(this);
//		moreRl.setOnClickListener(this);
		studyRb.setOnClickListener(this);
		lifeRb.setOnClickListener(this);
		disportRb.setOnClickListener(this);
		moreRb.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.slide_rb_study:
			studyRb.setChecked(true);
			lifeRb.setChecked(false);
			disportRb.setChecked(false);
			moreRb.setChecked(false);
	        getSupportFragmentManager().beginTransaction()  
            .replace(R.id.content_frame, new SdutyFragment()).commit(); 
			break;
		case R.id.slide_rb_life:
			studyRb.setChecked(false);
			lifeRb.setChecked(true);
			disportRb.setChecked(false);
			moreRb.setChecked(false);
	        getSupportFragmentManager().beginTransaction()  
            .replace(R.id.content_frame, new LifeFragment()).commit(); 
			break;
		case R.id.slide_rb_disport:
			disportRb.setChecked(true);
			studyRb.setChecked(false);
			lifeRb.setChecked(false);
			moreRb.setChecked(false);
	        getSupportFragmentManager().beginTransaction()  
            .replace(R.id.content_frame, new DisportFragment()).commit(); 
			break;
		case R.id.slide_rb_more:
			moreRb.setChecked(true);
			disportRb.setChecked(false);
			studyRb.setChecked(false);
			lifeRb.setChecked(false);
	        getSupportFragmentManager().beginTransaction()  
            .replace(R.id.content_frame, new MoreFragment()).commit(); 
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			// final File file = new
			// File("/data/data/com.risoft.xichengdemo/databases/datainfo.db");
			if (!FINISH) {
				Toast
						.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				FINISH = true;
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						FINISH = false;
						// RecursionDeleteFile(file);
					}
				}, 5000);
			} else {
				return super.dispatchKeyEvent(event);
			}
			return true;
		} else if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
			return false;
		}
		return super.dispatchKeyEvent(event);

	}	
}
