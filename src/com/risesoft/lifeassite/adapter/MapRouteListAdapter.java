package com.risesoft.lifeassite.adapter;

import java.util.List;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.map.MyMapRouteLine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapRouteListAdapter extends BaseAdapter {

	Context context;
	List<MyMapRouteLine> myMapRouteLines;
	LayoutInflater inflater;
	
	public MapRouteListAdapter(Context context,List<MyMapRouteLine> myMapRouteLines) {
		this.context=context;
		this.myMapRouteLines=myMapRouteLines;
		inflater=LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myMapRouteLines.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return myMapRouteLines.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder=null;
		
		if(convertView==null){
			
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.map_route_plan_list_item, null);
			holder.mapRouteTitleTv=(TextView)convertView.findViewById(R.id.map_route_plan_list_item_title_tv);
			holder.mapRouteDicTv=(TextView)convertView.findViewById(R.id.map_route_plan_list_item_dic_tv);
			holder.mapRouteDurTv=(TextView)convertView.findViewById(R.id.map_route_plan_list_item_dur_tv);
			holder.mapRouteDurCompareTv=(TextView)convertView.findViewById(R.id.map_route_plan_list_item_bijiao_tv);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		holder.mapRouteTitleTv.setText(myMapRouteLines.get(position).getTitle());
		holder.mapRouteDicTv.setText("总长约: "+myMapRouteLines.get(position).getDistance());
		holder.mapRouteDurTv.setText("用时约: "+myMapRouteLines.get(position).getDuration());
		holder.mapRouteDurCompareTv.setText(myMapRouteLines.get(position).getCompare());
		
		return convertView;
	}

	class ViewHolder{
		
		TextView mapRouteTitleTv,mapRouteDicTv,mapRouteDurTv,mapRouteDurCompareTv;
		
	}

	public void setMyMapRoutLine(List<MyMapRouteLine> myMapRouteLines) {
		// TODO Auto-generated method stub
		this.myMapRouteLines=myMapRouteLines;
	}
	
}
