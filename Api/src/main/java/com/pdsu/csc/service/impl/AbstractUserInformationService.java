package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.bean.UserInformationExample;
import com.pdsu.csc.exception.web.DeleteInforException;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.exception.web.user.UidRepetitionException;
import com.pdsu.csc.service.UserInformationService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author 半梦
 * @create 2021-02-20 16:35
 * UserInformationService 接口的默认实现类，是为了子类继承时无需实现所有方法
 */
public abstract class AbstractUserInformationService implements UserInformationService {

    @Override
    public boolean insert(@NonNull UserInformation information) throws UidRepetitionException {
        return false;
    }

    @Override
    public boolean deleteByUid(@NonNull Integer uid) throws NotFoundUidException, DeleteInforException {
        return false;
    }

    @Override
    public abstract UserInformation selectByUid(@NonNull Integer uid);

    @Override
    public List<UserInformation> selectUsersByUid(@NonNull Integer uid, @Nullable Integer p) throws NotFoundUidException {
        return selectAll();
    }

    @Override
    public List<UserInformation> selectUsersByLikeId(@NonNull Integer likeId, @Nullable Integer p) throws NotFoundUidException {
        return selectAll();
    }

    @Override
    public List<UserInformation> selectUsersByUids(@NonNull List<Integer> uids) {
        return selectAll();
    }

    @Override
    public abstract int countByUid(@NonNull Integer uid);

    @Override
    public boolean modifyThePassword(@NonNull Integer uid, @NonNull String password) {
        return false;
    }

    @Override
    public abstract int countByUserName(@NonNull String username);

    @Override
    public boolean updateUserInformation(@NonNull Integer uid, @NonNull UserInformation user) throws NotFoundUidException {
        return false;
    }

    @Override
    public List<UserInformation> selectUserInformations() {
        return selectAll();
    }

    @Override
    public boolean deleteByExample(@Nullable UserInformationExample example) {
        return false;
    }

    @Override
    public boolean updateByExample(@NonNull UserInformation userInformation, @Nullable UserInformationExample example) {
        return false;
    }

    @Override
    @NonNull
    public List<UserInformation> selectListByExample(@Nullable UserInformationExample example) {
        return Collections.emptyList();
    }

    @Override
    public long countByExample(UserInformationExample example) {
        return 0;
    }

    @Override
    public boolean isExistByUid(@NonNull Integer uid) {
        return countByUid(uid) > 0;
    }

}
