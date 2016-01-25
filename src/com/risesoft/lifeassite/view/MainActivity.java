package com.risesoft.lifeassite.view;

import java.util.Timer;
import java.util.TimerTask;
import com.risesoft.lifeassite.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;


public class MainActivity extends Activity {

	SharedPreferences fristPreferences;
	Editor fristEditor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//…Ë÷√ ˙∆¡œ‘ æ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		fristPreferences=getSharedPreferences("welcome", 15);
		fristEditor=fristPreferences.edit();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
                if(fristPreferences.getBoolean("frist", true)){
    				Intent intent = new Intent(MainActivity.this,
    						WelcomActivity.class);
    				MainActivity.this.startActivity(intent);
    				fristEditor.putBoolean("frist", false);
    				fristEditor.commit();
    				MainActivity.this.finish();
                }else{
    				Intent sintent = new Intent(MainActivity.this,
    						SlideMenuActivity.class);
    				MainActivity.this.startActivity(sintent);
    				MainActivity.this.finish();
                }
			}
		}, 3000);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.finish();
	}
	
	
	
}
