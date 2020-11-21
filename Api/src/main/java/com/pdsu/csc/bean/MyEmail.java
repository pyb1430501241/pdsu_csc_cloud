package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 邮箱
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyEmail implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer uid;

    private String email;

}