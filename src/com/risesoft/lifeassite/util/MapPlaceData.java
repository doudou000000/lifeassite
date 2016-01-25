package com.risesoft.lifeassite.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.MapPlaceListAdapter;
import com.risesoft.lifeassite.view.lifefragment.map.MapRoutPlanActivity;

public class MapPlaceData {
	
	public static void getStartEndData(Context context,
			SuggestAddrInfo suggestAddrInfo, EditText editSt, EditText editEn) {
		// TODO Auto-generated method stub
		List<PoiInfo> startPoiInfos = suggestAddrInfo.getSuggestStartNode();
		List<PoiInfo> endPoiInfos = suggestAddrInfo.getSuggestEndNode();
		List<PoiInfo> newStartPoiInfos = new ArrayList<PoiInfo>();
		List<PoiInfo> newEndPoiInfos = new ArrayList<PoiInfo>();
		if (startPoiInfos != null && startPoiInfos.size() > 0) {
			for(PoiInfo starPoiInfo:startPoiInfos){
				if(!(starPoiInfo.name.equals(starPoiInfo.address))){
					newStartPoiInfos.add(starPoiInfo);
				}
			}
			popDialog(context, newStartPoiInfos, editSt, "请选择起点");
		}
		if (endPoiInfos != null && endPoiInfos.size() > 0) {
			for(PoiInfo endPoiInfo:endPoiInfos){
				if(!(endPoiInfo.name.equals(endPoiInfo.address))){
					newEndPoiInfos.add(endPoiInfo);
				}
			}
			popDialog(context, newEndPoiInfos, editEn, "请选择终点");
		}
	}

	public static void popDialog(Context context,
			final List<PoiInfo> startPoiInfos, final EditText editSt, String str) {
		// TODO Auto-generated method stub
		View statView = LayoutInflater.from(context).inflate(
				R.layout.map_place_list, null);
		ListView placeListView = (ListView) statView
				.findViewById(R.id.map_place_list_view);
		MapPlaceListAdapter mapPlaceListAdapter = new MapPlaceListAdapter(
				context, startPoiInfos);
		placeListView.setAdapter(mapPlaceListAdapter);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		final AlertDialog alertDialog = builder.setTitle(str).setView(statView).show();

		placeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyApp.currentPlaceMap=new HashMap<String, String>();
				MyApp.currentPlaceMap.put(startPoiInfos.get(position).name, startPoiInfos.get(position).name+startPoiInfos.get(position).address);
				editSt.setText(startPoiInfos.get(position).name);
				// 5秒后运行run()方法。

				alertDialog.cancel();


			}

		});
	}

}
