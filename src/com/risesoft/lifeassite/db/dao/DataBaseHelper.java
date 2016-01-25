package com.risesoft.lifeassite.db.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.risesoft.lifeassite.entity.note.Classes;
import com.risesoft.lifeassite.entity.note.Photo;
import com.risesoft.lifeassite.entity.note.ZiLiao;
/**
 * 数据库对象映射操作（ormlite）
 * @author Administrator
 *
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    //数据库名称
	private static final String DATABASE_NAME="ziliaodb.db";
	//数据库版本
	private static final int DATABASE_VERSION=2;
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
    //创建数据库表
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// 
		try {
			//创建表photo、ziliao、classes
			TableUtils.createTable(arg1, Photo.class);
			TableUtils.createTable(arg1, ZiLiao.class);
			TableUtils.createTable(arg1, Classes.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
    //更新数据库表
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

		try {
			//删除表photo、ziliao、classes
			TableUtils.dropTable(arg1, Photo.class,true);
			TableUtils.dropTable(arg1, ZiLiao.class,true);
			TableUtils.dropTable(arg1, Classes.class,true);
			//创建表
			onCreate(arg0, arg1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
		
}
