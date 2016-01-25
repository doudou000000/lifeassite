package com.risesoft.lifeassite.view.studyfragment.note;

import java.util.UUID;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.db.service.ClassesService;
import com.risesoft.lifeassite.entity.note.Classes;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 添加文章类别页面
 * @author Administrator
 *
 */
public class NoteClassesAddActivity extends Activity {

	EditText classesEt;//类别名称
	
	TextView saveClassesIv;//保存类别按钮
	
	ClassesService classesService;//文章类别数据库
	
	ImageButton backIb;//返回按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//设置去除Android自带标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置竖屏显示
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_add_classes);
		try {			
			initView();
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	/**
	 * 初始化数据
	 */
	private void initView() {
		// TODO Auto-generated method stub
		classesEt=(EditText)this.findViewById(R.id.add_classes_name_et);
		saveClassesIv=(TextView)this.findViewById(R.id.zi_liao_calsses_add_save);
		backIb=(ImageButton)this.findViewById(R.id.add_classes_back_iv);
	}
	
	/**
	 * 监听事件
	 */
	private void initListener() {
		// 返回事件
		backIb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 关闭该activity
				NoteClassesAddActivity.this.finish();
			}
		});
		//保存事件
		saveClassesIv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 将类别保存至数据库
				classesService=new ClassesService(NoteClassesAddActivity.this);
				Classes classes=new Classes();
				classes.setClassesId(UUID.randomUUID().toString());
				classes.setName(classesEt.getText().toString());				
				if(classesService.insert(classes).equals("保存成功！")){
					Toast.makeText(NoteClassesAddActivity.this, "保存成功！", 2000).show();
				}
			}
		});
	}
	
	
}
