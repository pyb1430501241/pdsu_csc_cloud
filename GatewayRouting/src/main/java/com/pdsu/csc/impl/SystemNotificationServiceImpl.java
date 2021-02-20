package com.pdsu.csc.impl;

import com.pdsu.csc.bean.SystemNotification;
import com.pdsu.csc.bean.SystemNotificationExample;
import com.pdsu.csc.handler.AbstractHandler;
import com.pdsu.csc.zuulDao.SystemNotificationMapper;
import com.pdsu.csc.service.impl.AbstractSystemNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
    public List<SystemNotification> selectSystemNotificationsByUid(@NonNull Integer uid, Integer p) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        example.setOrderByClause("createtime DESC");
        return systemNotificationMapper.selectByExample(example);
    }

    @Override
    public Integer countSystemNotificationByUidAndUnRead(@NonNull Integer uid) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andUnreadEqualTo(AbstractHandler.SYSTEM_NOTIFICATION_UNREAD);
        return (int) systemNotificationMapper.countByExample(example);
    }

}
