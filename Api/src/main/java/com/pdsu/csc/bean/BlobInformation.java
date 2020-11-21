package com.pdsu.csc.bean;

import lombok.*;

/**
 * 博客信息
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlobInformation {
	
	private UserInformation user;
	
	private WebInformation web;
	
	private Integer visit;
	
	private Integer thumbs;
	
	private Integer collection;


	public BlobInformation(WebInformation web, Integer visit, Integer thumbs, Integer collection) {
		super();
		this.web = web;
		this.visit = visit;
		this.thumbs = thumbs;
		this.collection = collection;
	}

}
