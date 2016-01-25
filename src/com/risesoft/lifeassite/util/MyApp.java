package com.risesoft.lifeassite.util;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EncodingUtils;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.risesoft.lifeassite.entity.city.City;
import com.risesoft.lifeassite.entity.city.CityResultData;
import com.risesoft.lifeassite.entity.movie.MovieInfoData;
import com.thinkland.sdk.android.JuheSDKInitializer;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

public class MyApp extends Application {
	
	public static String jsonFilePath = Environment
			.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	
	public static List<MovieInfoData> movieInfoDatas=new ArrayList<MovieInfoData>();
	public static List<String> movieStatusList=new ArrayList<String>();
	public static Map<String, String> currentPlaceMap;
	public static boolean isAutoRefresh = true;
	SharedPreferences cityPreferences;
	Editor cityEditor;
	
	SharedPreferences cityIdPreferences;
	Editor cityIdEditor;

	public static MyApp myApp;
	
	public static String starSignName;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myApp=this;
		cityPreferences=getSharedPreferences("city", 0);
		cityIdPreferences=getSharedPreferences("cityid", 1);
		
		cityEditor=cityPreferences.edit();
		cityIdEditor=cityIdPreferences.edit();

		JuheSDKInitializer.initialize(getApplicationContext());
		SDKInitializer.initialize(getApplicationContext()); 
	    File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache");  
	    
	    ImageLoaderConfiguration config = new ImageLoaderConfiguration  
	    	    .Builder(getApplicationContext())  
	    	    .threadPoolSize(2)//线程池内加载的数量  
	    	    .threadPriority(Thread.NORM_PRIORITY - 2)  
	    	    .denyCacheImageMultipleSizesInMemory()  
	    	    .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现    
	    	    .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密   
	    	    .diskCacheFileCount(50)
	    	    .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径    
	    	    .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间  
	    	    .writeDebugLogs() // Remove for release app  
	    	    .build();//开始构建 
	    ImageLoader.getInstance().init(config);//全局初始化此配置 
	    getCity();
	}

	private void getCity() {
		
		String bj=cityPreferences.getString("city_0", null);
		int i=0;
		String result="";		
		if(bj==null){			
			try { 
//               InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("city/city.json"),"utf-8"); 
//               BufferedReader bufReader = new BufferedReader(inputReader);
//               while((line = bufReader.readLine()) != null){
//            	   result += line;           	   
//               }
//               bufReader.close();
//               inputReader.close();
				InputStream is = getResources().getAssets().open("city/city.json");
				int len = is.available();
				byte []buffer = new byte[len];
				is.read(buffer);
				result = EncodingUtils.getString(buffer, "utf-8");
				is.close();
           } catch (Exception e) { 
               e.printStackTrace(); 
           }		
		   City city=JSON.parseObject(result, City.class);		   
		   List<CityResultData> cityResultDatas=city.getResult();		   
		   cityEditor.putInt("city_size",cityResultDatas.size());
		   for(CityResultData cityResultData:cityResultDatas){
			   cityEditor.putString("city_" + (i++), cityResultData.getCity()+"-"+cityResultData.getDistrict());			   
		       cityIdEditor.putString(cityResultData.getCity()+"-"+cityResultData.getDistrict(), cityResultData.getId());
		   }		   
		   cityEditor.commit();
		   cityIdEditor.commit();
		}		
	}	
	
}
