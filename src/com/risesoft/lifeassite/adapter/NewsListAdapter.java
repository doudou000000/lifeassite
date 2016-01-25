package com.risesoft.lifeassite.adapter;

import java.util.List;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.news.NewsResult;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsListAdapter extends BaseAdapter {

	Context context;
	List<NewsResult> newsResults;
	LayoutInflater inflater;
	ImageLoader imageLoader = ImageLoader.getInstance(); 
	DisplayImageOptions options;
	public NewsListAdapter(Context context,
			List<NewsResult> newsResults) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.newsResults=newsResults;
		inflater=LayoutInflater.from(this.context);
		options = new DisplayImageOptions.Builder()  
		  .showImageOnLoading(R.drawable.wutu) //设置图片在下载期间显示的图片  
		  .showImageForEmptyUri(R.drawable.wutu)//设置图片Uri为空或是错误的时候显示的图片  
		 .showImageOnFail(R.drawable.wutu)  //设置图片加载/解码过程中错误时候显示的图片
		 .cacheInMemory(true)//设置下载的图片是否缓存在内存中  
		 .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中  
		 .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
		 .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示   
		 //.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
		 //设置图片加入缓存前，对bitmap进行设置  
		 //.preProcessor(BitmapProcessor preProcessor)  
		 .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位   
		 .build();//构建完成
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsResults.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return newsResults.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.news_list_item, null);
			holder.newsTitleTv=(TextView)convertView.findViewById(R.id.news_list_title);
			holder.newsStoryTv=(TextView)convertView.findViewById(R.id.news_list_story);
			holder.newsIconIv=(ImageView)convertView.findViewById(R.id.news_list_icon);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		holder.newsTitleTv.setText(newsResults.get(position).getTitle());
		holder.newsStoryTv.setText(newsResults.get(position).getContent());

//		imageLoader.displayImage(movieInfoDatas.get(position).getIconlinkUrl(), holder.movieIconIv,options);
		 imageLoader.displayImage(newsResults.get(position).getImg(), holder.newsIconIv,options, new ImageLoadingListener() {

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}  

		}); 
		return convertView;
	}

	public void setNewsResults(List<NewsResult> newsResults) {
		// TODO Auto-generated method stub
		this.newsResults=newsResults;
	}

	class ViewHolder{
		TextView newsTitleTv,newsStoryTv;
		
		ImageView newsIconIv;
	}
	
}
