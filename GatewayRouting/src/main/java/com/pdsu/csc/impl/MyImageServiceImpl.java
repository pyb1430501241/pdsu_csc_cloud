package com.pdsu.csc.impl;

import com.pdsu.csc.bean.*;
import com.pdsu.csc.dao.MyImageMapper;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.handler.AbstractHandler;
import com.pdsu.csc.service.impl.AbstractMyImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author 半梦
 *
 */
@Log4j2
@Service("myImageService")
public class MyImageServiceImpl extends AbstractMyImageService {

	@Autowired
	private MyImageMapper myImageMapper;

	@Override
	public MyImage selectImagePathByUid(@NonNull Integer uid) throws NotFoundUidException {
		if(!isExistByUid(uid)) {
			throw new NotFoundUidException("该用户不存在");
		}
		MyImageExample example = new MyImageExample();
		MyImageExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		MyImage myImage = selectByExample(example);
		return myImage == null ? new MyImage(uid, AbstractHandler.DEFAULT_USER_IMG_NAME) : myImage;
	}

	@Override
	@NonNull
	public List<MyImage> selectListByExample(@Nullable MyImageExample example) {
		return myImageMapper.selectByExample(example);
	}

}
