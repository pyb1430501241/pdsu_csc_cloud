package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * es 存储 File 信息
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EsFileInformation implements Serializable {

	private Integer fileid;
	
	private String title;
	
	private String description;

	public EsFileInformation(String description, String title, Integer fileid) {
		super();
		this.fileid = fileid;
		this.title = title;
		this.description = description;
	}

}
