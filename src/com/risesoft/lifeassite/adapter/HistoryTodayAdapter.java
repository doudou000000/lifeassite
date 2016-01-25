package com.risesoft.lifeassite.adapter;

import java.util.List;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.history.HistoryTodayResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryTodayAdapter extends BaseAdapter {

	Context context;
	List<HistoryTodayResult> historyTodays;
	LayoutInflater inflater;
	
	public HistoryTodayAdapter(Context context,
			List<HistoryTodayResult> historyTodays) {
		this.context=context;
		this.historyTodays=historyTodays;
		inflater=LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return historyTodays.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return historyTodays.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.history_today_list_item, null);
			holder.historyTitle=(TextView)convertView.findViewById(R.id.history_today_list_item_title);
			holder.historyContent=(TextView)convertView.findViewById(R.id.history_today_list_item_content);
			holder.historyDate=(TextView)convertView.findViewById(R.id.history_today_list_item_date);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		holder.historyTitle.setText(historyTodays.get(position).getTitle());
		holder.historyContent.setText("        "+historyTodays.get(position).getDes());
		holder.historyDate.setText(historyTodays.get(position).getYear()+"Äê"+historyTodays.get(position).getMonth()+"ÔÂ"+historyTodays.get(position).getDay()+"ÈÕ");
		return convertView;
	}

	public void getHistoryData(List<HistoryTodayResult> historyTodays) {
		// TODO Auto-generated method stub
		this.historyTodays=historyTodays;
	}

	class ViewHolder{
		
		TextView historyTitle,historyContent,historyDate;
		
	}
	
}
