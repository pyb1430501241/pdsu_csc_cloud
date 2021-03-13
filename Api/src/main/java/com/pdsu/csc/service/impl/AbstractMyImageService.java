package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.MyImage;
import com.pdsu.csc.bean.MyImageExample;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.service.MyImageService;
import com.pdsu.csc.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author 半梦
 * @create 2021-02-20 21:02
 */
public abstract class AbstractMyImageService implements MyImageService, UserService {

    @Override
    public List<MyImage> selectImagePathByUids(@NonNull List<Integer> uids) {
        return selectAll();
    }

    @Override
    public abstract MyImage selectImagePathByUid(@NonNull Integer uid) throws NotFoundUidException;

    /**
     * 默认返回 true, 防止未验情况下的异常抛出
     */
    @Override
    public boolean isExistByUid(@NonNull Integer uid) {
        return true;
    }

    @Override
    public boolean insert(@NonNull MyImage myImage) throws NotFoundUidException {
        return false;
    }

    @Override
    public boolean update(@NonNull MyImage myImage) throws NotFoundUidException {
        return updateByExample(myImage, null);
    }

    @Override
    public boolean deleteByExample(@Nullable MyImageExample example) {
        return false;
    }

    @Override
    public boolean updateByExample(@NonNull MyImage myImage, @Nullable MyImageExample example) {
        return false;
    }

    @Override
    @NonNull
    public List<MyImage> selectListByExample(@Nullable MyImageExample example) {
        return Collections.emptyList();
    }

    @Override
    public long countByExample(MyImageExample example) {
        return 0;
    }

}
