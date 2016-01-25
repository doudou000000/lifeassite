package com.risesoft.lifeassite.view.studyfragment.note;

import java.util.ArrayList;


import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.ShowPhotosAdapter;
import com.risesoft.lifeassite.db.service.ZiLiaoService;
import com.risesoft.lifeassite.entity.note.Photo;
import com.risesoft.lifeassite.entity.note.ZiLiao;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
/**
 * 文章详情页
 * @author Administrator
 *
 */
public class NoteCotentActivity extends Activity {

	String ziLiaoId;//文章Id

	ZiLiao ziLiao;//声明一篇文章

	TextView ziLiaoTitle, ziLiaoCotent;//声明文章的标题和内容

	LinearLayout ziLiaoPhotos;//文章图片显示的布局，默认为不显示

//	RelativeLayout ziLiaoAttach;//文章附件显示布局，默认不显示

	public GridView ziLiaoShowPhotosGv;//图片显示空件

	ShowPhotosAdapter showPhotosAdapter;//图片显示适配器

	//ImageButton backIb;//声明返回按钮
	LinearLayout backLl;//声明返回按钮

	ZiLiaoService ziLiaoService;//声明一个文章数据库
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置去除Android自带标题
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏显示
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_xiang_qing);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		// bigIv=(ImageView)this.findViewById(R.id.big_xing_qing_iv);
//		backIb = (ImageButton) this
//				.findViewById(R.id.zi_liao_xiang_qing_back_iv);
		backLl = (LinearLayout) this
				.findViewById(R.id.zi_liao_xiang_qing_back_ll);
        //得到传过来的文章Id
		ziLiaoId = this.getIntent().getStringExtra("ziLiaoId");

		ziLiaoTitle = (TextView) this.findViewById(R.id.show_title_tv);

		ziLiaoCotent = (TextView) this.findViewById(R.id.show_content_tv);

		ziLiaoPhotos = (LinearLayout) this.findViewById(R.id.show_photos_ll);

//		ziLiaoAttach = (RelativeLayout) this.findViewById(R.id.show_attach_rl);

		ziLiaoShowPhotosGv = (GridView) this.findViewById(R.id.show_photos_gv);

		ziLiaoService = new ZiLiaoService(NoteCotentActivity.this);
		
		try {
			//通过id得到资料
			ziLiao = ziLiaoService.findById(ziLiaoId);			
		} catch (Exception e) {
			// TODO: handle exception
		}
        //设置文章标题
		ziLiaoTitle.setText(ziLiao.getTitle().toString());
		//设置文章内容
		ziLiaoCotent.setText("      " + ziLiao.getDesc().toString());
        //判断是否有图片
		if (ziLiao.getPhotos().size() > 0) {
			ziLiaoPhotos.setVisibility(View.VISIBLE);//显示图片布局
			//创建一个图片显示适配器
			showPhotosAdapter = new ShowPhotosAdapter(
					getPhotos(ziLiao.getPhotos()), NoteCotentActivity.this);
			//显示图片
			ziLiaoShowPhotosGv.setAdapter(showPhotosAdapter);
//			//为图片添加点击事件
//			ziLiaoShowPhotosGv
//					.setOnItemClickListener(new OnItemClickListener() {
//
//						@Override
//						public void onItemClick(AdapterView<?> parent,
//								View view, int position, long id) {
//							try {
//								// 弹出图片对话框
//								showPhotoDialog(position);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//
//					});
		}
		// if(ziLiao.getModes().length()>0){
		// Button button=new Button(ZiLiaoXiangQing.this);
		// button.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT));
		// button.setPadding(0, 5, 0, 5);
		// button.setText("点击查看附件");
		// button.setTextSize(18);
		// ziLiaoAttach.addView(button);
		// }
	}

//	/**
//	 * 弹出大图片对话框
//	 * 
//	 * @param position点击gridview中的某一图片
//	 */
//	@SuppressLint("NewApi")
//	private void showPhotoDialog(int position) {
//		// TODO Auto-generated method stub
//		AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.Transparent);
//		View view=LayoutInflater.from(this).inflate(R.layout.xq_view_pager, null);
//		ViewPager viewPage=(ViewPager)view.findViewById(R.id.qx_view_pager_iv);
//		final TextView textView=(TextView)view.findViewById(R.id.qx_imge_ye_shu);
//		final List<Photo> photos=getPhotos(ziLiao.getPhotos());
//		textView.setText((position+1)+"/"+photos.size());
//		XqViewPagerAdapter adapter=new XqViewPagerAdapter(this,photos,getViews(photos));
//		viewPage.setAdapter(adapter);
//		viewPage.setCurrentItem((position));
//		builder.setView(view);
//		builder.create().show();
//		viewPage.setOnPageChangeListener(new OnPageChangeListener() {
//			
//			@Override
//			public void onPageSelected(int arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//				textView.setText((arg0+1)+"/"+photos.size());
//			}
//			
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	}

//	private List<View> getViews(List<Photo> photos) {
//		// TODO Auto-generated method stub
//		List<View> views = new ArrayList<View>();
//
//		for (int i = 0; i < photos.size(); i++) {
//			View view=LayoutInflater.from(ZiLiaoXiangQing.this).inflate(R.layout.xq_view_pager_item, null);
//			views.add(view);
//		}
//		return views;
//	}

	private List<Photo> getPhotos(ForeignCollection<Photo> foreignPhotos) {
		// TODO Auto-generated method stub
		List<Photo> photos = new ArrayList<Photo>();
		try {
			CloseableIterator<Photo> iterator = foreignPhotos
					.closeableIterator();
			while (iterator.hasNext()) {
				photos.add(iterator.next());
			}
			return photos;
		} catch (Exception e) {
			return null;
		}

	}

	private void initListener() {
		// 返回事件

		backLl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NoteCotentActivity.this.finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.finish();
	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// isBig=false;
	// }

	/**
	 * 异步加载图片
	 * @author Administrator
	 *
	 */

	
	
}
