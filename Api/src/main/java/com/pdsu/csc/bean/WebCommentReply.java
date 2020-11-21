package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 回复评论
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebCommentReply implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    /**
         * 文章ID
     */
    private Integer wid;

    /**
         *  评论ID
     */
    private Integer cid;

    /**
         * 评论人UID
     */
    private Integer uid;
    
    /**
         * 被评论人UID
     */
    private Integer bid;
    
    private String username;
    
    private String imgpath;

    private String content;

    private Integer thumb;

    private String createtime;

	public WebCommentReply(Integer id, Integer wid, Integer cid, Integer uid, Integer bid, String content,
			Integer thumb, String createtime) {
		this(id, wid, cid, uid, bid, null, null, content, thumb, createtime);
	}

	public WebCommentReply(Integer cid, Integer uid, Integer bid, String content, Integer thumb, String createtime) {
		this(null, null, cid, uid, bid, content, thumb, createtime);
	}
	
	public WebCommentReply(Integer wid, Integer cid, Integer uid, Integer bid, String content, Integer thumb,
			String createtime) {
		this(null, wid, cid, uid, bid, content, thumb, createtime);
	}

}