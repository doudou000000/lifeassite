package com.risesoft.lifeassite.view.studyfragment.note;

import java.util.ArrayList;
import java.util.List;
import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.adapter.ZiLiaoAdapter;
import com.risesoft.lifeassite.db.service.ZiLiaoService;
import com.risesoft.lifeassite.entity.note.ZiLiao;
import com.risesoft.lifeassite.util.ViewHolder;
import com.risesoft.lifeassite.view.SlideMenuActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteFragment extends Fragment {

	View noteView;
	private ListView ziLiaoListView;// 声明一个文章列表空件

	private ImageView ziLiaoAdd, searchTv;// 声明添加文章按钮

	private ZiLiaoAdapter ziLiaoAdapter;// 文章显示适配器

	private List<ZiLiao> ziLiaos;// 第一次加载时的文章

	private LinearLayout piLiangDeletell;// 文章批量删除布局，默认为不显示

	Button quanXuanBtn, deleteAllBtn;// 声明一个全选按钮、删除按钮，默认不显示

	TextView loadMoreTv;// 声明一个编辑按钮、加载更多按钮、搜索按钮
	ImageView cancelAllBtn;
	ImageButton noteMenuLeft;
	private View footView;// 文章列表底部布局（加载更多布局）

	private boolean isMultChoice = false;// 是否多选

	private Vibrator vibrator;// 声明一个振动器

	int page = 1;// 第一页

	int totlePage;// 总共页数
	List<ZiLiao> totals;// 所有的文章

	boolean finish = true;// 加载是否完成，默认true
	boolean isAddData = true;// 是否需要重新从数据库获取数据，默认true

	public List<ZiLiao> isSelecZiLiaotId = new ArrayList<ZiLiao>();

	//Button getAllJsonBnt;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		noteView = LayoutInflater.from(getActivity()).inflate(
				R.layout.note_layout, null);
		try {
			// 初始化数据方法
			initView();
			// 监听事件方法
			initListener();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return noteView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		//getAllJsonBnt=(Button)view.findViewById(R.id.get_json_btn);
		footView = LayoutInflater.from(getActivity()).inflate(
				R.layout.note_list_foot_view, null);
		loadMoreTv = (TextView) footView.findViewById(R.id.loading_more_tv);

		searchTv = (ImageView) noteView.findViewById(R.id.note_search_Iv);

		noteMenuLeft = (ImageButton) noteView.findViewById(R.id.note_menu_left);
		ziLiaoListView = (ListView) noteView.findViewById(R.id.note_list_view);

		ziLiaoAdd = (ImageView) noteView.findViewById(R.id.note_add_Iv);
		// 得到批量删除布局
		piLiangDeletell = (LinearLayout) noteView.findViewById(R.id.note_delete_ll);
		quanXuanBtn = (Button) noteView.findViewById(R.id.note_delete_qx_btn);
		deleteAllBtn = (Button) noteView.findViewById(R.id.note_delete_dl_btn);
		cancelAllBtn = (ImageView) noteView.findViewById(R.id.note_cancel_tv);
		// new 一个第一次加载时的文章
		ziLiaos = new ArrayList<ZiLiao>();
		ziLiaoAdapter = new ZiLiaoAdapter(getActivity(), ziLiaos);
		// 将加载更多布局添加到文章列表listview底部
		ziLiaoListView.addFooterView(footView);
		// 为listview设置适配器
		ziLiaoListView.setAdapter(ziLiaoAdapter);
		// 将加载更多布局从文章列表listview底部移除
		ziLiaoListView.removeFooterView(footView);
	}

	private void initListener() {
		
//		getAllJsonBnt.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				List<ZiLiao> liaos=new ZiLiaoService(getActivity()).findAllZiLiao();
//				
//				System.out.println(FsatJsonHelper.serialze(liaos));
//				
//			}
//		});
		noteMenuLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SlideMenuActivity.menu.toggle();
			}
		});
		// 添加搜索按钮点击事件
		searchTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转至搜索页面
				isAddData = false;
				Intent intent = new Intent(getActivity(),
						NoteSearchDataActivity.class);
				getActivity().startActivity(intent);
			}
		});
		// 为加载更多按钮添加事件
		loadMoreTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 加载更多文章
				loadMoreData();

			}

		});
		// 为新增文章添加监听事件
		ziLiaoAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 是否需要重新从数据库获取数据
				isAddData = true;
				Intent addIntent = new Intent(getActivity(),
						NoteAddActivity.class);
				getActivity().startActivity(addIntent);
			}
		});

		ziLiaoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (isMultChoice) {
					ViewHolder holder = (ViewHolder) view.getTag();
					holder.cb.toggle();
					if (holder.cb.isChecked() == true
							&& (!isSelecZiLiaotId.contains(position))) {
						ziLiaoAdapter.isCheck.put(position, true);
						isSelecZiLiaotId.add(ziLiaos.get(position));
					} else {
						ziLiaoAdapter.isCheck.put(position, false);
						if (isSelecZiLiaotId.contains(ziLiaos.get(position))) {
							isSelecZiLiaotId.remove(ziLiaos.get(position));
						}
					}
					ziLiaoAdapter.notifyDataSetChanged();
				} else {
					isAddData = false;
					Intent intent = new Intent(getActivity(),
							NoteCotentActivity.class);
					intent.putExtra("ziLiaoId", ziLiaos.get(position).getUuid());
					getActivity().startActivity(intent);
				}
			}
		});

		quanXuanBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < ziLiaoAdapter.isCheck.size(); i++) {
					ziLiaoAdapter.isCheck.put(i, true);
				}
				isSelecZiLiaotId.addAll(ziLiaos);
				ziLiaoAdapter.notifyDataSetChanged();

			}
		});

		deleteAllBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ZiLiaoService ziLiaoService = new ZiLiaoService(getActivity());
				try {
					if (isSelecZiLiaotId.size() > 0) {
						for (ZiLiao ziLiao : isSelecZiLiaotId) {
							ziLiaoService.deleteById(ziLiao.getUuid());
						}
						isSelecZiLiaotId.clear();
						isMultChoice = false;
						isAddData = true;
						getZiLiaoDataSize();

					} else {
						Toast.makeText(getActivity(), "您还没有选择，请选择您要删除的对象！！",
								1000).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "删除失败！！", 1000).show();
				} finally {
					Toast.makeText(getActivity(), "删除成功！！", 1000).show();
				}

			}
		});

		cancelAllBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isMultChoice) {
					isMultChoice = false;
					cancelAllBtn.setBackgroundResource(R.drawable.note_delete);
					piLiangDeletell.setVisibility(View.GONE);
					for (int i = 0; i < ziLiaoAdapter.visiblecheck.size(); i++) {
						ziLiaoAdapter.visiblecheck.put(i, CheckBox.GONE);
						ziLiaoAdapter.visibleJianTou.put(i, View.VISIBLE);
						ziLiaoAdapter.isCheck.put(i, false);
					}
				} else {
					isMultChoice = true;
					cancelAllBtn.setBackgroundResource(R.drawable.note_cancel);
					piLiangDeletell.setVisibility(View.VISIBLE);
					for (int i = 0; i < ziLiaoAdapter.visiblecheck.size(); i++) {
						ziLiaoAdapter.visiblecheck.put(i, CheckBox.VISIBLE);
						ziLiaoAdapter.visibleJianTou.put(i, View.GONE);
						ziLiaoAdapter.isCheck.put(i, false);
					}
				}
				ziLiaoAdapter.notifyDataSetChanged();
			}
		});

		ziLiaoListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				int tatalItem = firstVisibleItem + visibleItemCount;// 已加载项
				if (tatalItem == totalItemCount && page < totlePage && finish) {
					finish = false;
					ziLiaoListView.addFooterView(footView);
				}
			}
		});
		ziLiaoListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						vibrator = (Vibrator) getActivity().getSystemService(
								Service.VIBRATOR_SERVICE);
						vibrator.vibrate(100);
						isMultChoice = true;
						cancelAllBtn.setBackgroundResource(R.drawable.note_cancel);
						piLiangDeletell.setVisibility(View.VISIBLE);
						for (int i = 0; i < ziLiaoAdapter.visiblecheck.size(); i++) {
							ziLiaoAdapter.visiblecheck.put(i, CheckBox.VISIBLE);
							ziLiaoAdapter.visibleJianTou.put(i, View.GONE);
							ziLiaoAdapter.isCheck.put(i, false);
						}
						ziLiaoAdapter.notifyDataSetChanged();
						return true;
					}
				});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			getZiLiaoDataSize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getZiLiaoDataSize() {
		// TODO Auto-generated method stub
		piLiangDeletell.setVisibility(View.GONE);
		cancelAllBtn.setBackgroundResource(R.drawable.note_delete);
		ZiLiaoService ziLiaoService = new ZiLiaoService(getActivity());
		totals = ziLiaoService.findAllZiLiao();
		if (totals.size() > 15) {
			if (totals.size() % 15 == 0) {
				totlePage = totals.size() / 15;
			} else {
				totlePage = (totals.size() / 15) + 1;
			}
		} else {
			totlePage = 1;
		}
		if (isAddData) {
			try {
				page = 1;
				ziLiaos = new ArrayList<ZiLiao>();
				isAddData = false;
				List<ZiLiao> firstZiLiaos = ziLiaoService.limitLoad(page);
				List<ZiLiao> newZiLiaos = new ArrayList<ZiLiao>();
				if (firstZiLiaos.size() > 0) {
					for (int i = 0; i < firstZiLiaos.size(); i++) {
						newZiLiaos.add(ziLiaoService.findById(firstZiLiaos.get(
								i).getUuid()));
					}
					ziLiaos.addAll(newZiLiaos);
					for (int i = 0; i < ziLiaos.size(); i++) {
						ziLiaoAdapter.visiblecheck.put(i, CheckBox.GONE);
						ziLiaoAdapter.visibleJianTou.put(i, View.VISIBLE);
						ziLiaoAdapter.isCheck.put(i, false);
					}
				} else {
					Toast.makeText(getActivity(), "还没有数据，请添加！！！！", 2000).show();
				}
				ziLiaoAdapter.addList(ziLiaos);
				ziLiaoAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// File file = new File(MyApp.jsonFilePath + "ziliao/json/ziliao.txt");
	// String res = "";
	// String json = "";
	// if (file.exists()) {
	// try {
	// FileInputStream fs = new FileInputStream(file);
	// int lenth = fs.available();
	// byte[] buffer = new byte[lenth];
	// fs.read(buffer);
	// res = EncodingUtils.getString(buffer, "uft-8");
	// json = "[" + res.substring(0, res.lastIndexOf(",")) + "]";
	// ziLiaos = FsatJsonHelper.deserialzeList(json, ZiLiao.class);
	// ziLiaoAdapter.addList(ziLiaos);
	// ziLiaoAdapter.notifyDataSetChanged();
	// fs.close();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	// }

	private void loadMoreData() {
		// TODO Auto-generated method stub
		page++;
		finish = true;
		ZiLiaoService ziLiaoService = new ZiLiaoService(getActivity());
		try {
			List<ZiLiao> loadMoreDatas = ziLiaoService.limitLoad(page);
			List<ZiLiao> newZiLiaos = new ArrayList<ZiLiao>();
			if (loadMoreDatas.size() > 0) {
				for (ZiLiao ziLiao : loadMoreDatas) {
					newZiLiaos.add(ziLiaoService.findById(ziLiao.getUuid()));
				}
				ziLiaos.addAll(newZiLiaos);
				ziLiaoAdapter.addList(ziLiaos);
				ziLiaoAdapter.notifyDataSetChanged();
				if (ziLiaoListView.getFooterViewsCount() > 0) {
					ziLiaoListView.removeFooterView(footView);
				}
				piLiangDeletell.setVisibility(View.GONE);
				cancelAllBtn.setBackgroundResource(R.drawable.note_delete);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
