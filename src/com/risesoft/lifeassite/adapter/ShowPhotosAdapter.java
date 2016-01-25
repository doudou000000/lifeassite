package com.risesoft.lifeassite.adapter;

import java.util.List;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.entity.note.Photo;
import com.risesoft.lifeassite.util.HackyViewPager;
import com.risesoft.lifeassite.util.ImageCache;
import com.risesoft.lifeassite.util.RotainPicture;
import com.risesoft.lifeassite.view.studyfragment.note.NoteCotentActivity;


import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPhotosAdapter extends BaseAdapter {

	private Animator mCurrentAnimator;

	private int mShortAnimationDuration = 300;

	List<Photo> photos;
	Context context;
	ImageCache imageCache;

	public ShowPhotosAdapter(List<Photo> photos, Context context) {
		// TODO Auto-generated constructor stub
		this.photos = photos;
		this.context = context;
		imageCache = new ImageCache();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return photos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return photos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return photos.get(position).hashCode();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//
		ViewHolder holder = null;

		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(
					R.layout.image_gride_view_item, null);

			holder = new ViewHolder();

			holder.showPhotoIv = (ImageView) convertView
					.findViewById(R.id.view_pager_iv);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.showPhotoIv.setTag(photos.get(position).getUrl());
		// 预设一个图片
		holder.showPhotoIv.setImageResource(R.drawable.ic_launcher);

		Bitmap bitmap = imageCache.loadBitmap(holder.showPhotoIv,
				photos.get(position).getUrl());
		if (bitmap != null) {
			holder.showPhotoIv.setImageBitmap(bitmap);
		}

		holder.showPhotoIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				zoomImageFromThumb(view, position);
			}
		});

		return convertView;
	}

	class ViewHolder {

		ImageView showPhotoIv, showPhotoBigIv;

	}

	float startScale;
	HackyViewPager viewPager;
	TextView textView;
	Rect startBounds;
	float startScaleFinal;

	@SuppressLint("NewApi")
	private void zoomImageFromThumb(View view, int position) {
		// TODO Auto-generated method stub
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}

		viewPager = (HackyViewPager) ((Activity) context)
				.findViewById(R.id.expanded_image);
		textView = (TextView) ((Activity) context)
				.findViewById(R.id.xq_ye_shu);
		textView.setText((position+1)+"/"+photos.size());
		
		viewPager.setAdapter(new SamplePagerAdapter(photos, context));
		viewPager.setCurrentItem(position);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				// TODO Auto-generated method stub
				textView.setText((position+1)+"/"+photos.size());
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();

		// The start bounds are the global visible rectangle of the thumbnail,
		// and the
		// final bounds are the global visible rectangle of the container view.
		// Also
		// set the container view's offset as the origin for the bounds, since
		// that's
		// the origin for the positioning animation properties (X, Y).
		view.getGlobalVisibleRect(startBounds);

		((Activity) context).findViewById(R.id.container).getGlobalVisibleRect(
				finalBounds, globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);

		if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
				.width() / startBounds.height()) {
			// Extend start bounds horizontally
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {
			// Extend start bounds vertically
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}

		// show the zoomed-in view. When the animation
		// begins,
		// it will position the zoomed-in view in the place of the thumbnail.
		viewPager.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);

		AnimatorSet animSet = new AnimatorSet();
		animSet.setDuration(1);
		animSet.play(ObjectAnimator.ofFloat(viewPager, "pivotX", 0f))
				.with(ObjectAnimator.ofFloat(viewPager, "pivotY", 0f))
				.with(ObjectAnimator.ofFloat(viewPager, "alpha", 1.0f));
		animSet.start();

		// Construct and run the parallel animation of the four translation and
		// scale properties
		// (X, Y, SCALE_X, and SCALE_Y).
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(
				((NoteCotentActivity) context).ziLiaoShowPhotosGv, "alpha", 1.0f,
				0.f);
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(viewPager, "x",
				startBounds.left, finalBounds.left);
		ObjectAnimator animatorY = ObjectAnimator.ofFloat(viewPager, "y",
				startBounds.top, finalBounds.top);
		ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(viewPager,
				"scaleX", startScale, 1f);
		ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(viewPager,
				"scaleY", startScale, 1f);

		set.play(alphaAnimator).with(animatorX).with(animatorY)
				.with(animatorScaleX).with(animatorScaleY);
		set.setDuration(mShortAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {

			public void onAnimationEnd(Animator animation) {
				mCurrentAnimator = null;
			}

			public void onAnimationCancel(Animator animation) {
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;

		// Upon clicking the zoomed-in image, it should zoom back down to the
		// original bounds
		// and show the thumbnail instead of the expanded image.
		startScaleFinal = startScale;

	}

	public boolean getScaleFinalBounds(int position) {
		GridView gridView = ((NoteCotentActivity) context).ziLiaoShowPhotosGv;
		View childView = gridView.getChildAt(position);
		
		startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();

		try {
			childView.getGlobalVisibleRect(startBounds);
		} catch (Exception e) {
			return false;
		}
		((Activity) context).findViewById(R.id.container).getGlobalVisibleRect(
				finalBounds, globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);

		if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
				.width() / startBounds.height()) {
			// Extend start bounds horizontally
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {
			// Extend start bounds vertically
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}
		startScaleFinal = startScale;
		return true;
	}

	class SamplePagerAdapter extends PagerAdapter {

		List<Photo> photos;
		Context context;
		int degree;
		public SamplePagerAdapter(List<Photo> photos, Context context) {
			// TODO Auto-generated constructor stub

			this.photos = photos;
			this.context = context;
			// 获取图片的旋转角度
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return this.photos.size();
		}

		@Override
		@SuppressLint("NewApi")
		public Object instantiateItem(ViewGroup container, final int position) {
			degree = RotainPicture.readPictureDegree(photos.get(position).getUrl());
			final PhotoView photoView = new PhotoView(container.getContext());
			photoView.setImageBitmap(RotainPicture.rotaingImageView(degree, getBitmap(photos.get(position).getUrl())));

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			photoView
					.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

						public void onPhotoTap(View paramAnonymousView,
								float paramAnonymousFloat1,
								float paramAnonymousFloat2) {
							if (mCurrentAnimator != null) {
								mCurrentAnimator.cancel();
							}

							photoView.clearZoom();

							boolean scaleResult = getScaleFinalBounds(position);

							AnimatorSet as = new AnimatorSet();
							ObjectAnimator containAlphaAnimator = ObjectAnimator
									.ofFloat(
											((NoteCotentActivity) context).ziLiaoShowPhotosGv,
											"alpha", 0.f, 1.0f);
							if (scaleResult) {
								ObjectAnimator animatorX = ObjectAnimator
										.ofFloat(viewPager, "x",
												startBounds.left);
								ObjectAnimator animatorY = ObjectAnimator
										.ofFloat(viewPager, "y",
												startBounds.top);
								ObjectAnimator animatorScaleX = ObjectAnimator
										.ofFloat(viewPager, "scaleX",
												startScaleFinal);
								ObjectAnimator animatorScaleY = ObjectAnimator
										.ofFloat(viewPager, "scaleY",
												startScaleFinal);

								as.play(containAlphaAnimator).with(animatorX)
										.with(animatorY).with(animatorScaleX)
										.with(animatorScaleY);
							} else {
								// the selected photoview is beyond the mobile
								// screen display
								// so it just fade out
								ObjectAnimator alphaAnimator = ObjectAnimator
										.ofFloat(viewPager, "alpha", 0.1f);
								as.play(alphaAnimator).with(
										containAlphaAnimator);
							}
							as.setDuration(mShortAnimationDuration);
							as.setInterpolator(new DecelerateInterpolator());
							as.addListener(new AnimatorListenerAdapter() {

								@Override
								public void onAnimationEnd(Animator animation) {
									textView.setVisibility(View.GONE);
									viewPager.clearAnimation();
									viewPager.setVisibility(View.GONE);
									mCurrentAnimator = null;
								}

								@Override
								public void onAnimationCancel(Animator animation) {
									textView.setVisibility(View.GONE);
									viewPager.clearAnimation();
									viewPager.setVisibility(View.GONE);
									mCurrentAnimator = null;
								}
							});
							as.start();
							mCurrentAnimator = as;

						}
					});

			return photoView;

		}

		private Bitmap getBitmap(String url) {
			// TODO Auto-generated method stub
			Bitmap bitmap = null;
			try {

				// 获取bitmap
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;
				bitmap = BitmapFactory.decodeFile(url, options);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

}
