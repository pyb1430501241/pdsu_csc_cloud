package com.pdsu.csc.dao;

import com.pdsu.csc.bean.MyCollection;
import java.util.List;

import com.pdsu.csc.bean.MyCollectionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyCollectionMapper {
    long countByExample(MyCollectionExample example);

    int deleteByExample(MyCollectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MyCollection record);

    int insertSelective(MyCollection record);

    List<MyCollection> selectByExample(MyCollectionExample example);

    MyCollection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MyCollection record, @Param("example") MyCollectionExample example);

    int updateByExample(@Param("record") MyCollection record, @Param("example") MyCollectionExample example);

    int updateByPrimaryKeySelective(MyCollection record);

    int updateByPrimaryKey(MyCollection record);
}