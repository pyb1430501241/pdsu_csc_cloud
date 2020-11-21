package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 实名
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealName implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer uid;

    private String realName;

    private String idcard;

}