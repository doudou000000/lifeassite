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
		  .showImageOnLoading(R.drawable.wutu) //����ͼƬ�������ڼ���ʾ��ͼƬ  
		  .showImageForEmptyUri(R.drawable.wutu)//����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
		 .showImageOnFail(R.drawable.wutu)  //����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
		 .cacheInMemory(true)//�������ص�ͼƬ�Ƿ񻺴����ڴ���  
		 .cacheOnDisk(true)//�������ص�ͼƬ�Ƿ񻺴���SD����  
		 .considerExifParams(true)  //�Ƿ���JPEGͼ��EXIF��������ת����ת��
		 .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//����ͼƬ����εı��뷽ʽ��ʾ   
		 //.delayBeforeLoading(int delayInMillis)//int delayInMillisΪ�����õ�����ǰ���ӳ�ʱ��
		 //����ͼƬ���뻺��ǰ����bitmap��������  
		 //.preProcessor(BitmapProcessor preProcessor)  
		 .resetViewBeforeLoading(true)//����ͼƬ������ǰ�Ƿ����ã���λ   
		 .build();//�������
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
