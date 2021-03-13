package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.SystemNotification;
import com.pdsu.csc.bean.SystemNotificationExample;
import com.pdsu.csc.exception.CodeSharingCommunityException;
import com.pdsu.csc.service.SystemNotificationService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 半梦
 * @create 2021-02-20 21:04
 */
public abstract class AbstractSystemNotificationService implements SystemNotificationService {

    @Override
    public boolean insert(@NonNull List<SystemNotification> list) {
        return false;
    }

    @Override
    public abstract List<SystemNotification> selectSystemNotificationsByUid(@NonNull Integer uid, @Nullable Integer p);

    @Override
    public boolean deleteSystemNotificationsByUid(@NonNull Integer uid) {
        return false;
    }

    @Override
    public boolean updateSystemNotificationsByUid(@NonNull Integer uid) {
        return false;
    }

    @Override
    public abstract Integer countSystemNotificationByUidAndUnRead(@NonNull Integer uid);


    @Override
    public boolean insert(@NonNull SystemNotification systemNotification) throws CodeSharingCommunityException {
        return insert(Collections.singletonList(systemNotification));
    }

    @Override
    public boolean deleteByExample(@Nullable SystemNotificationExample example) {
        return false;
    }

    @Override
    public boolean updateByExample(@NonNull SystemNotification systemNotification,
                                   @Nullable SystemNotificationExample example) {
        return false;
    }

    @Override
    @NonNull
    public List<SystemNotification> selectListByExample(@Nullable SystemNotificationExample example) {
        return Collections.emptyList();
    }

    @Override
    public long countByExample(@Nullable SystemNotificationExample example) {
        return 0;
    }

}
