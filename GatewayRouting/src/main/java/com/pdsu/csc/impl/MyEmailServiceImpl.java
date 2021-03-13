package com.pdsu.csc.impl;

import com.pdsu.csc.bean.MyEmail;
import com.pdsu.csc.bean.MyEmailExample;
import com.pdsu.csc.dao.MyEmailMapper;
import com.pdsu.csc.service.impl.AbstractMyEmailService;
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
@Service("myEmailService")
public class MyEmailServiceImpl extends AbstractMyEmailService {

	@Autowired
	private MyEmailMapper myEmailMapper;
	
	@Override
	public MyEmail selectMyEmailByUid(@NonNull Integer uid) {
		MyEmailExample example = new MyEmailExample();
		MyEmailExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return selectByExample(example);
	}

	@Override
	@NonNull
	public List<MyEmail> selectListByExample(@Nullable MyEmailExample example) {
		return myEmailMapper.selectByExample(example);
	}

}
