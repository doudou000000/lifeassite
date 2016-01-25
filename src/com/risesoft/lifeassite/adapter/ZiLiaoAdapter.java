package com.risesoft.lifeassite.adapter;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.note.Photo;
import com.risesoft.lifeassite.entity.note.ZiLiao;
import com.risesoft.lifeassite.util.ImageCache;
import com.risesoft.lifeassite.util.ViewHolder;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;

public class ZiLiaoAdapter extends BaseAdapter {

	private Context context;
	List<ZiLiao> ziLiaos;
	public Map<Integer, Integer> visiblecheck;// 记录是否显示checkbox
	public Map<Integer, Integer> visibleJianTou;// 记录是否显示checkbox

	LayoutInflater inflater;

	int degree;

	public Map<Integer, Boolean> isCheck;

	ImageCache imageCache;

	public ZiLiaoAdapter(Context context, List<ZiLiao> ziLiaos) {

		imageCache = new ImageCache();

		this.context = context;
		this.ziLiaos = ziLiaos;
		visiblecheck = new HashMap<Integer, Integer>();
		visibleJianTou = new HashMap<Integer, Integer>();
		isCheck = new HashMap<Integer, Boolean>();
		inflater = LayoutInflater.from(this.context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ziLiaos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ziLiaos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return ziLiaos.get(position).hashCode();
	}

	public void addList(List<ZiLiao> ziLiaos) {
		// TODO Auto-generated method stub
		this.ziLiaos = ziLiaos;
		for (int i = 0; i < ziLiaos.size(); i++) {
			isCheck.put(i, false);
			visiblecheck.put(i, CheckBox.GONE);
			visibleJianTou.put(i, View.VISIBLE);
		}
	}

	 public List<ZiLiao> getList() {
	 // TODO Auto-generated method stub
	 return ziLiaos;
	 }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.note_list_view_item, null);
			holder.title = (TextView) convertView
					.findViewById(R.id.zi_liao_title);
			holder.classes = (TextView) convertView
					.findViewById(R.id.zi_liao_classes);
			holder.time = (TextView) convertView
					.findViewById(R.id.zi_liao_create_time);
			holder.ifPhotos = (TextView) convertView
					.findViewById(R.id.if_photos_tv);
			// holder.ifAttach = (TextView) convertView
			// .findViewById(R.id.if_attach_tv);
			holder.photo = (ImageView) convertView
					.findViewById(R.id.zi_liao_photo);
			holder.jiantouIv = (ImageView) convertView
					.findViewById(R.id.list_view_item_img);
			holder.cb = (CheckBox) convertView
					.findViewById(R.id.list_view_item_check_box);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(ziLiaos.get(position).getTitle());
		holder.classes.setText(ziLiaos.get(position).getClasses().getName());
		holder.time.setText(ziLiaos.get(position).getTime().substring(0, 11));
		holder.cb.setVisibility(visiblecheck.get(position));
		holder.cb.setChecked(isCheck.get(position));
		
		holder.jiantouIv.setVisibility(visibleJianTou.get(position));

		
		holder.photo.setImageResource(R.drawable.wutu);

		if (ziLiaos.get(position).getPhotos().size() > 0) {
			holder.ifPhotos.setText("有图");

			Photo photo = getImageBitMap(ziLiaos.get(position).getPhotos());
			holder.photo.setTag(photo.getUrl());
			// 预设一个图片
			
			Bitmap bitmap = imageCache.loadBitmap(holder.photo, photo.getUrl());
			if (bitmap != null) {
				holder.photo.setImageBitmap(bitmap);
			}

		} else {
			holder.ifPhotos.setText("无图");
		}

		return convertView;
	}

	private Photo getImageBitMap(ForeignCollection<Photo> photos) {
		// TODO Auto-generated method stub
		List<Photo> photo = new ArrayList<Photo>();
		// Bitmap bitmap = null;
		try {
			CloseableIterator<Photo> iterator = photos.closeableIterator();
			while (iterator.hasNext()) {
				photo.add(iterator.next());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return photo.get(0);
	}

}