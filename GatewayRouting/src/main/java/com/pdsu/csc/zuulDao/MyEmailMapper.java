package com.pdsu.csc.zuulDao;

import com.pdsu.csc.bean.MyEmail;
import com.pdsu.csc.bean.MyEmailExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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