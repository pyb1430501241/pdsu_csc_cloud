package com.pdsu.csc.dao;

import com.pdsu.csc.bean.ClazzInformation;
import java.util.List;

import com.pdsu.csc.bean.ClazzInformationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClazzInformationMapper {
    long countByExample(ClazzInformationExample example);

    int deleteByExample(ClazzInformationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClazzInformation record);

    int insertSelective(ClazzInformation record);

    List<ClazzInformation> selectByExample(ClazzInformationExample example);

    ClazzInformation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClazzInformation record, @Param("example") ClazzInformationExample example);

    int updateByExample(@Param("record") ClazzInformation record, @Param("example") ClazzInformationExample example);

    int updateByPrimaryKeySelective(ClazzInformation record);

    int updateByPrimaryKey(ClazzInformation record);
}