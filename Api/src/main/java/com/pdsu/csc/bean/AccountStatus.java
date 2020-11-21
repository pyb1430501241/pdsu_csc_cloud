package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 账号状态
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatus implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String status;

}