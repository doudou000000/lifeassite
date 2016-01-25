package com.risesoft.lifeassite.adapter;

import java.util.List;


import com.risesoft.lifeassite.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityAdapter extends BaseAdapter {

	Context context;
	List<String> cityList;
	LayoutInflater inflater;
	public CityAdapter(Context context,
			List<String> cityList) {
		this.context=context;
		this.cityList=cityList;
		inflater=LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cityList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return cityList.get(position).hashCode();
	}

	public void getCity(List<String> cityList) {
		// TODO Auto-generated method stub
		this.cityList=cityList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.city_search_list_item, null);
			holder.citySearchTv=(TextView)convertView.findViewById(R.id.city_search_list_item_tv);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		holder.citySearchTv.setText(cityList.get(position));
		return convertView;
	}

	class ViewHolder{
		
		TextView citySearchTv;
		
	}
	
}
