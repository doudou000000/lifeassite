package com.risesoft.lifeassite.view.disportfragment.star;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.StarSignAdapter;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;

public class StarFragment extends Fragment implements OnClickListener{

	View starView;
	
	GridView starSignGv;
	
	StarSignAdapter starSignAdapter;
	
	List<Integer> starSigns;
	
	Map<Integer, String> starSignNameMaps;
	
	ImageButton leftMenuBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		starView = LayoutInflater.from(getActivity()).inflate(
				R.layout.star_sign_layout, null);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return starView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		starSigns=new ArrayList<Integer>();
		starSignNameMaps=new HashMap<Integer, String>();
		getStarSignData();
		starSignAdapter=new StarSignAdapter(getActivity(),starSigns);
		leftMenuBtn=(ImageButton)starView.findViewById(R.id.star_sign_menu_left);
		starSignGv=(GridView)starView.findViewById(R.id.star_sign_grid_view);
		starSignGv.setAdapter(starSignAdapter);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		leftMenuBtn.setOnClickListener(this);
		starSignGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String starSignName=starSignNameMaps.get(starSigns.get(position));
				Intent intent=new Intent(getActivity(), StarSignContentActivity.class);
				intent.putExtra("starSignName", starSignName);
				getActivity().startActivity(intent);
			}
		});
	}
	private void getStarSignData() {
		// TODO Auto-generated method stub
		starSigns.add(R.drawable.star_sign_by_icon);
		starSigns.add(R.drawable.star_sign_jn_icon);
		starSigns.add(R.drawable.star_sign_shz_icon);
		starSigns.add(R.drawable.star_sign_jx_icon);
		starSigns.add(R.drawable.star_sign_sz_icon);
		starSigns.add(R.drawable.star_sign_chn_icon);
		starSigns.add(R.drawable.star_sign_tp_icon);
		starSigns.add(R.drawable.star_sign_tx_icon);
		starSigns.add(R.drawable.star_sign_shsh_icon);
		starSigns.add(R.drawable.star_sign_mx_icon);
		starSigns.add(R.drawable.star_sign_shp_icon);
		starSigns.add(R.drawable.star_sign_shy_icon);
		starSignNameMaps.put(R.drawable.star_sign_by_icon, "°×Ñò×ù");
		starSignNameMaps.put(R.drawable.star_sign_jn_icon, "½ðÅ£×ù");
		starSignNameMaps.put(R.drawable.star_sign_shz_icon, "Ë«×Ó×ù");
		starSignNameMaps.put(R.drawable.star_sign_jx_icon, "¾ÞÐ·×ù");
		starSignNameMaps.put(R.drawable.star_sign_sz_icon, "Ê¨×Ó×ù");
		starSignNameMaps.put(R.drawable.star_sign_chn_icon, "´¦Å®×ù");
		starSignNameMaps.put(R.drawable.star_sign_tp_icon, "Ìì³Ó×ù");
		starSignNameMaps.put(R.drawable.star_sign_tx_icon, "ÌìÐ«×ù");
		starSignNameMaps.put(R.drawable.star_sign_shsh_icon, "ÉäÊÖ×ù");
		starSignNameMaps.put(R.drawable.star_sign_mx_icon, "Ä¦ôÉ×ù");
		starSignNameMaps.put(R.drawable.star_sign_shp_icon, "Ë®Æ¿×ù");
		starSignNameMaps.put(R.drawable.star_sign_shy_icon, "Ë«Óã×ù");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.star_sign_menu_left:
			try {
				SlideMenuActivity.menu.toggle();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
}
