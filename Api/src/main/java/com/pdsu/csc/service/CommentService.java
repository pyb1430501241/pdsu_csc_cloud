package com.pdsu.csc.service;

/**
 * 评论相关需要实现的接口, 意在对所有评论相关功能提供一些信息
 * @author 半梦
 * @create 2021-03-12 21:09
 */
public interface CommentService {

    /**
     * <p>评论是否存在<p/>
     * @param cId 评论 id
     * @return
     *  <li>如存在则返回 true</li>
     *  <li>反之 false</li>
     */
    boolean isExistByCommentId(Integer cId);

}
