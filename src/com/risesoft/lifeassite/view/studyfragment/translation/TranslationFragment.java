package com.risesoft.lifeassite.view.studyfragment.translation;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.translation.TransLation;
import com.risesoft.lifeassite.entity.translation.TransResultDataWeb;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TranslationFragment extends Fragment implements OnClickListener {

	View transView;

	EditText transContentEt;

	Button transBtn;
	ImageButton transLeftMenuBtn;

	TextView transResultTv;
	ProgressDialog progressDialog;
	Handler handler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			try {
				switch (msg.what) {
				case 0:
					String transResult = msg.obj.toString();
					if(transResult!=null){
						TransLation transLation = JSON.parseObject(transResult,
								TransLation.class);
						if (transLation.getResult() != null) {
							StringBuffer transBuffer = new StringBuffer();
							StringBuffer explainBuffer = new StringBuffer();
							StringBuffer webBuffer = new StringBuffer();

							List<String> explains = transLation.getResult()
									.getData().getBasic().getExplains();
							if (explains.size() > 0) {
								for (String str : explains) {
									explainBuffer.append("        " + str + "\n");
								}
							}

							List<TransResultDataWeb> webs = transLation.getResult()
									.getData().getWeb();
							if (webs.size() > 0) {

								for (TransResultDataWeb transResultDataWeb : webs) {

									webBuffer.append("        "
											+ transResultDataWeb.getKey() + "\n");
									List<String> values = transResultDataWeb
											.getValue();
									if (values.size() > 0) {
										for (int i = 0; i < values.size(); i++) {
											webBuffer.append("            "
													+ values.get(i) + "\n");
										}
									}

								}

							}

							List<String> transLations = transLation.getResult()
									.getData().getTranslation();
							if (transLations.size() > 0) {
								for (String str : transLations) {
									transBuffer.append("        " + str + "\n");
								}
							}

							transResultTv.setText("读音:"
									+ "\n"
									+ "        "
									+ transLation.getResult().getData().getBasic()
											.getPhonetic() + "\n" + "翻译：" + "\n"
									+ transBuffer.toString() + "解释:" + "\n"
									+ explainBuffer.toString() + "详解:" + "\n"
									+ webBuffer.toString());
						} else {
							transResultTv.setText("查询不到数据，请重新输入！！！");
						}
					}else{						
						Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 500).show();
					}					
					break;
				default:
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			progressDialog.dismiss();
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		transView = LayoutInflater.from(getActivity()).inflate(
				R.layout.translation_layout, null);
		initView();
		initListener();
		return transView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		transContentEt = (EditText) transView
				.findViewById(R.id.traslation_content_et);

		transBtn = (Button) transView.findViewById(R.id.traslation_btn);

		transLeftMenuBtn = (ImageButton) transView
				.findViewById(R.id.trans_menu_left);

		transResultTv = (TextView) transView
				.findViewById(R.id.traslation_result_tv);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		transBtn.setOnClickListener(this);
		transLeftMenuBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			switch (v.getId()) {
			case R.id.trans_menu_left:
				SlideMenuActivity.menu.toggle();
				break;
			case R.id.traslation_btn:
				try {
					if (OpenNetWork.getConn()) {
						progressDialog = ProgressDialog.show(getActivity(),
								"Loading...", "Please wait...");
						Parameters params = new Parameters();
						params.add("word", transContentEt.getText().toString());
						params.add("dtype", "json");
						JuHeRequest.getJuHeData(params, getActivity(), 111,
								"http://japi.juhe.cn/youdao/dictionary.from",
								JuheData.GET, handler, 0);

					} else {
						OpenNetWork.showDialog(getActivity());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
