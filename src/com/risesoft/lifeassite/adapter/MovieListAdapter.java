package com.risesoft.lifeassite.adapter;

import java.util.List;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.movie.MovieInfoData;
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
import android.widget.ListView;
import android.widget.TextView;

public class MovieListAdapter extends BaseAdapter {

	List<MovieInfoData> movieInfoDatas;
	Context context;
	LayoutInflater inflater;
	ImageLoader imageLoader = ImageLoader.getInstance(); 
	DisplayImageOptions options; 
	List<String> movieStatusList;
	
	public MovieListAdapter(List<MovieInfoData> movieInfoDatas, Context context, List<String> movieStatusList) {
		// TODO Auto-generated constructor stub
		this.movieInfoDatas=movieInfoDatas;
		this.context=context;
		this.movieStatusList=movieStatusList;
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
		return movieInfoDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return movieInfoDatas.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.movie_list_item, null);
			holder.movieTitleTv=(TextView)convertView.findViewById(R.id.movie_list_title);
			holder.movieStoryTv=(TextView)convertView.findViewById(R.id.movie_list_story);
			holder.movieGradeTv=(TextView)convertView.findViewById(R.id.movie_list_grade);
			holder.movieStatusTv=(TextView)convertView.findViewById(R.id.movie_list_status);
			holder.movieIconIv=(ImageView)convertView.findViewById(R.id.movie_list_icon);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		holder.movieTitleTv.setText(movieInfoDatas.get(position).getTvTitle());
		holder.movieStoryTv.setText(movieInfoDatas.get(position).getStory().getData().getStoryBrief());
		if(movieStatusList.get(position).equals("正在上映")){
			holder.movieGradeTv.setText("评分："+movieInfoDatas.get(position).getGrade());			
		}else{			
			holder.movieGradeTv.setText("");			
		}
		holder.movieStatusTv.setText(movieStatusList.get(position));
//		imageLoader.displayImage(movieInfoDatas.get(position).getIconlinkUrl(), holder.movieIconIv,options);
		 imageLoader.displayImage(movieInfoDatas.get(position).getIconaddress(), holder.movieIconIv,options, new ImageLoadingListener() {

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

	public void getMovieListData(List<MovieInfoData> movieInfoDatas,List<String> movieStatusList) {
		// TODO Auto-generated method stub
		this.movieInfoDatas=movieInfoDatas;
		this.movieStatusList=movieStatusList;
	}

	class ViewHolder {
		
		TextView movieTitleTv,movieStoryTv,movieGradeTv,movieStatusTv;
		
		ImageView movieIconIv;
		
	}
	
}
