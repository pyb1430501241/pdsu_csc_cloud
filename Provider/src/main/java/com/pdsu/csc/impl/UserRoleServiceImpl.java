package com.pdsu.csc.impl;

import com.pdsu.csc.bean.Role;
import com.pdsu.csc.bean.UserRole;
import com.pdsu.csc.bean.UserRoleExample;
import com.pdsu.csc.dao.UserRoleMapper;
import com.pdsu.csc.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 半梦
 * @create 2020-08-13 16:10
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    private static final Integer ADMIN = Role.ROLE_ADMIN;
    private static final Integer TEACHER = Role.ROLE_TEACHER;

    @Override
    public boolean isAdmin(@NonNull Integer uid) {
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andRoleidEqualTo(ADMIN);
        return countByExample(example) != 0;
    }

    @Override
    public boolean isTeacher(@NonNull Integer uid) {
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andRoleidEqualTo(TEACHER);
        return countByExample(example) != 0;
    }

    @Override
    public boolean insert(@NonNull UserRole userRole) {
        return userRoleMapper.insertSelective(userRole) > 0;
    }

    @Override
    public boolean deleteByExample(@Nullable UserRoleExample example) {
        return userRoleMapper.deleteByExample(example) > 0;
    }

    @Override
    public boolean updateByExample(@NonNull UserRole userRole, @Nullable UserRoleExample example) {
        return userRoleMapper.updateByExampleSelective(userRole, example) > 0;
    }

    @Override
    @NonNull
    public List<UserRole> selectListByExample(@Nullable UserRoleExample example) {
        return userRoleMapper.selectByExample(example);
    }

    @Override
    public long countByExample(UserRoleExample example) {
        return userRoleMapper.countByExample(example);
    }

}
