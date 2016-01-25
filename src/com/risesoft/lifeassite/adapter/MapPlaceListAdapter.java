package com.risesoft.lifeassite.adapter;

import java.util.List;

import com.baidu.mapapi.search.core.PoiInfo;
import com.risesoft.lifeassite.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapPlaceListAdapter extends BaseAdapter {

	Context context;
	List<PoiInfo> startPoiInfos;
	LayoutInflater inflater;
	
	public MapPlaceListAdapter(Context context,
			List<PoiInfo> startPoiInfos) {
		this.context=context;
		this.startPoiInfos=startPoiInfos;
		inflater=LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return startPoiInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return startPoiInfos.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder=null;
		
		if(convertView==null){
			
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.map_place_list_item, null);
			holder.mapPlaceNameTv=(TextView)convertView.findViewById(R.id.map_place_list_item_name);
			holder.mapPlaceAddrTv=(TextView)convertView.findViewById(R.id.map_place_list_item_addr);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		holder.mapPlaceNameTv.setText(startPoiInfos.get(position).name);
		if(startPoiInfos.get(position).address==null||"".equals(startPoiInfos.get(position).address)){
			holder.mapPlaceAddrTv.setText(startPoiInfos.get(position).name);
			
		}else{
			holder.mapPlaceAddrTv.setText(startPoiInfos.get(position).address);		
		}
		
		return convertView;
	}

	class ViewHolder{
		
		TextView mapPlaceNameTv,mapPlaceAddrTv;
		
	}
	
}
