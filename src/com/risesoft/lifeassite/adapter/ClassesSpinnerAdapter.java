package com.risesoft.lifeassite.adapter;

import java.util.List;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.note.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassesSpinnerAdapter extends BaseAdapter {

	private Context context;
	public List<Classes> classess;

	LayoutInflater inflater;

	public ClassesSpinnerAdapter(Context context, List<Classes> classess) {

		this.context = context;
		this.classess = classess;
		inflater = LayoutInflater.from(this.context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return classess.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return classess.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return classess.get(position).hashCode();
	}

	public void addList(List<Classes> classess) {
		// TODO Auto-generated method stub
		this.classess = classess;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.note_add_calsses_spinner_item, null);
			holder.classesTv=(TextView)convertView.findViewById(R.id.add_classes_spinner_item_tv);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.classesTv.setText(classess.get(position).getName());
		return convertView;
	}


	class ViewHolder{
		TextView classesTv;
	}

}
