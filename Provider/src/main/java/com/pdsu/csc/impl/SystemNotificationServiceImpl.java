package com.pdsu.csc.impl;

import com.github.pagehelper.PageHelper;
import com.pdsu.csc.bean.SystemNotification;
import com.pdsu.csc.bean.SystemNotificationExample;
import com.pdsu.csc.dao.SystemNotificationMapper;
import com.pdsu.csc.handler.InitHandler;
import com.pdsu.csc.service.impl.AbstractSystemNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 半梦
 * @create 2020-08-13 14:12
 */
@Service("systemNotificationService")
public class SystemNotificationServiceImpl extends AbstractSystemNotificationService {

    @Autowired
    private SystemNotificationMapper systemNotificationMapper;

    @Override
    public boolean insert(@NonNull List<SystemNotification> list) {
        int i = systemNotificationMapper.insertByList(list);
        return i > 0;
    }

    @Override
    public List<SystemNotification> selectSystemNotificationsByUid(@NonNull Integer uid, @Nullable Integer p) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        example.setOrderByClause("createtime DESC");
        if(p != null) {
            PageHelper.startPage(p, 10);
        }
        return selectListByExample(example);
    }

    @Override
    public boolean deleteSystemNotificationsByUid(@NonNull Integer uid) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        return countByExample(example) > 0 == deleteByExample(example);
    }

    @Override
    public boolean updateSystemNotificationsByUid(@NonNull Integer uid) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        SystemNotification systemNotification = new SystemNotification();
        systemNotification.setUnread(InitHandler.SYSTEM_NOTIFICATION_READ);
        criteria.andUidEqualTo(uid);
        return countByExample(example) > 0  == updateByExample(systemNotification, example);
    }

    @Override
    public Integer countSystemNotificationByUidAndUnRead(@NonNull Integer uid) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andUnreadEqualTo(InitHandler.SYSTEM_NOTIFICATION_UNREAD);
        return (int)countByExample(example);
    }

    @Override
    public boolean deleteByExample(@Nullable SystemNotificationExample example) {
        return systemNotificationMapper.deleteByExample(example) > 0;
    }

    @Override
    public boolean updateByExample(@NonNull SystemNotification systemNotification
            , @Nullable SystemNotificationExample example) {
        return systemNotificationMapper.updateByExample(systemNotification, example) > 0;
    }

    @Override
    @NonNull
    public List<SystemNotification> selectListByExample(@Nullable SystemNotificationExample example) {
        return systemNotificationMapper.selectByExample(example);
    }

    @Override
    public long countByExample(@Nullable SystemNotificationExample example) {
        return systemNotificationMapper.countByExample(example);
    }

}
