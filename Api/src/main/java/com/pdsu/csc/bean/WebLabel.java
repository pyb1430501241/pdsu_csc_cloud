package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 文章标签
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebLabel implements Serializable {
    private Integer id;

    private String label;

	public WebLabel(String label) {
		super();
		this.label = label;
	}

}