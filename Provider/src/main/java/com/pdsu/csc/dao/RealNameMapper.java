package com.pdsu.csc.dao;

import com.pdsu.csc.bean.RealName;
import java.util.List;

import com.pdsu.csc.bean.RealNameExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RealNameMapper {
    long countByExample(RealNameExample example);

    int deleteByExample(RealNameExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RealName record);

    int insertSelective(RealName record);

    List<RealName> selectByExample(RealNameExample example);

    RealName selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RealName record, @Param("example") RealNameExample example);

    int updateByExample(@Param("record") RealName record, @Param("example") RealNameExample example);

    int updateByPrimaryKeySelective(RealName record);

    int updateByPrimaryKey(RealName record);
}