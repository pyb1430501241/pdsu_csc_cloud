package com.pdsu.csc.dao;


import com.pdsu.csc.bean.WebThumbs;
import com.pdsu.csc.bean.WebThumbsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebThumbsMapper {

    long countByExample(WebThumbsExample example);
	
    int deleteByExample(WebThumbsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebThumbs record);

    int insertSelective(WebThumbs record);

    List<WebThumbs> selectByExample(WebThumbsExample example);

    WebThumbs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebThumbs record, @Param("example") WebThumbsExample example);

    int updateByExample(@Param("record") WebThumbs record, @Param("example") WebThumbsExample example);

    int updateByPrimaryKeySelective(WebThumbs record);

    int updateByPrimaryKey(WebThumbs record);

}