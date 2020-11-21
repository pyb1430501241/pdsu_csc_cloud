package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 头像
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyImage implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer uid;

    private String imagePath;

    public MyImage(Integer uid, String imagePath){
    	this(null,uid,imagePath);
    }
}