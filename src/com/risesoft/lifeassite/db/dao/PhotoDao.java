package com.risesoft.lifeassite.db.dao;

import java.sql.SQLException;

import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.risesoft.lifeassite.entity.note.Photo;

public class PhotoDao {

	//
	DataBaseHelper baseHelper=null;
	//
	private Dao<Photo, String> photoDao=null;
	//
	private RuntimeExceptionDao<Photo, String> simpleRuntimephotoDao=null;
		
	public PhotoDao(Context context) {
		super();
		baseHelper=new DataBaseHelper(context);
	}

	public Dao<Photo, String> getPhotoDao() throws SQLException{
	
	if(photoDao==null){
		photoDao=baseHelper.getDao(Photo.class);
	}
	
	return photoDao;		
}
	
	public RuntimeExceptionDao<Photo, String> getSimpleDataPhotoDao() throws SQLException{
	
	if(simpleRuntimephotoDao==null){
		simpleRuntimephotoDao=baseHelper.getRuntimeExceptionDao(Photo.class);
	}
	
	return simpleRuntimephotoDao;		
}
	
	/**
	 * ����һ��Photo����
	 */
	public int insertPhoto(Photo photo) {
		
		try {
			RuntimeExceptionDao<Photo, String> dao=getSimpleDataPhotoDao();
			int returnValue=dao.create(photo);
			return returnValue;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}
	
	/**
	 * ��ѯ���е�ͼƬ
	 */
	public List<Photo> findAllPhotos(){
		
		try {
			RuntimeExceptionDao<Photo, String> dao=getSimpleDataPhotoDao();
			return dao.queryForAll();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * ��ѯĳһ��ͼƬ
	 */
	public Photo findPhoto(String photoId){
		
		try {
			RuntimeExceptionDao<Photo, String> dao=getSimpleDataPhotoDao();
			return dao.queryForId(photoId);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * ɾ��ĳһ��ͼƬ
	 */
	public int deletePhoto(String photoId){
		
		try {
			RuntimeExceptionDao<Photo, String> dao=getSimpleDataPhotoDao();
			return dao.deleteById(photoId);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
}
