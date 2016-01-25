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
 * 全文检索
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
	
	String[] classes=new String[]{"标题","类别"};
	String str="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置去除Android自带标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置竖屏显示
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
     * 初始化数据
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
		//为搜索数据列表添加适配器
		searchDatalist.setAdapter(ziLiaoAdapter);
		
		calssesArrayAdapter=new ArrayAdapter<>(NoteSearchDataActivity.this, R.layout.note_search_calsses_spinner_item, R.id.search_classes_spinner_item_tv, classes);
		//为搜索类型添加适配器
		calssesSpinner.setAdapter(calssesArrayAdapter);
	}

	/**
	 * 监听事件
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
		
		// 搜索类型点击监听事件
		calssesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 得到搜索文件的类型
				str=classes[position];
				switch (position) {
				case 0:
					//如果搜索的是标题，则设置搜索输入框为标题
					searchEditText.setHint("请输入您要查询的文章的标题");
					break;
				case 1:
					//如果搜索的是文章类别，则设置搜索输入框为文章类别
					searchEditText.setHint("请输入您要查询的文章的类别");					
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
		//搜索输入框变化监听事件
		searchEditText.addTextChangedListener(new MyTextWatcher());
		//返回按钮事件
		backIl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 关闭activity
				NoteSearchDataActivity.this.finish();
			}
		});
	}
	//自定义输入框变化事件
	class MyTextWatcher implements TextWatcher{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// 判断输入内容是否为空
			if (!("".equals(s.toString()))) {
				//new 一个新的文章列表
				List<ZiLiao> newZiLiaos = new ArrayList<ZiLiao>();
				//new 一个文章数据库
				ZiLiaoService ziLiaoService=new ZiLiaoService(NoteSearchDataActivity.this);
				try {
					//根据输入框内容得到文章
					List<ZiLiao> totalZiLiaos=ziLiaoService.findByName(s.toString(),str);
					//判断查询的文章是否为空
					if (totalZiLiaos.size() > 0) {
						//如果不为空，循环文章列表
						for (ZiLiao ziLiao : totalZiLiaos) {
							//根据文章Id获取到带图片的文章，并将其放入到新的文件列表中
							newZiLiaos.add(ziLiaoService.findById(ziLiao.getUuid()));
						}
						ziLiaoAdapter.addList(newZiLiaos);
						//刷新界面
						ziLiaoAdapter.notifyDataSetChanged();
						
					}else{
						Toast.makeText(NoteSearchDataActivity.this, "没有找到您要查询的数据！！", 500).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				ziLiaoAdapter.addList(zList);
				//刷新界面
				ziLiaoAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
