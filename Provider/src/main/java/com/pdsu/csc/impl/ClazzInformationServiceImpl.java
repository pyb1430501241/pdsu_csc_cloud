package com.pdsu.csc.impl;

import com.pdsu.csc.bean.ClazzInformation;
import com.pdsu.csc.dao.ClazzInformationMapper;
import com.pdsu.csc.service.ClazzInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * @author 半梦
 * @create 2020-08-21 14:47
 */
@Deprecated
@Service("clazzInformationService")
public class ClazzInformationServiceImpl implements ClazzInformationService {

    @Autowired
    private ClazzInformationMapper clazzInformationMapper;

    @Override
    public boolean insert(@NonNull ClazzInformation clazzInformation) {
        int i = clazzInformationMapper.insertSelective(clazzInformation);
        return i > 0;
    }
}
