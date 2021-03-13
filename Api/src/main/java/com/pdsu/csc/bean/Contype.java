package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 文章类型
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class Contype implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String contype;

}