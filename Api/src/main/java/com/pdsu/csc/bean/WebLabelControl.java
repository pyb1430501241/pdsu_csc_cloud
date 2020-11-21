package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 文章标签对照
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebLabelControl implements Serializable {
    private Integer id;

    private Integer wid;

    private Integer lid;

	public WebLabelControl(Integer wid, Integer lid) {
		super();
		this.wid = wid;
		this.lid = lid;
	}
    
}