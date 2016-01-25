package com.risesoft.lifeassite.adapter;

import java.util.List;


import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.express.ExpressResultList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpressListAdapter extends BaseAdapter {

	private List<ExpressResultList> expressResultLists;
	private List<Integer> resourceIds;
	private Context context;
	
	private LayoutInflater inflater;
	
	int resourceId;
	public ExpressListAdapter(List<ExpressResultList> expressResultLists, List<Integer> resourceIds, Context context) {
		super();
		this.expressResultLists = expressResultLists;
		this.context = context;
		inflater=LayoutInflater.from(this.context);
		this.resourceIds=resourceIds;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return expressResultLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return expressResultLists.get(position).hashCode();
	}

	public void getExpresses(List<ExpressResultList> expressResultLists,List<Integer> resourceIds){
		this.expressResultLists=expressResultLists;
		this.resourceIds=resourceIds;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.express_time_list_item, null);
		    holder.expressContentTv=(TextView)convertView.findViewById(R.id.express_show_content);
		    holder.expressContentRl=(RelativeLayout)convertView.findViewById(R.id.express_show_content_rl);
		    convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
        
        holder.expressContentRl.setBackgroundResource(resourceIds.get(position));
		holder.expressContentTv.setText(expressResultLists.get(position).getDatetime()+"\n"+expressResultLists.get(position).getRemark());
		return convertView;
	}

	class ViewHolder{
		
		private TextView expressContentTv;
		private RelativeLayout expressContentRl;
		
	}
	
}
