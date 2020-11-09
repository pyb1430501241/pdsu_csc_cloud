package com.pdsu.csc.service.impl;

import com.pdsu.csc.dao.UserClazzInformationMapper;
import com.pdsu.csc.service.UserClazzInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 半梦
 * @create 2020-08-21 18:30
 */
@Service("userClazzInformationService")
@Deprecated
public class UserClazzInformationServiceImpl implements UserClazzInformationService {

    @Autowired
    private UserClazzInformationMapper userClazzInformationMapper;

    @Override
    public boolean insertByList(@NonNull List<Integer> uids, @NonNull Integer clazzId) {
        return userClazzInformationMapper.insertByList(uids, clazzId) > 0;
    }
}
