package com.risesoft.lifeassite.adapter;

import java.util.List;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.note.Photo;
import com.risesoft.lifeassite.util.ImageCache;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyRecycyViewAdapter extends RecyclerView.Adapter<MyRecycyViewAdapter.ViewHolder> {

	List<Photo> ziLiaoPhotos;
	ImageCache imageCache;
	Context context;
	public MyRecycyViewAdapter(	Context context,List<Photo> ziLiaoPhotos) {
		imageCache=new ImageCache();
		this.context=context;
		this.ziLiaoPhotos=ziLiaoPhotos;
		
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		ImageView img;
		public ViewHolder(View arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}
	}

	public void addViews(List<Photo> ziLiaoPhotos) {
		// TODO Auto-generated method stub
		this.ziLiaoPhotos=ziLiaoPhotos;
	}
	
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return ziLiaoPhotos.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int arg1) {
        //ΪͼƬ���ñ�ʶ
		holder.img.setTag(ziLiaoPhotos.get(arg1).getUrl());
		// Ԥ��һ��ͼƬ
		holder.img.setImageResource(R.drawable.ic_launcher);
		//�õ�����ͼƬ
		Bitmap bitmap = imageCache.loadBitmap(holder.img, ziLiaoPhotos.get(arg1).getUrl());
		if (bitmap != null) {
			//ΪͼƬ����bitmap
			holder.img.setImageBitmap(bitmap);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(context).inflate(
				R.layout.image_recyc_view_item, null);
		ViewHolder holder=new ViewHolder(view);
		holder.img=(ImageView)view.findViewById(R.id.recyc_view_iv_item);
		return holder;
	}
	
}
