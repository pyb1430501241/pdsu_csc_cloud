package com.pdsu.csc.service;

import java.util.List;

import com.pdsu.csc.bean.WebComment;
import com.pdsu.csc.bean.WebCommentReply;
import com.pdsu.csc.bean.WebCommentReplyExample;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.blob.comment.NotFoundCommentIdException;
import org.springframework.lang.NonNull;

/**
 * 回复评论
 * @author 半梦
 *
 */
public interface WebCommentReplyService extends TemplateService<WebCommentReply, WebCommentReplyExample> {

	/**
	 * 根据评论查询所有回复
	 * @param commentList
	 * @return
	 */
	public List<WebCommentReply> selectCommentReplysByWebComments(@NonNull List<WebComment> commentList);
	
	/**
	 * 根据网页ID查询所有评论回复
	 * @param webid
	 * @return
	 */
	public List<WebCommentReply> selectCommentReplysByWebId(@NonNull Integer webid) throws NotFoundBlobIdException;

	/**
	 * 根据用户所有文章和uid获取总共有多少评论回复
	 * @param webs
	 * @return
	 */
	public Integer countByWebsAndUid(@NonNull List<Integer> webs);

}
