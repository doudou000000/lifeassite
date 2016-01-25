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
    //��������
	public String insert(ZiLiao ziLiao) {
		if (dao.insertZiLiao(ziLiao) == 1) {
			return "����ɹ���";
		} else {
			return null;
		}
	}

	/**
	 * ��ѯ��������
	 */
	public List<ZiLiao> findAllZiLiao() {

		return dao.findAllZiLiaos();

	}

	/**
	 * ����id��ѯ����
	 */
	public ZiLiao findById(String id) {

		try {
			return dao.findZiLiao(id);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * ����idɾ������
	 */
	public String deleteById(String id) {

			if(dao.deleteZiLiao(id)==1){
				return "ɾ���ɹ�";
			}else{
				return "ɾ��ʧ��";
			}

	}

	/**
	 * �������ϵ����ơ����ģ����ѯ
	 */
	public List<ZiLiao> findByName(String title,String classes) {

			return dao.findByName(title,classes);

	}
	
	/**
	 * ��ҳ��ѯ
	 */
	public List<ZiLiao> limitLoad(int page) {

			return dao.limitZiLiao(page);

	}
	
}
