package com.pdsu.csc.impl;

import com.pdsu.csc.bean.*;
import com.pdsu.csc.zuulDao.MyImageMapper;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.handler.AbstractHandler;
import com.pdsu.csc.service.impl.AbstractMyImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

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
		if(!countByUid(uid)) {
			throw new NotFoundUidException("该用户不存在");
		}
		MyImageExample example = new MyImageExample();
		MyImageExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return myImageMapper.selectByExample(example).size() == 0 ? new MyImage(uid, AbstractHandler.DEFAULT_USER_IMG_NAME) : myImageMapper.selectByExample(example).get(0);
	}

}
