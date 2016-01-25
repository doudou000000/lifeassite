package com.risesoft.lifeassite.db.service;

import java.util.List;

import com.risesoft.lifeassite.db.dao.ClassesDao;
import com.risesoft.lifeassite.entity.note.Classes;

import android.content.Context;

public class ClassesService {
    //���dao
	ClassesDao dao;

	public ClassesService(Context context) {
		super();
		dao = new ClassesDao(context);
	}
    //�������
	public String insert(Classes classes) {
		if (dao.insertClasses(classes) == 1) {
			return "����ɹ���";
		} else {
			return null;
		}
	}
    //�������
	public void update(Classes classes) {
		dao.updateClasses(classes);
	}
	
	/**
	 * ��ѯ�������
	 */
	public List<Classes> findAllClasses() {

		return dao.findAllClassess();

	}

	/**
	 * ����id��ѯ���
	 */
	public Classes findById(String id) {

		try {
			return dao.findClasses(id);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * ����idɾ�����
	 */
	public String deleteById(String id) {

			if(dao.deleteClasses(id)==1){
				return "ɾ���ɹ�";
			}else{
				return "ɾ��ʧ��";
			}

	}

}
