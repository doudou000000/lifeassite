package com.risesoft.lifeassite.view.disportfragment.star;

import com.alibaba.fastjson.JSON;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.starsign.MonthSatrSign;
import com.risesoft.lifeassite.util.JuHeRequest;
import com.risesoft.lifeassite.util.MyApp;
import com.risesoft.lifeassite.util.ProgressDialogUtil;
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
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class StarMonthFragment extends Fragment {

	View starMonthView;

	TextView monthfortuneResultTv;
	TextView monthfortuneDateTv;
	ProgressDialog dialog;
	Handler handler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);

			try {
				switch (msg.what) {
				case 0:
					String monthfortuneResult = msg.obj.toString();
					if (monthfortuneResult != null) {
						MonthSatrSign monthSatrSign = JSON.parseObject(
								monthfortuneResult, MonthSatrSign.class);
						if (monthSatrSign.getResultcode().equals("200")) {
							monthfortuneDateTv.setText(monthSatrSign.getDate());
							monthfortuneResultTv.setText("健康提醒" + "\n"
									+ "        " + monthSatrSign.getHealth()
									+ "\n\n" + "整体运" + "\n" + "        "
									+ monthSatrSign.getAll() + "\n\n" + "爱情运"
									+ "\n" + "        "
									+ monthSatrSign.getLove() + "\n\n" + "财运"
									+ "\n" + "        "
									+ monthSatrSign.getMoney() + "\n\n" + "事业运"
									+ "\n" + "        "
									+ monthSatrSign.getWork());
						} else {

							Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！",
									1000).show();

						}
					} else {
						Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 1000)
								.show();

					}

					break;
				case -1:
					Toast.makeText(getActivity(), "数据加载失败，请检查网络！！！", 1000)
							.show();
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			}, 500);
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		starMonthView = LayoutInflater.from(getActivity()).inflate(
				R.layout.star_sign_fortune_layout, null);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return starMonthView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		monthfortuneResultTv = (TextView) starMonthView
				.findViewById(R.id.star_sign_fortune_tv);
		monthfortuneDateTv = (TextView) starMonthView
				.findViewById(R.id.star_sign_fortune_date);
	}

	private void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	public void getStarMonthData() {
		try {
			dialog = ProgressDialogUtil.getProgress(getActivity(),"加载中...");
			dialog.show();
			Parameters params = new Parameters();
			params.add("consName", MyApp.starSignName);
			params.add("type", "month");
			JuHeRequest.getJuHeData(params, getActivity(), 58,
					"http://web.juhe.cn:8080/constellation/getAll",
					JuheData.GET, handler, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
