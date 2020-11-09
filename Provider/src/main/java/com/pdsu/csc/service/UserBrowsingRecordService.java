package com.pdsu.csc.service;

import com.pdsu.csc.bean.UserBrowsingRecord;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author 半梦
 * @create 2020-08-12 22:41
 */
public interface UserBrowsingRecordService {

    /**
     * 添加浏览记录
     * @param userBrowsingRecord
     * @return
     */
    public boolean insert(@NonNull UserBrowsingRecord userBrowsingRecord);

    /**
     * 获取用户浏览记录
     * @param uid
     * @return
     */
    @NonNull
    public List<UserBrowsingRecord> selectBrowsingRecordByUid(@NonNull Integer uid);

    /**
     * 清空用户记录
     * @param uid
     * @return
     */
    public boolean deleteBrowsingRecordByUid(@NonNull Integer uid);
}
