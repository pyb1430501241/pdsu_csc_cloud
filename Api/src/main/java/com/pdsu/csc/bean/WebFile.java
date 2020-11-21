package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 文件相关
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebFile implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer uid;

    private String title;

    private String description;

    private String filePath;

    private String creattime;

	public WebFile(Integer uid, String title, String description, String filePath, String creattime) {
		this(null, uid, title, description, filePath, creattime);
	}
    

}