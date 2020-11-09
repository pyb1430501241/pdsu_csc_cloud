package com.pdsu.csc.dao;

import com.pdsu.csc.bean.WebFile;
import java.util.List;

import com.pdsu.csc.bean.WebFileExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebFileMapper {
    long countByExample(WebFileExample example);

    int deleteByExample(WebFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebFile record);

    int insertSelective(WebFile record);

    List<WebFile> selectByExample(WebFileExample example);

    WebFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebFile record, @Param("example") WebFileExample example);

    int updateByExample(@Param("record") WebFile record, @Param("example") WebFileExample example);

    int updateByPrimaryKeySelective(WebFile record);

    int updateByPrimaryKey(WebFile record);
}