package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 点赞相关
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebThumbs implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer uid;

    private Integer bid;

    private Integer webid;

	public WebThumbs(Integer uid, Integer bid, Integer webid) {
		this(null, uid, bid, webid);
	}

}