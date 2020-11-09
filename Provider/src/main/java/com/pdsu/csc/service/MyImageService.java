package com.pdsu.csc.service;

import java.util.List;

import com.pdsu.csc.bean.MyImage;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import org.springframework.lang.NonNull;

/**
 * 该接口用于提供与头像相关的方法
 * @author 半梦
 *
 */
public interface MyImageService {
	
	//根据一组学号来获取一组头像地址
	public List<MyImage> selectImagePathByUids(@NonNull List<Integer> uids);

	//根据学号来获取头像地址
	public MyImage selectImagePathByUid(@NonNull Integer uid) throws NotFoundUidException;
	
	public boolean countByUid(@NonNull Integer uid);

	public boolean insert(@NonNull MyImage myImage) throws NotFoundUidException;

	public boolean update(@NonNull MyImage myImage) throws NotFoundUidException;
	
}
