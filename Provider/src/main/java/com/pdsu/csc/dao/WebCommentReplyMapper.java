package com.pdsu.csc.dao;

import com.pdsu.csc.bean.WebCommentReply;
import java.util.List;

import com.pdsu.csc.bean.WebCommentReplyExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebCommentReplyMapper {
    long countByExample(WebCommentReplyExample example);

    int deleteByExample(WebCommentReplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebCommentReply record);

    int insertSelective(WebCommentReply record);

    List<WebCommentReply> selectByExample(WebCommentReplyExample example);

    WebCommentReply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebCommentReply record, @Param("example") WebCommentReplyExample example);

    int updateByExample(@Param("record") WebCommentReply record, @Param("example") WebCommentReplyExample example);

    int updateByPrimaryKeySelective(WebCommentReply record);

    int updateByPrimaryKey(WebCommentReply record);
}