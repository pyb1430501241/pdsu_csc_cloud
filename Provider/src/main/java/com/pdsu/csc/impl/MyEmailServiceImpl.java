package com.pdsu.csc.impl;

import java.util.List;

import com.pdsu.csc.bean.MyEmail;
import com.pdsu.csc.bean.MyEmailExample;
import com.pdsu.csc.bean.UserInformationExample;
import com.pdsu.csc.dao.MyEmailMapper;
import com.pdsu.csc.dao.UserInformationMapper;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.exception.web.user.email.EmailRepetitionException;
import com.pdsu.csc.exception.web.user.email.NotFoundEmailException;
import com.pdsu.csc.service.impl.AbstractMyEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


/**
 * 
 * @author 半梦
 *
 */
@Service("myEmailService")
public class MyEmailServiceImpl extends AbstractMyEmailService {

	@Autowired
	private MyEmailMapper myEmailMapper;
	
	@Autowired
	private UserInformationMapper userInformationMapper;
	
	@Override
	public boolean countByEmail(@NonNull String email) {
		MyEmailExample example = new MyEmailExample();
		MyEmailExample.Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		return myEmailMapper.countByExample(example) != 0;
	}

	@Override
	public MyEmail selectMyEmailByEmail(@NonNull String email) throws NotFoundEmailException {
		if(!countByEmail(email)) {
			throw new NotFoundEmailException("邮箱不存在");
		}
		MyEmailExample example = new MyEmailExample();
		MyEmailExample.Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		return myEmailMapper.selectByExample(example).get(0);
	}

	@Override
	public MyEmail selectMyEmailByUid(@NonNull Integer uid) {
		MyEmailExample example = new MyEmailExample();
		MyEmailExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<MyEmail> list = myEmailMapper.selectByExample(example);
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public boolean insert(@NonNull MyEmail myEmail) throws EmailRepetitionException, NotFoundUidException {
		if(!countByUid(myEmail.getUid())) {
			throw new NotFoundUidException("该用户不存在");
		}
		if(countByEmail(myEmail.getEmail())) {
			throw new EmailRepetitionException("该邮箱已被绑定");
		}
		return myEmailMapper.insertSelective(myEmail) != 0;
	}

	@Override
	public boolean countByUid(@NonNull Integer uid) {
		UserInformationExample example = new UserInformationExample();
		com.pdsu.csc.bean.UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return userInformationMapper.countByExample(example) != 0;
	}
}
