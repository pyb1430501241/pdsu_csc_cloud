package com.pdsu.csc.dao;

import com.pdsu.csc.bean.WebLabel;
import java.util.List;

import com.pdsu.csc.bean.WebLabelExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebLabelMapper {
    long countByExample(WebLabelExample example);

    int deleteByExample(WebLabelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebLabel record);

    int insertSelective(WebLabel record);

    List<WebLabel> selectByExample(WebLabelExample example);

    WebLabel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebLabel record, @Param("example") WebLabelExample example);

    int updateByExample(@Param("record") WebLabel record, @Param("example") WebLabelExample example);

    int updateByPrimaryKeySelective(WebLabel record);

    int updateByPrimaryKey(WebLabel record);
}