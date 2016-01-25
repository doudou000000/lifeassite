package com.risesoft.lifeassite.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;

import com.risesoft.lifeassite.entity.movie.MovieInfoData;

import android.util.Base64;

/**
 * 用android sharedpreferences保存List集合
 * 
 * @author Administrator
 * 
 */
public class PreferencesSaveList {

	public static String SceneList2String(List movieInfoDatas) {
		// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// 然后将得到的字符数据装载到ObjectOutputStream
		ObjectOutputStream objectOutputStream;
		String SceneListString = null;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(movieInfoDatas);
			// writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
			// 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
			SceneListString = new String(Base64.encode(
					byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
			// 关闭objectOutputStream
			objectOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SceneListString;
	}

	public static List String2SceneList(String SceneListString) {
		List SceneList=null;
		try {
			byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
					Base64.DEFAULT);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					mobileBytes);
			ObjectInputStream objectInputStream = new ObjectInputStream(
					byteArrayInputStream);
			SceneList = (List) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return SceneList;
	}

}
