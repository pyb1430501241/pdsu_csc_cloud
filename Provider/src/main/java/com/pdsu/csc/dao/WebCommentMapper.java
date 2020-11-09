package com.pdsu.csc.dao;

import com.pdsu.csc.bean.WebComment;
import java.util.List;

import com.pdsu.csc.bean.WebCommentExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebCommentMapper {
    long countByExample(WebCommentExample example);

    int deleteByExample(WebCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebComment record);

    int insertSelective(WebComment record);

    List<WebComment> selectByExample(WebCommentExample example);

    WebComment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebComment record, @Param("example") WebCommentExample example);

    int updateByExample(@Param("record") WebComment record, @Param("example") WebCommentExample example);

    int updateByPrimaryKeySelective(WebComment record);

    int updateByPrimaryKey(WebComment record);
}