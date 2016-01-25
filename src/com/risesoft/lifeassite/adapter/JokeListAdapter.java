package com.risesoft.lifeassite.adapter;

import java.util.List;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.joke.JokeResultData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JokeListAdapter extends BaseAdapter {

	List<JokeResultData> jokeResultDatas;
	Context context;
	LayoutInflater inflater;
	public JokeListAdapter(List<JokeResultData> jokeResultDatas,
			Context context) {
		this.jokeResultDatas=jokeResultDatas;
		this.context=context;
		inflater=LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jokeResultDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return jokeResultDatas.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.joke_list_item, null);
			holder.titleTv=(TextView)convertView.findViewById(R.id.joke_item_title);
			holder.timeTv=(TextView)convertView.findViewById(R.id.joke_item_time);
			holder.contentTv=(TextView)convertView.findViewById(R.id.joke_item_content);
		    convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		holder.titleTv.setText("ÓÄÄ¬Ð¦»°");
		holder.timeTv.setText(jokeResultDatas.get(position).getUpdatetime());
		holder.contentTv.setText(jokeResultDatas.get(position).getContent());
		return convertView;
	}

	public void getJokeResultDatas(List<JokeResultData> jokeResultDatas) {
		// TODO Auto-generated method stub
		this.jokeResultDatas=jokeResultDatas;
	}

	class ViewHolder {
		
		private TextView titleTv,timeTv,contentTv;
		
	}
	
}
