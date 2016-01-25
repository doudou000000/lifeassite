package com.risesoft.lifeassite.view.lifefragment.express;

import java.io.FileInputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.EncodingUtils;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.view.SlideMenuActivity;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class ExpressFragment extends Fragment implements OnClickListener{

    View expressView;
	Spinner expressCompanySpinner;
	AutoCompleteTextView expressModelEt;
	Button expressSearchBtn;
	String companyName;
	Map<String, String> expressCompanyMap=new HashMap<String, String>();

	ImageButton expressMenuLeft;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		expressView=LayoutInflater.from(getActivity()).inflate(R.layout.express_layout, null);
		initView();
		initListener();
		return expressView;
	}
	private void initView() {
		// TODO Auto-generated method stub
		expressCompanyMap.put("˳��", "sf");
		expressCompanyMap.put("��ͨ", "sto");
		expressCompanyMap.put("Բͨ", "yt");
		expressCompanyMap.put("�ϴ�", "yd");
		expressCompanyMap.put("����", "tt");
		expressCompanyMap.put("EMS", "ems");
		expressCompanyMap.put("��ͨ", "zto");
		expressCompanyMap.put("��ͨ", "ht");
		expressCompanyMap.put("ȫ��", "qf");
		expressCompanyMap.put("�°�", "db");
		expressMenuLeft=(ImageButton)expressView.findViewById(R.id.express_menu_left);
		expressCompanySpinner=(Spinner)expressView.findViewById(R.id.express_company_spinner);
		expressModelEt=(AutoCompleteTextView)expressView.findViewById(R.id.express_model_et);
		expressSearchBtn=(Button)expressView.findViewById(R.id.express_search_btn);
//		String[] expressNos=readFileData().split(",");
//		expressModelEt.setAdapter(new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_dropdown_item_1line ,expressNos)); 
	}
	private void initListener() {
		// TODO Auto-generated method stub
		expressSearchBtn.setOnClickListener(this);
		expressMenuLeft.setOnClickListener(this);
		expressCompanySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		    
		        String[] languages = getResources().getStringArray(R.array.express_company);
		        companyName=languages[pos];
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.express_search_btn:
			if(expressModelEt.getText().toString().contains("(")){
				if(expressModelEt.getText().toString().contains(expressCompanyMap.get(companyName))){
					Intent intent=new Intent(getActivity(), ExpressContentActivity.class);
					intent.putExtra("companyName", expressCompanyMap.get(companyName));
					intent.putExtra("expressNo", expressModelEt.getText().toString());
					getActivity().startActivity(intent);
				}else{
					Toast.makeText(getActivity(), "��ݹ�˾�뵥���еĹ�˾����", 1000).show();
				}
			}else{
				Intent intent=new Intent(getActivity(), ExpressContentActivity.class);
				intent.putExtra("companyName", expressCompanyMap.get(companyName));
				intent.putExtra("expressNo", expressModelEt.getText().toString()+"("+expressCompanyMap.get(companyName)+")");
				getActivity().startActivity(intent);
			}

			break;
		case R.id.express_menu_left:
			SlideMenuActivity.menu.toggle();
			break;
		default:
			break;
		}
	}
	   public String readFileData(){ 

	        String res=""; 

	        try{ 

	         FileInputStream fin = getActivity().openFileInput("express.txt"); 

	         int length = fin.available(); 

	         byte [] buffer = new byte[length]; 

	         fin.read(buffer);     

	         res = EncodingUtils.getString(buffer, "UTF-8"); 

	         fin.close();     

	        } 

	        catch(Exception e){ 

	         e.printStackTrace(); 

	        } 

	        return res; 

	    } 
	   
//	    public static boolean isContainChinese(String str) {
//
//	        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//	        Matcher m = p.matcher(str);
//	        if (m.find()) {
//	            return true;
//	        }
//	        return false;
//	    }
	  @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String[] expressNos=readFileData().split(",");
		expressModelEt.setAdapter(new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_dropdown_item_1line ,expressNos));
	} 
}
