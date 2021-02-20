package com.pdsu.csc.impl;

import com.pdsu.csc.bean.MyEmail;
import com.pdsu.csc.bean.MyEmailExample;
import com.pdsu.csc.zuulDao.MyEmailMapper;
import com.pdsu.csc.service.impl.AbstractMyEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author 半梦
 *
 */
@Service("myEmailService")
public class MyEmailServiceImpl extends AbstractMyEmailService {

	@Autowired
	private MyEmailMapper myEmailMapper;
	
	@Override
	public MyEmail selectMyEmailByUid(@NonNull Integer uid) {
		MyEmailExample example = new MyEmailExample();
		MyEmailExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<MyEmail> list = myEmailMapper.selectByExample(example);
		return list.size() == 0 ? null : list.get(0);
	}

}
