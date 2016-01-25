package com.risesoft.lifeassite.db.dao;

import java.sql.SQLException;

import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.risesoft.lifeassite.entity.note.Classes;

public class ClassesDao {

	DataBaseHelper baseHelper=null;
	
	private Dao<Classes, String> classesDao=null;
	
	private RuntimeExceptionDao<Classes, String> simpleRuntimeClassesDao=null;
		
	public ClassesDao(Context context) {
		super();
		baseHelper=new DataBaseHelper(context);
	}

	public Dao<Classes, String> getClassesDao() throws SQLException{
	
	if(classesDao==null){
		classesDao=baseHelper.getDao(Classes.class);
	}
	
	return classesDao;		
}
	
	public RuntimeExceptionDao<Classes, String> getSimpleDataClassesDao() throws SQLException{
	
	if(simpleRuntimeClassesDao==null){
		simpleRuntimeClassesDao=baseHelper.getRuntimeExceptionDao(Classes.class);
	}
	
	return simpleRuntimeClassesDao;		
}
	
	/**
	 * 插入一条Classes数据
	 */
	public int insertClasses(Classes classes) {
		
		try {
			RuntimeExceptionDao<Classes, String> dao=getSimpleDataClassesDao();
			int returnValue=dao.create(classes);
			return returnValue;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}
	
	/**
	 * 更新一条Classes数据
	 */
	public int updateClasses(Classes classes) {
		
		try {
			RuntimeExceptionDao<Classes, String> dao=getSimpleDataClassesDao();
			int returnValue=dao.update(classes);
			return returnValue;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}
	
	/**
	 * 查询所有的图片
	 */
	public List<Classes> findAllClassess(){
		
		try {
			RuntimeExceptionDao<Classes, String> dao=getSimpleDataClassesDao();
			return dao.queryForAll();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * 查询某一个图片
	 */
	public Classes findClasses(String ClassesId){
		
		try {
			RuntimeExceptionDao<Classes, String> dao=getSimpleDataClassesDao();
			return dao.queryForId(ClassesId);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * 删除某一个图片
	 */
	public int deleteClasses(String ClassesId){
		
		try {
			RuntimeExceptionDao<Classes, String> dao=getSimpleDataClassesDao();
			return dao.deleteById(ClassesId);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
}
