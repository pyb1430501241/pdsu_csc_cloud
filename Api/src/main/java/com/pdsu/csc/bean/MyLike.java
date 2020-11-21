package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 关注
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyLike implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/**
	 * 自己的uid
	 */
    private Integer uid;

    /**
     * 关注人的uid
     */
    private Integer likeId;

}