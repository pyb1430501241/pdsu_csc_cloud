package com.pdsu.csc.service;

import com.pdsu.csc.bean.SystemNotification;
import com.pdsu.csc.bean.SystemNotificationExample;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author 半梦
 * @create 2020-08-13 14:09
 */
public interface SystemNotificationService extends
        TemplateService<SystemNotification, SystemNotificationExample>{

    /**
     * 插入系统通知
     * @param list
     * @return
     */
    public boolean insert(@NonNull List<SystemNotification> list);


    /**
     * 获取用户通知
     * @param uid
     * @return
     */
    public List<SystemNotification> selectSystemNotificationsByUid(@NonNull Integer uid, @Nullable Integer p);

    /**
     * 删除用户通知
     * @param uid
     * @return
     */
    public boolean deleteSystemNotificationsByUid(@NonNull Integer uid);

    /**
     * 更新通知
     * @param uid
     * @return
     */
    public boolean updateSystemNotificationsByUid(@NonNull Integer uid);

    /**
     * 获取未读信息数量
     * @param uid
     * @return
     */
    public Integer countSystemNotificationByUidAndUnRead(@NonNull Integer uid);

}
