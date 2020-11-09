package com.pdsu.csc.dao;

import com.pdsu.csc.bean.SystemNotification;
import java.util.List;

import com.pdsu.csc.bean.SystemNotificationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemNotificationMapper {
    long countByExample(SystemNotificationExample example);

    int deleteByExample(SystemNotificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemNotification record);

    int insertSelective(SystemNotification record);

    List<SystemNotification> selectByExample(SystemNotificationExample example);

    SystemNotification selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemNotification record, @Param("example") SystemNotificationExample example);

    int updateByExample(@Param("record") SystemNotification record, @Param("example") SystemNotificationExample example);

    int updateByPrimaryKeySelective(SystemNotification record);

    int updateByPrimaryKey(SystemNotification record);

    int insertByList(@Param("list") List<SystemNotification> list);
}