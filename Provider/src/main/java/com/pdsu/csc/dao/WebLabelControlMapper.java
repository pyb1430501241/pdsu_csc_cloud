package com.pdsu.csc.dao;

import com.pdsu.csc.bean.WebLabelControl;
import java.util.List;

import com.pdsu.csc.bean.WebLabelControlExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebLabelControlMapper {
    long countByExample(WebLabelControlExample example);

    int deleteByExample(WebLabelControlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebLabelControl record);

    int insertSelective(WebLabelControl record);

    List<WebLabelControl> selectByExample(WebLabelControlExample example);

    WebLabelControl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebLabelControl record, @Param("example") WebLabelControlExample example);

    int updateByExample(@Param("record") WebLabelControl record, @Param("example") WebLabelControlExample example);

    int updateByPrimaryKeySelective(WebLabelControl record);

    int updateByPrimaryKey(WebLabelControl record);

}