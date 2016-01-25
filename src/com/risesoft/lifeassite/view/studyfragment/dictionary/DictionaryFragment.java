package com.risesoft.lifeassite.view.studyfragment.dictionary;

import com.alibaba.fastjson.JSON;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.dictionary.Dictionary;
import com.risesoft.lifeassite.util.JuHeRequest;
import com.risesoft.lifeassite.util.OpenNetWork;
import com.risesoft.lifeassite.view.SlideMenuActivity;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
/**
 * 字典
 * @author Administrator
 *
 */
public class DictionaryFragment extends Fragment implements OnClickListener{

	private View dicView;
	
	private ImageButton sildeMenuLeftBtn;
	
	private EditText searchEt;
	
	private TextView resultTv;
	
	private ProgressDialog progressDialog;
	
	private Handler handler=new Handler(){
		
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			try {
				switch (msg.what) {
				case 0:
					String result=msg.obj.toString();
					if(result!=null){
						try {
							Dictionary dictionary=JSON.parseObject(result, Dictionary.class); // 把JSON文本parse为JavaBean 
							if(dictionary.getResult()!=null){
								resultTv.setText("汉字："+dictionary.getResult().getZi()+"\n"
										 +"拼音："+dictionary.getResult().getPinyin()+"\n"
										 +"笔画："+dictionary.getResult().getBihua()+"\n"
										 +"五笔："+dictionary.getResult().getWubi()+"\n"
										 +"简介："+"\n"+"        "+dictionary.getResult().getJijie()+"\n"
										 +"描述："+"\n"+"        "+dictionary.getResult().getXiangjie()+"\n"		
										);
							}else{
								resultTv.setText("查询不到记录");
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						progressDialog.dismiss();
					}else{
						resultTv.setText("没有查询到数据，请核对后重新输入！！！");
					}
					break;

				default:
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dicView=LayoutInflater.from(getActivity()).inflate(R.layout.dictionary_layout, null);
		initView();
		initListener();
		return dicView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		sildeMenuLeftBtn=(ImageButton)dicView.findViewById(R.id.dic_menu_left);
		searchEt=(EditText)dicView.findViewById(R.id.dictionary_et);
		resultTv=(TextView)dicView.findViewById(R.id.dictionary_result_tv);
		searchEt.addTextChangedListener(new MyTextWatcher());
	}

	private void initListener() {
		// TODO Auto-generated method stub
		sildeMenuLeftBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dic_menu_left:
			SlideMenuActivity.menu.toggle();
			break;

		default:
			break;
		}
	}
	
	class MyTextWatcher implements TextWatcher{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if(s.toString()!=null){
				try {
					if (OpenNetWork.getConn()) {
						progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...");   
						Parameters params = new Parameters();
						params.add("word", s.toString());
						params.add("dtype", "json");
						JuHeRequest.getJuHeData(params, getActivity(), 156, "http://v.juhe.cn/xhzd/query", JuheData.GET,handler,0);
					}else{
						OpenNetWork.showDialog(getActivity());
					}	
				} catch (Exception e) {
					// TODO: handle exception
				}
		
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
				
	}
	
}
