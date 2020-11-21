package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 处罚信息
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyRecord implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer uid;

    private String reasons;

    private String time;

    private String dutation;

}