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
 * ��android sharedpreferences����List����
 * 
 * @author Administrator
 * 
 */
public class PreferencesSaveList {

	public static String SceneList2String(List movieInfoDatas) {
		// ʵ����һ��ByteArrayOutputStream��������װ��ѹ������ֽ��ļ���
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// Ȼ�󽫵õ����ַ�����װ�ص�ObjectOutputStream
		ObjectOutputStream objectOutputStream;
		String SceneListString = null;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(movieInfoDatas);
			// writeObject ��������д���ض���Ķ����״̬���Ա���Ӧ�� readObject �������Ի�ԭ��
			// �����Base64.encode���ֽ��ļ�ת����Base64���뱣����String��
			SceneListString = new String(Base64.encode(
					byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
			// �ر�objectOutputStream
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
