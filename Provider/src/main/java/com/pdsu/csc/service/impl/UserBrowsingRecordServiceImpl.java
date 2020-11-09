package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.UserBrowsingRecord;
import com.pdsu.csc.bean.UserBrowsingRecordExample;
import com.pdsu.csc.dao.UserBrowsingRecordMapper;
import com.pdsu.csc.service.UserBrowsingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 半梦
 * @create 2020-08-12 22:43
 */
@Service("uerBrowsingRecordService")
public class UserBrowsingRecordServiceImpl implements UserBrowsingRecordService {

    @Autowired
   private UserBrowsingRecordMapper userBrowsingRecordMapper;

    @Override
    public boolean insert(@NonNull UserBrowsingRecord userBrowsingRecord) {
        return userBrowsingRecordMapper.insertSelective(userBrowsingRecord) == 0;
    }

    @Override
    public List<UserBrowsingRecord> selectBrowsingRecordByUid(@NonNull Integer uid) {
        UserBrowsingRecordExample example = new UserBrowsingRecordExample();
        UserBrowsingRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        example.setOrderByClause("createtime DESC");
        return userBrowsingRecordMapper.selectByExample(example);
    }

    @Override
    public boolean deleteBrowsingRecordByUid(@NonNull Integer uid) {
        UserBrowsingRecordExample example = new UserBrowsingRecordExample();
        UserBrowsingRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        long count = userBrowsingRecordMapper.countByExample(example);
        return userBrowsingRecordMapper.deleteByExample(example) == count;
    }
}
