package com.pdsu.csc.dao;

import com.pdsu.csc.bean.UserBrowsingRecord;
import java.util.List;

import com.pdsu.csc.bean.UserBrowsingRecordExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBrowsingRecordMapper {
    long countByExample(UserBrowsingRecordExample example);

    int deleteByExample(UserBrowsingRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserBrowsingRecord record);

    int insertSelective(UserBrowsingRecord record);

    List<UserBrowsingRecord> selectByExample(UserBrowsingRecordExample example);

    UserBrowsingRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserBrowsingRecord record, @Param("example") UserBrowsingRecordExample example);

    int updateByExample(@Param("record") UserBrowsingRecord record, @Param("example") UserBrowsingRecordExample example);

    int updateByPrimaryKeySelective(UserBrowsingRecord record);

    int updateByPrimaryKey(UserBrowsingRecord record);
}