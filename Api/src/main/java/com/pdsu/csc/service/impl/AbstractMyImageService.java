package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.MyImage;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.service.MyImageService;

import java.util.List;

/**
 * @author 半梦
 * @create 2021-02-20 21:02
 */
public abstract class AbstractMyImageService implements MyImageService {

    @Override
    public List<MyImage> selectImagePathByUids(List<Integer> uids) {
        return null;
    }

    @Override
    public abstract MyImage selectImagePathByUid(Integer uid) throws NotFoundUidException;

    @Override
    public boolean countByUid(Integer uid) {
        return true;
    }

    @Override
    public boolean insert(MyImage myImage) throws NotFoundUidException {
        return false;
    }

    @Override
    public boolean update(MyImage myImage) throws NotFoundUidException {
        return false;
    }
}
