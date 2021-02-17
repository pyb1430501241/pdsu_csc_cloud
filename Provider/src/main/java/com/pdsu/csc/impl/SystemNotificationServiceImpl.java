package com.pdsu.csc.impl;

import com.github.pagehelper.PageHelper;
import com.pdsu.csc.bean.SystemNotification;
import com.pdsu.csc.bean.SystemNotificationExample;
import com.pdsu.csc.dao.SystemNotificationMapper;
import com.pdsu.csc.handler.ParentHandler;
import com.pdsu.csc.service.SystemNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 半梦
 * @create 2020-08-13 14:12
 */
@Service("systemNotificationService")
public class SystemNotificationServiceImpl implements SystemNotificationService {

    @Autowired
    private SystemNotificationMapper systemNotificationMapper;

    @Override
    public boolean insert(@NonNull List<SystemNotification> list) {
        int i = systemNotificationMapper.insertByList(list);
        return i > 0;
    }

    @Override
    public List<SystemNotification> selectSystemNotificationsByUid(@NonNull Integer uid, Integer p) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        example.setOrderByClause("createtime DESC");
        if(p != null) {
            PageHelper.startPage(p, 10);
        }
        return systemNotificationMapper.selectByExample(example);
    }

    @Override
    public boolean deleteSystemNotificationsByUid(@NonNull Integer uid) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        return systemNotificationMapper.countByExample(example) == systemNotificationMapper.deleteByExample(example);
    }

    @Override
    public boolean updateSystemNotificationsByUid(@NonNull Integer uid) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        SystemNotification systemNotification = new SystemNotification();
        systemNotification.setUnread(ParentHandler.SYSTEM_NOTIFICATION_READ);
        criteria.andUidEqualTo(uid);
        return systemNotificationMapper.countByExample(example)
                       == systemNotificationMapper.updateByExampleSelective(systemNotification, example);
    }

    @Override
    public Integer countSystemNotificationByUidAndUnRead(@NonNull Integer uid) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andUnreadEqualTo(ParentHandler.SYSTEM_NOTIFICATION_UNREAD);
        return (int) systemNotificationMapper.countByExample(example);
    }
}
