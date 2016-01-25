package com.risesoft.lifeassite.view.morefragment;

import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDBannerAd.BannerAdListener;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.view.SlideMenuActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MoreFragment extends Fragment implements OnClickListener{

	View moreView;

	RelativeLayout historyTodayRl;
	RelativeLayout appVersionRl;
	//RelativeLayout newsRl;
	ImageButton moreMenuLeft;
	private RelativeLayout appxBannerContainer;
	private static BDBannerAd bannerAdView;
	private static String TAG = "AppX_BannerAd";
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		moreView=LayoutInflater.from(getActivity()).inflate(R.layout.more_layout, null);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return moreView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		// 创建广告容器
		appxBannerContainer = (RelativeLayout) moreView.findViewById(R.id.appx_banner_container);
		historyTodayRl=(RelativeLayout)moreView.findViewById(R.id.more_history_today_rl);
		appVersionRl=(RelativeLayout)moreView.findViewById(R.id.more_current_version_rl);
		moreMenuLeft=(ImageButton)moreView.findViewById(R.id.more_menu_left);
		//newsRl=(RelativeLayout)moreView.findViewById(R.id.more_news_rl);
		bannerAdView = new BDBannerAd(getActivity(), "yaxdmPKNcFSbsufEnSeCE97FvxL8CMaF",
				"LcFOCaNGPoTmpH0a4iDDoPNa");

		// 设置横幅广告展示尺寸，如不设置，默认为SIZE_FLEXIBLE;
		bannerAdView.setAdSize(BDBannerAd.SIZE_FLEXIBLE);

		// 显示广告视图
		appxBannerContainer.addView(bannerAdView);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		historyTodayRl.setOnClickListener(this);
		appVersionRl.setOnClickListener(this);
		moreMenuLeft.setOnClickListener(this);
		//newsRl.setOnClickListener(this);
		// 设置横幅广告行为监听器
		bannerAdView.setAdListener(new BannerAdListener() {

			@Override
			public void onAdvertisementDataDidLoadFailure() {
				Log.e(TAG, "load failure");
			}

			@Override
			public void onAdvertisementDataDidLoadSuccess() {
				Log.e(TAG, "load success");
			}

			@Override
			public void onAdvertisementViewDidClick() {
				Log.e(TAG, "on click");
			}

			@Override
			public void onAdvertisementViewDidShow() {
				Log.e(TAG, "on show");
			}

			@Override
			public void onAdvertisementViewWillStartNewIntent() {
				Log.e(TAG, "leave app");
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.more_history_today_rl:
			Intent historyIntent = new Intent(getActivity(),
					HistoryTodayActivity.class);
			getActivity().startActivity(historyIntent);
			break;
		case R.id.more_menu_left:
			SlideMenuActivity.menu.toggle();
			break;
		case R.id.more_current_version_rl:
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			final AlertDialog alertDialog=builder.create();
			builder.setTitle("版本").setMessage("应用当前版本：2.1.3").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					alertDialog.cancel();
				}
			}).show();


			break;
		default:
			break;
		}
	}
	
}
