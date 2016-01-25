package com.risesoft.lifeassite.view.studyfragment.note;

import java.io.File;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.ClassesSpinnerAdapter;
import com.risesoft.lifeassite.adapter.MyRecycyViewAdapter;
import com.risesoft.lifeassite.db.service.ClassesService;
import com.risesoft.lifeassite.db.service.PhotoService;
import com.risesoft.lifeassite.db.service.ZiLiaoService;
import com.risesoft.lifeassite.entity.note.Classes;
import com.risesoft.lifeassite.entity.note.Photo;
import com.risesoft.lifeassite.entity.note.ZiLiao;
import com.risesoft.lifeassite.util.MyApp;
/**
 * 文章增加页面
 * @author Administrator
 *
 */
public class NoteAddActivity extends Activity {

	private RecyclerView mRecyclerView;
	
	MyRecycyViewAdapter recycyViewAdapter;
	// 声明一个文字标题输入框、文章内容输入框
	EditText ziLiaoTitleEdit, ziLiaoDescEt;// , ziLiaoModeEdit;
	// 图片显示线性布局
	LinearLayout showPhotosll;
//	// 获取图片来源按钮
//	ImageButton showPhotosIB;
	// 保存文章按钮、增加文章类别按钮
	ImageView addClassesIv;
	// 图片显示按钮
	TextView ziLiaoPhotoTv,saveZiLiaoIv;// ziLiaoDescTv,;
	// Button ziLiaoModeBtn;
//	// 文章图片显示空件
//	ViewPager viewPager;
//
//	private LinearLayout container;
	// 创建文章标题
	String ziLiaoTitle = "";
	// 创建文章类别
	String ziLiaoClasses = "";
	// 创建文章描述
	String ziLiaoDesc = "";
	// 创建文章附件路径
	String ziLiaoModeUrl = "";
	// 创建文章时间
	String ziLiaoTime = "";
	// 创建多张文章图片
	List<Photo> ziLiaoPhotos = new ArrayList<Photo>();
	// 文章显示布局
//	List<View> ziLiaoPhotosView = new ArrayList<View>();
//	// 文章显示适配器
//	MyViewPagerAdapter viewPagerAdapter;
	// 文章类别选择器
	Spinner calssesSpinner;
	// 文章类别选择适配器
	ClassesSpinnerAdapter classesSpinnerAdapter;
	// 定义一个类别
	List<Classes> classesList = new ArrayList<Classes>();

	Classes classes;

	// 声明PopupWindow对象的引用
	private PopupWindow popupWindow;

	ImageButton backIb;// 声明一个返回按钮
	public final int ZI_LIAO_DESC_CODE = 1;
	public final int ZI_LIAO_SELECT_CODE = 2;
	public final int ZI_LIAO_CAMARA_CODE = 3;
	public final String IMAGE_TYPE = "image/*";
	public final int ZI_LIAO_XIANG_CE_CODE = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置去除Android自带标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置竖屏显示
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_add_layout);
		try {
			initView();
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		classes = new Classes();
		mRecyclerView=(RecyclerView)this.findViewById(R.id.id_recyclerview_horizontal);
		backIb = (ImageButton) this.findViewById(R.id.add_zi_liao_back_iv);
		showPhotosll = (LinearLayout) this
				.findViewById(R.id.add_zi_liao_show_photo_ll);
		ziLiaoTitleEdit = (EditText) this.findViewById(R.id.add_zi_liao_title);
		addClassesIv = (ImageView) this.findViewById(R.id.add_clsses_name_iv);
		ziLiaoDescEt = (EditText) this.findViewById(R.id.add_zi_liao_desc_et);

		saveZiLiaoIv = (TextView) this.findViewById(R.id.save_zi_liao);
		ziLiaoPhotoTv = (TextView) this.findViewById(R.id.add_zi_liao_photo);

		calssesSpinner = (Spinner) this
				.findViewById(R.id.add_clsses_name_spinner_list);
		classesSpinnerAdapter = new ClassesSpinnerAdapter(
				NoteAddActivity.this, classesList);
		// classesSpinnerAdapter.setDropDownViewResource(R.layout.myspinner_dropdown);
		calssesSpinner.setAdapter(classesSpinnerAdapter);
		//设置布局管理器
		LinearLayoutManager manager=new LinearLayoutManager(this);
		manager.setOrientation(LinearLayoutManager.HORIZONTAL);
		mRecyclerView.setLayoutManager(manager);
		recycyViewAdapter=new MyRecycyViewAdapter(NoteAddActivity.this,ziLiaoPhotos);
		mRecyclerView.setAdapter(recycyViewAdapter);
//
//		container = (LinearLayout) this
//				.findViewById(R.id.add_zi_liao_photo_container);
//		viewPager = (ViewPager) this.findViewById(R.id.add_zi_liao_show_photo);
//
//		// 1.设置幕后item的缓存数目
//		viewPager.setOffscreenPageLimit(3);
//		// 2.设置页与页之间的间距
//		viewPager.setPageMargin(5);
//		// 3.将父类的touch事件分发至viewPgaer，否则只能滑动中间的一个view对象
//		container.setOnTouchListener(new View.OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				return viewPager.dispatchTouchEvent(event);
//			}
//		});
//		viewPagerAdapter = new MyViewPagerAdapter(AddZiLiaoActivity.this,
//				ziLiaoPhotos, ziLiaoPhotosView);
//		// //////////////////////////////////////////////////////////////
//		viewPager.setAdapter(viewPagerAdapter); // 为viewpager设置adapter

	}

	private void initListener() {
		// TODO Auto-generated method stub
		calssesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				classes = classesList.get(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		addClassesIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NoteAddActivity.this,
						NoteClassesAddActivity.class);
				NoteAddActivity.this.startActivity(intent);
			}
		});
