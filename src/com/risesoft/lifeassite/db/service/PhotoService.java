package com.risesoft.lifeassite.db.service;

import java.util.List;

import com.risesoft.lifeassite.db.dao.PhotoDao;
import com.risesoft.lifeassite.entity.note.Photo;

import android.content.Context;

public class PhotoService {

	PhotoDao dao;

	public PhotoService(Context context) {
		super();
		dao = new PhotoDao(context);
	}
    //保存照片
	public String insert(Photo photo) {
		if (dao.insertPhoto(photo) == 1) {
			return "保存成功！";
		} else {
			return null;
		}
	}

	/**
	 * 查询所有照片
	 */
	public List<Photo> findAllPhoto() {

		return dao.findAllPhotos();

	}

	/**
	 * 根据id查询照片
	 */
	public Photo findById(String id) {

		try {
			return dao.findPhoto(id);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 根据id删除照片
	 */
	public String deleteById(String id) {

			if(dao.deletePhoto(id)==1){
				return "删除成功";
			}else{
				return "删除失败";
			}

	}

}
