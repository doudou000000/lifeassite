package com.risesoft.lifeassite.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

public class ImageCache {

	// 鍐呭瓨缂撳瓨榛樿�?5M
	static final int MEM_CACHE_DEFAULT_SIZE = 5 * 1024 * 1024;
	
	public LruCache<String, Bitmap> lruCache;

	@SuppressLint("NewApi")
	public ImageCache() {

		lruCache = new LruCache<String, Bitmap>(MEM_CACHE_DEFAULT_SIZE) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};

	}

	public void addBitmapToMemory(String key, Bitmap bitmap) {

		if (getBitmapFromMemory(key) == null) {
			lruCache.put(key, bitmap);
		}

	}

	public Bitmap getBitmapFromMemory(String key) {

		return lruCache.get(key);

	}

	public Bitmap loadBitmap(ImageView img, String url) {
		// TODO Auto-generated method stub
		Bitmap bitmap = getBitmapFromMemory(url);

		if (bitmap != null) {
			Log.i("leslie", "image exists in memory");
			return bitmap;
		}

		// 閸愬懎鐡ㄩ崪灞炬瀮娴犳湹鑵戦柈鑺ョ梾閺堝鍟�禒搴ｇ秹缂佹粈绗呮潪锟�?		
		if (!TextUtils.isEmpty(url)) {
			MyTaskImage myTaskImage = new MyTaskImage(img);
			myTaskImage.execute(url);
		}

		return null;

	}

	class MyTaskImage extends AsyncTask<String, Integer, Bitmap> {

		// 澹版槑涓�釜imageview
		ImageView img;
		// 鍥剧墖鐨勬棆杞搴�?		
		int degree;

		String imageUrl;

		public MyTaskImage(ImageView img) {

			this.img = img;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			imageUrl = params[0];
			Bitmap bitmap = null;
			try {
				// 鑾峰彇鍥剧墖鐨勬棆杞搴�
				degree = RotainPicture.readPictureDegree(params[0]);
				// 鑾峰彇bitmap
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;
				bitmap = BitmapFactory.decodeFile(params[0], options);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				if (img.getTag() != null && img.getTag().equals(imageUrl)) {
					if (result != null) {
						// 鐏忓棗娴�?��鍥у閸忋儱鍩岄崘鍛摠缂傛挸鐡ㄦ稉锟�?						addBitmapToMemory(imageUrl, RotainPicture.rotaingImageView(degree, result));
						img.setImageBitmap(RotainPicture.rotaingImageView(degree, result));
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}
}