//		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
//				if (viewPager != null) {
//					viewPager.invalidate();
//				}
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
		ziLiaoPhotoTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPhotosll.setVisibility(View.VISIBLE);
				showZiLiaoPhoto();
				popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
			}

		});

		saveZiLiaoIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 将数据保存至json中
				saveZiLiaoDataForFile();
			}
		});

		backIb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showIfSaveDilaog();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case ZI_LIAO_CAMARA_CODE:
				String photoName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()).toString().trim()
						+ ".jpg";
				String filename = MyApp.jsonFilePath + "ziliao/photo/"
						+ photoName;
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");
				try {
					File photoDir = new File(MyApp.jsonFilePath
							+ "ziliao/photo");
					if (!photoDir.exists()) {
						photoDir.mkdirs();
					}
					File file = new File(filename);
					if (!file.exists()) {
						file.createNewFile();
					}
					FileOutputStream outputStream = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
							outputStream);
					Photo photo = new Photo();
					photo.setPhotoId(UUID.randomUUID().toString());
					photo.setUrl(filename);
					ziLiaoPhotos.add(photo);
//					ziLiaoPhotosView.add(LayoutInflater.from(
//							AddZiLiaoActivity.this).inflate(
//							R.layout.image_recyc_view_item, null));
					recycyViewAdapter.addViews(ziLiaoPhotos);
					recycyViewAdapter.notifyDataSetChanged();
//					viewPagerAdapter.addViews(ziLiaoPhotos, ziLiaoPhotosView);
//					viewPagerAdapter.notifyDataSetChanged();
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case ZI_LIAO_XIANG_CE_CODE:

				try {
					Uri xiangCeUri = data.getData();
					String res = getPath(NoteAddActivity.this, xiangCeUri);
					Photo photo = new Photo();
					photo.setPhotoId(UUID.randomUUID().toString());
					photo.setUrl(res);
					ziLiaoPhotos.add(photo);
//					ziLiaoPhotosView.add(LayoutInflater.from(
//							AddZiLiaoActivity.this).inflate(
//							R.layout.image_recyc_view_item, null));
					recycyViewAdapter.addViews(ziLiaoPhotos);
					recycyViewAdapter.notifyDataSetChanged();
//					viewPagerAdapter.addViews(ziLiaoPhotos, ziLiaoPhotosView);
//					viewPagerAdapter.notifyDataSetChanged();

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}

	}

