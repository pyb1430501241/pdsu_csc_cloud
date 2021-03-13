package com.pdsu.csc.service;

import com.pdsu.csc.bean.WebComment;
import com.pdsu.csc.bean.WebCommentExample;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 评论
 * @author 半梦
 *
 */
public interface WebCommentService extends
		TemplateService<WebComment, WebCommentExample>, CommentService{

	/**
	 * 根据 webid 获取文章评论
	 * @param webid
	 * @return
	 */
	public List<WebComment> selectCommentsByWebId(@NonNull Integer webid) throws NotFoundBlobIdException;

	/**
	 * 根据学号获取一个人的总评论数
	 * @param uid
	 * @return
	 */
	public Integer countByUid(@NonNull Integer uid);

	/**
	 *  获取评论
	 * @param cid
	 * @return
	 */
    public WebComment selectCommentById(Integer cid);
}
