package com.risesoft.lifeassite.db.service;

import java.util.List;

import com.risesoft.lifeassite.db.dao.ZiLiaoDao;
import com.risesoft.lifeassite.entity.note.ZiLiao;

import android.content.Context;

public class ZiLiaoService {

	ZiLiaoDao dao;
 
	public ZiLiaoService(Context context) {
		super();
		dao = new ZiLiaoDao(context);
	}
    //保存资料
	public String insert(ZiLiao ziLiao) {
		if (dao.insertZiLiao(ziLiao) == 1) {
			return "保存成功！";
		} else {
			return null;
		}
	}

	/**
	 * 查询所有资料
	 */
	public List<ZiLiao> findAllZiLiao() {

		return dao.findAllZiLiaos();

	}

	/**
	 * 根据id查询资料
	 */
	public ZiLiao findById(String id) {

		try {
			return dao.findZiLiao(id);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 根据id删除资料
	 */
	public String deleteById(String id) {

			if(dao.deleteZiLiao(id)==1){
				return "删除成功";
			}else{
				return "删除失败";
			}

	}

	/**
	 * 根据资料的名称、类别模糊查询
	 */
	public List<ZiLiao> findByName(String title,String classes) {

			return dao.findByName(title,classes);

	}
	
	/**
	 * 分页查询
	 */
	public List<ZiLiao> limitLoad(int page) {

			return dao.limitZiLiao(page);

	}
	
}
