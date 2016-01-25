package com.risesoft.lifeassite.view.studyfragment.note;

import java.util.ArrayList;
import java.util.List;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.ZiLiaoAdapter;
import com.risesoft.lifeassite.db.service.ZiLiaoService;
import com.risesoft.lifeassite.entity.note.ZiLiao;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * ȫ�ļ���
 * @author Administrator
 *
 */
public class NoteSearchDataActivity extends Activity {

	EditText searchEditText;
	
	ListView searchDatalist;
	
	List<ZiLiao> zList;
	
	ZiLiaoAdapter ziLiaoAdapter;
	
	//ImageButton backIb;
	LinearLayout backIl;
	
	Spinner calssesSpinner;
	
	ArrayAdapter<String> calssesArrayAdapter;
	
	String[] classes=new String[]{"����","���"};
	String str="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//����ȥ��Android�Դ�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//����������ʾ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.note_search_data);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    /**
     * ��ʼ������
     */
	private void initView() {
		// TODO Auto-generated method stub
		calssesSpinner=(Spinner)this.findViewById(R.id.search_data_classes);
		backIl=(LinearLayout)this.findViewById(R.id.search_zi_liao_back_ll);
	//	backIb=(ImageButton)this.findViewById(R.id.search_zi_liao_back_iv);
		searchEditText=(EditText)this.findViewById(R.id.search_zi_liao_edit);
		searchDatalist=(ListView)this.findViewById(R.id.search_zi_liao_list_view);
		zList=new ArrayList<ZiLiao>();
		ziLiaoAdapter=new ZiLiaoAdapter(NoteSearchDataActivity.this, zList);
		//Ϊ���������б����������
		searchDatalist.setAdapter(ziLiaoAdapter);
		
		calssesArrayAdapter=new ArrayAdapter<>(NoteSearchDataActivity.this, R.layout.note_search_calsses_spinner_item, R.id.search_classes_spinner_item_tv, classes);
		//Ϊ�����������������
		calssesSpinner.setAdapter(calssesArrayAdapter);
	}

	/**
	 * �����¼�
	 */
	private void initListener() {
		
		searchDatalist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NoteSearchDataActivity.this,
						NoteCotentActivity.class);
				intent.putExtra("ziLiaoId", ziLiaoAdapter.getList().get(position).getUuid());
				NoteSearchDataActivity.this.startActivity(intent);
			}
		});
		
		// �������͵�������¼�
		calssesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// �õ������ļ�������
				str=classes[position];
				switch (position) {
				case 0:
					//����������Ǳ��⣬���������������Ϊ����
					searchEditText.setHint("��������Ҫ��ѯ�����µı���");
					break;
				case 1:
					//���������������������������������Ϊ�������
					searchEditText.setHint("��������Ҫ��ѯ�����µ����");					
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		//���������仯�����¼�
		searchEditText.addTextChangedListener(new MyTextWatcher());
		//���ذ�ť�¼�
		backIl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// �ر�activity
				NoteSearchDataActivity.this.finish();
			}
		});
	}
	//�Զ��������仯�¼�
	class MyTextWatcher implements TextWatcher{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// �ж����������Ƿ�Ϊ��
			if (!("".equals(s.toString()))) {
				//new һ���µ������б�
				List<ZiLiao> newZiLiaos = new ArrayList<ZiLiao>();
				//new һ���������ݿ�
				ZiLiaoService ziLiaoService=new ZiLiaoService(NoteSearchDataActivity.this);
				try {
					//������������ݵõ�����
					List<ZiLiao> totalZiLiaos=ziLiaoService.findByName(s.toString(),str);
					//�жϲ�ѯ�������Ƿ�Ϊ��
					if (totalZiLiaos.size() > 0) {
						//�����Ϊ�գ�ѭ�������б�
						for (ZiLiao ziLiao : totalZiLiaos) {
							//��������Id��ȡ����ͼƬ�����£���������뵽�µ��ļ��б���
							newZiLiaos.add(ziLiaoService.findById(ziLiao.getUuid()));
						}
						ziLiaoAdapter.addList(newZiLiaos);
						//ˢ�½���
						ziLiaoAdapter.notifyDataSetChanged();
						
					}else{
						Toast.makeText(NoteSearchDataActivity.this, "û���ҵ���Ҫ��ѯ�����ݣ���", 500).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				ziLiaoAdapter.addList(zList);
				//ˢ�½���
				ziLiaoAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
