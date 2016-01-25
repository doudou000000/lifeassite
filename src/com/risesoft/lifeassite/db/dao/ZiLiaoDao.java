package com.risesoft.lifeassite.db.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.risesoft.lifeassite.entity.note.ZiLiao;

public class ZiLiaoDao{

	DataBaseHelper baseHelper = null;

	private Dao<ZiLiao, String> ZiLiaoDao = null;

	private RuntimeExceptionDao<ZiLiao, String> simpleRuntimeZiLiaoDao = null;

	public ZiLiaoDao(Context context) {
		super();
		baseHelper = new DataBaseHelper(context);
	}

	public Dao<ZiLiao, String> getZiLiaoDao() throws SQLException {

		if (ZiLiaoDao == null) {
			ZiLiaoDao = baseHelper.getDao(ZiLiao.class);
		}

		return ZiLiaoDao;
	}

	public RuntimeExceptionDao<ZiLiao, String> getSimpleDataZiLiaoDao()
			throws SQLException {

		if (simpleRuntimeZiLiaoDao == null) {
			simpleRuntimeZiLiaoDao = baseHelper
					.getRuntimeExceptionDao(ZiLiao.class);
		}

		return simpleRuntimeZiLiaoDao;
	}

	/**
	 * ����һ��ZiLiao����
	 */
	public int insertZiLiao(ZiLiao ziLiao) {

		try {
			RuntimeExceptionDao<ZiLiao, String> dao = getSimpleDataZiLiaoDao();
			int returnValue = dao.create(ziLiao);
			return returnValue;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * ��ѯ���е�
	 */
	public List<ZiLiao> findAllZiLiaos() {

		try {
			RuntimeExceptionDao<ZiLiao, String> dao = getSimpleDataZiLiaoDao();
			return dao.queryBuilder().orderBy("time", false).query();
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * ��ѯĳһ��ͼƬ
	 */
	public ZiLiao findZiLiao(String ZiLiaoId) {

		try {
			RuntimeExceptionDao<ZiLiao, String> dao = getSimpleDataZiLiaoDao();
			return dao.queryForId(ZiLiaoId);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * ɾ��ĳһ��ͼƬ
	 */
	public int deleteZiLiao(String ZiLiaoId) {

		try {
			RuntimeExceptionDao<ZiLiao, String> dao = getSimpleDataZiLiaoDao();
			return dao.deleteById(ZiLiaoId);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * ����titleģ����ѯ
	 */
	public List<ZiLiao> findByName(String title,String str) {
		String sql = null;
//		String sql = "select uuid,time from ZiLiao where title like '%"+title+"%' order by time desc";//��ʱ�併������
		if(str.equals("����")){
			sql = "select uuid from ZiLiao where title like '%"+title+"%' order by time desc";//��ʱ�併������			
		}
		if(str.equals("���")){
			sql = "select z.uuid from ZiLiao z,Classes c where c.name like '%"+title+"%' order by time desc";//��ʱ�併������			
		}
		GenericRawResults<ZiLiao> rawResults = null;
		List<ZiLiao> ziLiaos = new ArrayList<ZiLiao>();
		try {
			RuntimeExceptionDao<ZiLiao, String> dao = getSimpleDataZiLiaoDao();
			rawResults = dao.queryRaw(sql,new RawRowMapper<ZiLiao>() {
				public ZiLiao mapRow(String[] columnNames,
						String[] resultColumns) {
					return new ZiLiao(resultColumns[0]);
				}
			});
			for (ZiLiao ziLiao : rawResults) {
				ziLiaos.add(ziLiao);
			}
			rawResults.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ziLiaos;
	}

	/**
	 * ��ҳ��ѯ
	 */
	public List<ZiLiao> limitZiLiao(int page) {
		String sql= "select uuid,time from ZiLiao order by time desc limit "+15*(page-1)+","+15;//��ʱ�併������			
		
		GenericRawResults<ZiLiao> rawResults = null;
		List<ZiLiao> ziLiaos = new ArrayList<ZiLiao>();
		try {
			RuntimeExceptionDao<ZiLiao, String> dao = getSimpleDataZiLiaoDao();
			rawResults = dao.queryRaw(sql, 
					new RawRowMapper<ZiLiao>() {
				public ZiLiao mapRow(String[] columnNames,
						String[] resultColumns) {
					return new ZiLiao(resultColumns[0],resultColumns[1]);
				}
			});
			for (ZiLiao ziLiao : rawResults) {
				ziLiaos.add(ziLiao);
			}
			rawResults.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ziLiaos;
	}
	
}
