package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 访问相关
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitInformation implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer vid;

    private Integer sid;

    private Integer wid;

}