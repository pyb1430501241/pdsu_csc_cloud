package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * es 存储的 blob 信息
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EsBlobInformation implements Serializable {
	
	private Integer webid;
	
	private String title;
	
	private String description;

}
