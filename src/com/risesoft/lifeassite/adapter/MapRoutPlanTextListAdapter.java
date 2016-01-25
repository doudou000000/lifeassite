package com.risesoft.lifeassite.adapter;

import java.util.List;
import com.risesoft.lifeassite.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapRoutPlanTextListAdapter extends BaseAdapter {


	private Context context;	
	private LayoutInflater inflater;
	List<String> mapRoutPlanTextLists;

	public MapRoutPlanTextListAdapter(Context context, List<String> mapRoutPlanTextLists) {
		super();
		this.mapRoutPlanTextLists = mapRoutPlanTextLists;
		this.context = context;
		inflater=LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mapRoutPlanTextLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mapRoutPlanTextLists.get(position).hashCode();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.map_route_plan_text_list_item, null);
		    holder.mapRoutePlanTextContentTv=(TextView)convertView.findViewById(R.id.map_route_plan_text_list_item_content);
		    convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
        
		holder.mapRoutePlanTextContentTv.setText(mapRoutPlanTextLists.get(position));
		return convertView;
	}

	class ViewHolder{
		
		private TextView mapRoutePlanTextContentTv;
		
	}
	

}
