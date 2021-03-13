package com.pdsu.csc.impl;

import com.github.pagehelper.PageHelper;
import com.pdsu.csc.bean.UserBrowsingRecord;
import com.pdsu.csc.bean.UserBrowsingRecordExample;
import com.pdsu.csc.dao.UserBrowsingRecordMapper;
import com.pdsu.csc.service.UserBrowsingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 半梦
 * @create 2020-08-12 22:43
 */
@Service("userBrowsingRecordService")
public class UserBrowsingRecordServiceImpl implements UserBrowsingRecordService {

    @Autowired
   private UserBrowsingRecordMapper userBrowsingRecordMapper;

    @Override
    public boolean insert(@NonNull UserBrowsingRecord userBrowsingRecord) {
        return userBrowsingRecordMapper.insertSelective(userBrowsingRecord) == 0;
    }

    @Override
    @NonNull
    public List<UserBrowsingRecord> selectBrowsingRecordByUid(@NonNull Integer uid, @Nullable Integer p) {
        UserBrowsingRecordExample example = new UserBrowsingRecordExample();
        UserBrowsingRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        example.setOrderByClause(splicing("createtime", ORDER_INCREMENTAL));
        if(p != null) {
            PageHelper.startPage(p,15);
        }
        return selectListByExample(example);
    }

    @Override
    public boolean deleteBrowsingRecordByUid(@NonNull Integer uid) {
        UserBrowsingRecordExample example = new UserBrowsingRecordExample();
        UserBrowsingRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        return userBrowsingRecordMapper.deleteByExample(example) == countByExample(example);
    }

    @Override
    public boolean deleteByExample(@Nullable UserBrowsingRecordExample example) {
        return userBrowsingRecordMapper.deleteByExample(example) > 0;
    }

    @Override
    public boolean updateByExample(@NonNull UserBrowsingRecord userBrowsingRecord,
            @Nullable UserBrowsingRecordExample example) {
        return userBrowsingRecordMapper.updateByExampleSelective(userBrowsingRecord, example) > 0;
    }

    @Override
    @NonNull
    public List<UserBrowsingRecord> selectListByExample(@Nullable UserBrowsingRecordExample example) {
        return userBrowsingRecordMapper.selectByExample(example);
    }

    @Override
    public long countByExample(UserBrowsingRecordExample example) {
        return userBrowsingRecordMapper.countByExample(example);
    }

}
