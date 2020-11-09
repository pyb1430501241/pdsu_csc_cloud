package com.pdsu.csc.dao;

import com.pdsu.csc.bean.UserClazzInformation;
import java.util.List;

import com.pdsu.csc.bean.UserClazzInformationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClazzInformationMapper {
    long countByExample(UserClazzInformationExample example);

    int deleteByExample(UserClazzInformationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserClazzInformation record);

    int insertSelective(UserClazzInformation record);

    List<UserClazzInformation> selectByExample(UserClazzInformationExample example);

    UserClazzInformation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserClazzInformation record, @Param("example") UserClazzInformationExample example);

    int updateByExample(@Param("record") UserClazzInformation record, @Param("example") UserClazzInformationExample example);

    int updateByPrimaryKeySelective(UserClazzInformation record);

    int updateByPrimaryKey(UserClazzInformation record);

    int insertByList(@Param("uids") List<Integer> uids, @Param("clazzId") Integer clazzId);
}