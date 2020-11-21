package com.pdsu.csc.bean;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;

/**
 * 博客信息
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebInformation implements Serializable, Cloneable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotBlank(message = "文章标题必须为 30 个字符以内")
    @Size(min = 1, max = 30, message = "文章标题必须为 30 个字符以内")
    private String title;

    private Integer uid;

    @NotNull(message = "文章类型不可为空")
    private Integer contype;

    private String subTime;

    private byte[] webData;

    @NotBlank(message = "文章主题内容不可为空")
    private String webDataString;
    
	public WebInformation(Integer id, String title, Integer uid, Integer contype, String subTime, byte[] webData) {
		this(id, title, uid, contype, subTime, webData, null);
	}

    public WebInformation getWebInformation(WebInformation web) {
        try {
            return (WebInformation) clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}