package com.risesoft.lifeassite.adapter;

import java.util.List;
import com.risesoft.lifeassite.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class UserCityAdapter extends BaseAdapter {

	List<String> useCity;
	Context context;
	
	public UserCityAdapter(List<String> useCity,
			Context context) {
		// TODO Auto-generated constructor stub
		this.useCity=useCity;
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return useCity.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return useCity.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			
			holder=new ViewHolder();
			convertView=LayoutInflater.from(this.context).inflate(R.layout.use_city_list_item, null);
			holder.userCityTv=(Button)convertView.findViewById(R.id.city_use_location_tv);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		holder.userCityTv.setText(useCity.get(position));
		return convertView;
	}

	class ViewHolder{
		
		Button userCityTv;
		
	}
	
}
