package com.risesoft.lifeassite.db.service;

import java.util.List;

import com.risesoft.lifeassite.db.dao.ClassesDao;
import com.risesoft.lifeassite.entity.note.Classes;

import android.content.Context;

public class ClassesService {
    //类别dao
	ClassesDao dao;

	public ClassesService(Context context) {
		super();
		dao = new ClassesDao(context);
	}
    //保存类别
	public String insert(Classes classes) {
		if (dao.insertClasses(classes) == 1) {
			return "保存成功！";
		} else {
			return null;
		}
	}
    //更新类别
	public void update(Classes classes) {
		dao.updateClasses(classes);
	}
	
	/**
	 * 查询所有类别
	 */
	public List<Classes> findAllClasses() {

		return dao.findAllClassess();

	}

	/**
	 * 根据id查询类别
	 */
	public Classes findById(String id) {

		try {
			return dao.findClasses(id);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 根据id删除类别
	 */
	public String deleteById(String id) {

			if(dao.deleteClasses(id)==1){
				return "删除成功";
			}else{
				return "删除失败";
			}

	}

}
