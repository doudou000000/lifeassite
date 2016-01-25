package com.risesoft.lifeassite.adapter;

import java.util.List;

import com.risesoft.lifeassite.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class StarSignAdapter extends BaseAdapter {

	Context context;
	List<Integer> starSigns;
	LayoutInflater inflater;
	
	public StarSignAdapter(Context context,
			List<Integer> starSigns) {
		this.context=context;
		this.starSigns=starSigns;
		inflater=LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return starSigns.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return starSigns.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		
		if(convertView==null){
			holder=new ViewHolder();
			
			convertView=inflater.inflate(R.layout.star_sign_gv_item, null);
			holder.starSignTB=(ImageView)convertView.findViewById(R.id.star_sign_gv_item_ib);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		holder.starSignTB.setImageResource(starSigns.get(position));
		return convertView;
	}

	
	class ViewHolder{
		
		private ImageView starSignTB;
		
	}
	
}
