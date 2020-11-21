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
public class FileInformation implements Serializable {

	private UserInformation user;
	
	private WebFile webfile;
	
	private Integer downloads;

}
