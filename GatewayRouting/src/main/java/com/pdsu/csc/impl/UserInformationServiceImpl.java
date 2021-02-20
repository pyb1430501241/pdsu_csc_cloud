package com.pdsu.csc.impl;

import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.bean.UserInformationExample;
import com.pdsu.csc.zuulDao.UserInformationMapper;
import com.pdsu.csc.service.impl.AbstractUserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * @author 半梦
 * @create 2021-02-20 20:30
 */
@Service("userInformationService")
public class UserInformationServiceImpl extends AbstractUserInformationService {

    @Autowired
    private UserInformationMapper userInformationMapper;

    /**
     * 根据用户的uid查询用户信息
     */
    @Override
    public UserInformation selectByUid(@NonNull Integer uid) {
        if(countByUid(uid) <= 0) {
            return null;
        }
        UserInformationExample example = new UserInformationExample();
        UserInformationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        return userInformationMapper.selectByExample(example).get(0);
    }

    /**
     * 查询是否有此账号
     */
    @Override
    public int countByUid(@NonNull Integer uid) {
        UserInformationExample example = new UserInformationExample();
        UserInformationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        return (int) userInformationMapper.countByExample(example);
    }

    @Override
    public int countByUserName(@NonNull String username) {
        UserInformationExample example = new UserInformationExample();
        UserInformationExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        return (int) userInformationMapper.countByExample(example);
    }

}
