package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 搜索引擎所保存的数据
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EsUserInformation implements Serializable {
	
	private Integer uid;
	
	private String username;
	
	private String imgpath;
	
	private Integer blobnum;
	
	private Integer likenum;

	public EsUserInformation(Integer uid, Integer blobnum, String imgpath, Integer likenum, String username) {
		super();
		this.uid = uid;
		this.username = username;
		this.imgpath = imgpath;
		this.blobnum = blobnum;
		this.likenum = likenum;
	}
}
