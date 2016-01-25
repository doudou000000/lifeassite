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
    //������Ƭ
	public String insert(Photo photo) {
		if (dao.insertPhoto(photo) == 1) {
			return "����ɹ���";
		} else {
			return null;
		}
	}

	/**
	 * ��ѯ������Ƭ
	 */
	public List<Photo> findAllPhoto() {

		return dao.findAllPhotos();

	}

	/**
	 * ����id��ѯ��Ƭ
	 */
	public Photo findById(String id) {

		try {
			return dao.findPhoto(id);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * ����idɾ����Ƭ
	 */
	public String deleteById(String id) {

			if(dao.deletePhoto(id)==1){
				return "ɾ���ɹ�";
			}else{
				return "ɾ��ʧ��";
			}

	}

}