//	private void showFileChooer() {
//		// TODO Auto-generated method stub
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//		intent.setType("*/*");
//		intent.addCategory(Intent.CATEGORY_OPENABLE);
//		try {
//			startActivityForResult(
//					Intent.createChooser(intent, "select a file"),
//					ZI_LIAO_SELECT_CODE);
//		} catch (Exception e) {
//			Toast.makeText(AddZiLiaoActivity.this,
//					"please install a file manager", 2000);
//		}
//	}

	private String getPath(Context context, Uri uri) {

		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver().query(uri, projection,
						null, null, null);
				int column = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	private void saveZiLiaoDataForFile() {
		// TODO Auto-generated method stub
		ziLiaoTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		ziLiaoTitle = ziLiaoTitleEdit.getText().toString().trim();
		ziLiaoDesc = ziLiaoDescEt.getText().toString().trim();
		if ("".equals(ziLiaoTitle) || "".equals(ziLiaoDesc)
				|| "".equals(classes)) {
			Toast.makeText(NoteAddActivity.this, "“*”为必填项，不能为空。", 1000)
					.show();
		} else {
			// 保存至数据库sqlite
			ZiLiaoService ziLiaoService = new ZiLiaoService(
					NoteAddActivity.this);
			PhotoService photoService = new PhotoService(NoteAddActivity.this);
			ZiLiao ziLiao = new ZiLiao();
			ziLiao.setUuid(UUID.randomUUID().toString());
			ziLiao.setTitle(ziLiaoTitle);
			ziLiao.setClasses(classes);
			ziLiao.setDesc(ziLiaoDesc);
			ziLiao.setTime(ziLiaoTime);
			try {
				for (Photo photo : ziLiaoPhotos) {
					photo.setZiLiao(ziLiao);
					photoService.insert(photo);
				}
				ziLiaoService.insert(ziLiao);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				Toast.makeText(NoteAddActivity.this, "保存成功！", 1000).show();
				ziLiaoTitleEdit.setText("");
				ziLiaoTitleEdit.setHint("请输入文章标题");
				calssesSpinner.setSelection(0);
				ziLiaoDescEt.setText("");
				ziLiaoDescEt.setHint("请输入文章内容");
				ziLiaoPhotos.clear();
				mRecyclerView.removeAllViews();
//				viewPagerAdapter = new MyViewPagerAdapter(
//						AddZiLiaoActivity.this, ziLiaoPhotos, ziLiaoPhotosView);
//				viewPager.setAdapter(viewPagerAdapter); // 为viewpager设置adapter
			}

		}
	}

	private void showZiLiaoPhoto() {
		// TODO Auto-generated method stub
		if (popupWindow != null) {
			popupWindow.dismiss();
			return;
		} else {
			initPopupWindow();
		}
	}

	private void initPopupWindow() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(NoteAddActivity.this).inflate(
				R.layout.note_get_photo_popup, null);
		popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setAnimationStyle(R.style.popWindow_animation);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		TextView canmoraPhotoTv = (TextView) view
				.findViewById(R.id.pop_camera_tv);
		TextView xiangCePhotoTv = (TextView) view
				.findViewById(R.id.pop_xiangce_tv);


		canmoraPhotoTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				NoteAddActivity.this.startActivityForResult(intent,
						ZI_LIAO_CAMARA_CODE);
			}

		});
		xiangCePhotoTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType(IMAGE_TYPE);
				NoteAddActivity.this.startActivityForResult(intent,
						ZI_LIAO_XIANG_CE_CODE);
			}
		});
		
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});
		
	}

//	private void showZiLiaoPhotoView(View v) {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		classesList = new ClassesService(NoteAddActivity.this)
				.findAllClasses();
		classesSpinnerAdapter.addList(classesList);
		classesSpinnerAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			showIfSaveDilaog();

		}

		return super.dispatchKeyEvent(event);
	}

	private void showIfSaveDilaog() {
		// TODO Auto-generated method stub
		ziLiaoTitle = ziLiaoTitleEdit.getText().toString().trim();
		ziLiaoDesc = ziLiaoDescEt.getText().toString().trim();
		if (!("".equals(ziLiaoTitle)) || !("".equals(ziLiaoDesc))) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					NoteAddActivity.this);
			AlertDialog dialog = builder.create();

			builder.setTitle("数据保存")
					.setView(
							LayoutInflater.from(NoteAddActivity.this)
									.inflate(R.layout.if_save_dialog, null))
					.setPositiveButton("保存",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									saveZiLiaoDataForFile();
									dialog.dismiss();
								}
							})
					.setNegativeButton("不用了",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									NoteAddActivity.this.finish();
								}
							}).show();
		} else {
			NoteAddActivity.this.finish();
		}
	}

}
