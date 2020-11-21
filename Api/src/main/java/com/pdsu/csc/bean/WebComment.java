package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 评论相关
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebComment implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
    
    /**
     * 网页id
     */
    private Integer wid;

    /**
     * 评论人uid
     */
    private Integer uid;
    
    private String username;
    
    private String imgpath;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer thumb;

    /**
     * 评论时间
     */
    private String createtime;

    /**
     * 状态
     */
    private Integer state;
    
    private List<WebCommentReply> commentReplyList;
    
    public WebComment(Integer id, Integer wid, Integer uid, String content, Integer thumb, String createtime,
			Integer state, List<WebCommentReply> commentReplyList) {
		this(id, wid, uid, null, null, content, thumb, createtime, state, commentReplyList);
	}

	public WebComment(Integer id, Integer wid, Integer uid, String content, Integer thumb, String createtime,
			Integer state) {
		this(id, wid, uid, content, thumb, createtime, state, null);
	}


}