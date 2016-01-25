package com.risesoft.lifeassite.adapter;

import java.util.List;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.note.Photo;
import com.risesoft.lifeassite.util.ImageCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyViewPagerAdapter extends PagerAdapter {
	Context context;
	List<Photo> ziLiaoPhotos;
	List<View> ziLiaoPhotosView;
	ImageCache imageCache;
	public MyViewPagerAdapter(Context context,
			List<Photo> ziLiaoPhotos, List<View> ziLiaoPhotosView) {
		imageCache=new ImageCache();
		this.context=context;
		this.ziLiaoPhotos=ziLiaoPhotos;
		this.ziLiaoPhotosView=ziLiaoPhotosView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ziLiaoPhotos.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	public void addViews(List<Photo> ziLiaoPhotos,List<View> ziLiaoPhotosView) {
		// TODO Auto-generated method stub
		this.ziLiaoPhotos=ziLiaoPhotos;
		this.ziLiaoPhotosView=ziLiaoPhotosView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		( (ViewPager)container).removeView((View)object);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		// TODO Auto-generated method stub
        View view =ziLiaoPhotosView.get(position); 
        // ���������������ͼƬ���˴����Խ����첽����  
        ImageView img = (ImageView) view.findViewById(R.id.view_pager_iv);   
        //ΪͼƬ���ñ�ʶ
        img.setTag(ziLiaoPhotos.get(position).getUrl());
		// Ԥ��һ��ͼƬ
		img.setImageResource(R.drawable.ic_launcher);
		//�õ�����ͼƬ
		Bitmap bitmap = imageCache.loadBitmap(img, ziLiaoPhotos.get(position).getUrl());
		if (bitmap != null) {
			//ΪͼƬ����bitmap
			img.setImageBitmap(bitmap);
		}
        container.addView(view);  
        return view; // ���ظ�view������Ϊkey 
	}
	
}
