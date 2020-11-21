package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * @author 半梦
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FansInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserInformation user;
	
	private boolean islike;

}
