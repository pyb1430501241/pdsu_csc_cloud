package com.pdsu.csc.service;

import java.util.List;

import com.pdsu.csc.bean.MyImage;
import com.pdsu.csc.bean.MyImageExample;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import org.springframework.lang.NonNull;

/**
 * 该接口用于提供与头像相关的方法
 * @author 半梦
 *
 */
public interface MyImageService extends TemplateService<MyImage, MyImageExample> {

	/**
	 * 根据一组 UID 获取对应头像
	 * @param uids 一组 学号
	 */
	public List<MyImage> selectImagePathByUids(@NonNull List<Integer> uids);

	/**
	 * 根据学号来获取头像地址
	 * @param uid 学号
	 */
	public MyImage selectImagePathByUid(@NonNull Integer uid) throws NotFoundUidException;
	
	public boolean update(@NonNull MyImage myImage) throws NotFoundUidException;
	
}
