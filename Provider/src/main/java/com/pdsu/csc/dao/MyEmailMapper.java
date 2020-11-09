package com.pdsu.csc.dao;

import com.pdsu.csc.bean.MyEmail;
import java.util.List;

import com.pdsu.csc.bean.MyEmailExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEmailMapper {
    long countByExample(MyEmailExample example);

    int deleteByExample(MyEmailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MyEmail record);

    int insertSelective(MyEmail record);

    List<MyEmail> selectByExample(MyEmailExample example);

    MyEmail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MyEmail record, @Param("example") MyEmailExample example);

    int updateByExample(@Param("record") MyEmail record, @Param("example") MyEmailExample example);

    int updateByPrimaryKeySelective(MyEmail record);

    int updateByPrimaryKey(MyEmail record);
}