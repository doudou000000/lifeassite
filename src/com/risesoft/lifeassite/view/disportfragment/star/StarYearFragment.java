package com.risesoft.lifeassite.view.disportfragment.star;

import com.alibaba.fastjson.JSON;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.starsign.YearSatrSign;
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

public class StarYearFragment extends Fragment {

	View starYearView;

	TextView yearfortuneResultTv;
	TextView yearfortuneDateTv;
	ProgressDialog dialog;
	Handler handler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);

			try {
				switch (msg.what) {
				case 0:
					String yearfortuneResult = msg.obj.toString();
					if (yearfortuneResult != null) {
						YearSatrSign yearSatrSign = JSON.parseObject(
								yearfortuneResult, YearSatrSign.class);
						if (yearSatrSign.getResultcode().equals("200")) {
							yearfortuneDateTv.setText(yearSatrSign.getDate());
							yearfortuneResultTv.setText("幸运石:  "
									+ yearSatrSign.getLuckyStone() + "\n\n"
									+ "健康提醒" + "\n" + "        "
									+ yearSatrSign.getHealth().get(0) + "\n\n"
									+ "整体运" + "\n" + "        "
									+ yearSatrSign.getMima().getText().get(0)
									+ "\n\n" + "爱情运" + "\n" + "        "
									+ yearSatrSign.getLove().get(0) + "\n\n"
									+ "财运" + "\n" + "        "
									+ yearSatrSign.getFinance().get(0) + "\n\n"
									+ "事业运" + "\n" + "        "
									+ yearSatrSign.getCareer().get(0));
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
				// TODO: handle exception
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
		starYearView = LayoutInflater.from(getActivity()).inflate(
				R.layout.star_sign_fortune_layout, null);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return starYearView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		yearfortuneResultTv = (TextView) starYearView
				.findViewById(R.id.star_sign_fortune_tv);
		yearfortuneDateTv = (TextView) starYearView
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

	public void getStarYearData() {
		try {
			dialog = ProgressDialogUtil.getProgress(getActivity(),"加载中...");
			dialog.show();
			Parameters params = new Parameters();
			params.add("consName", MyApp.starSignName);
			params.add("type", "year");
			JuHeRequest.getJuHeData(params, getActivity(), 58,
					"http://web.juhe.cn:8080/constellation/getAll",
					JuheData.GET, handler, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
