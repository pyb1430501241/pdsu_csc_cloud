package com.pdsu.csc.bean;

import lombok.*;

/**
 * 作者信息
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
	
	/**
	 * 学号
	 */
	private Integer uid;
	
	/**
	 * 昵称
	 */
	private String username;
	
	/**
	 * 头像地址
	 */
	private String imgpath;
	
	/**
	 * 原创数
	 */
	private Integer original;
	
	/**
	 * 粉丝数
	 */
	private Integer fans;
	
	/**
	 * 点赞数
	 */
	private Integer thumbs;
	
	/**
	 * 评论数
	 */
	private Integer comment;
	
	/**
	 * 访问量
	 */
	private Integer visits;
	
	/**
	 * 收藏量
	 */
	private Integer collection;
	
	/**
	 * 关注数
	 */
	private Integer attention;
	
	/**
	 * 上传文件数
	 */
	private Integer files;
	
	/**
	 * 文件下载量
	 */
	private Integer downloads;
	
	public Author(Integer uid, String username, String imgpath, Integer original, Integer fans, Integer thumbs,
			Integer comment, Integer visits, Integer collection, Integer attention, Integer files) {
		super();
		this.uid = uid;
		this.username = username;
		this.imgpath = imgpath;
		this.original = original;
		this.fans = fans;
		this.thumbs = thumbs;
		this.comment = comment;
		this.visits = visits;
		this.collection = collection;
		this.attention = attention;
		this.files = files;
	}

	public Author(Integer uid, String username, String imgpath, Integer original, Integer fans, Integer thumbs,
			Integer comment, Integer visits, Integer collection) {
		super();
		this.uid = uid;
		this.username = username;
		this.imgpath = imgpath;
		this.original = original;
		this.fans = fans;
		this.thumbs = thumbs;
		this.comment = comment;
		this.visits = visits;
		this.collection = collection;
	}

	public Author(Integer uid, String username, Integer original, Integer fans, Integer thumbs, Integer comment,
			Integer visits, Integer collection) {
		super();
		this.uid = uid;
		this.username = username;
		this.original = original;
		this.fans = fans;
		this.thumbs = thumbs;
		this.comment = comment;
		this.visits = visits;
		this.collection = collection;
	}
	
	public Author(Integer uid, String username, String imgpath, Integer original, Integer fans, Integer thumbs,
			Integer comment, Integer visits, Integer collection, Integer attention) {
		super();
		this.uid = uid;
		this.username = username;
		this.imgpath = imgpath;
		this.original = original;
		this.fans = fans;
		this.thumbs = thumbs;
		this.comment = comment;
		this.visits = visits;
		this.collection = collection;
		this.attention = attention;
	}


}
