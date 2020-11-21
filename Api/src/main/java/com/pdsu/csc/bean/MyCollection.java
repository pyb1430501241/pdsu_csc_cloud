package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 收藏
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyCollection implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer uid;

    private Integer wid;

    private Integer bid;

}