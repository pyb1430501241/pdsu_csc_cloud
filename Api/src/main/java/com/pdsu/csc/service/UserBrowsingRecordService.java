package com.pdsu.csc.service;

import com.pdsu.csc.bean.UserBrowsingRecord;
import com.pdsu.csc.bean.UserBrowsingRecordExample;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author 半梦
 * @create 2020-08-12 22:41
 */
public interface UserBrowsingRecordService extends
        TemplateService<UserBrowsingRecord, UserBrowsingRecordExample> {

    /**
     * 获取用户浏览记录
     * @param uid
     * @return
     */
    @NonNull
    public List<UserBrowsingRecord> selectBrowsingRecordByUid(@NonNull Integer uid, @Nullable Integer p);

    /**
     * 清空用户记录
     * @param uid
     * @return
     */
    public boolean deleteBrowsingRecordByUid(@NonNull Integer uid);
}
