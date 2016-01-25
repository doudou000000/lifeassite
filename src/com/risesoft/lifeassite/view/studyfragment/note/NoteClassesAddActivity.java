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
 * ����������ҳ��
 * @author Administrator
 *
 */
public class NoteClassesAddActivity extends Activity {

	EditText classesEt;//�������
	
	TextView saveClassesIv;//�������ť
	
	ClassesService classesService;//����������ݿ�
	
	ImageButton backIb;//���ذ�ť
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//����ȥ��Android�Դ�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//����������ʾ
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
	 * ��ʼ������
	 */
	private void initView() {
		// TODO Auto-generated method stub
		classesEt=(EditText)this.findViewById(R.id.add_classes_name_et);
		saveClassesIv=(TextView)this.findViewById(R.id.zi_liao_calsses_add_save);
		backIb=(ImageButton)this.findViewById(R.id.add_classes_back_iv);
	}
	
	/**
	 * �����¼�
	 */
	private void initListener() {
		// �����¼�
		backIb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// �رո�activity
				NoteClassesAddActivity.this.finish();
			}
		});
		//�����¼�
		saveClassesIv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ����𱣴������ݿ�
				classesService=new ClassesService(NoteClassesAddActivity.this);
				Classes classes=new Classes();
				classes.setClassesId(UUID.randomUUID().toString());
				classes.setName(classesEt.getText().toString());				
				if(classesService.insert(classes).equals("����ɹ���")){
					Toast.makeText(NoteClassesAddActivity.this, "����ɹ���", 2000).show();
				}
			}
		});
	}
	
	
}
