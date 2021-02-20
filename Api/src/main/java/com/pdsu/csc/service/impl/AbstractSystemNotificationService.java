package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.SystemNotification;
import com.pdsu.csc.service.SystemNotificationService;

import java.util.List;

/**
 * @author 半梦
 * @create 2021-02-20 21:04
 */
public abstract class AbstractSystemNotificationService implements SystemNotificationService {

    @Override
    public boolean insert(List<SystemNotification> list) {
        return false;
    }

    @Override
    public abstract List<SystemNotification> selectSystemNotificationsByUid(Integer uid, Integer p);

    @Override
    public boolean deleteSystemNotificationsByUid(Integer uid) {
        return false;
    }

    @Override
    public boolean updateSystemNotificationsByUid(Integer uid) {
        return false;
    }

    @Override
    public abstract Integer countSystemNotificationByUidAndUnRead(Integer uid);

}
