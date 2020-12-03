package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 账号状态
 * @author Admin
 *
 */
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AccountStatus implements Serializable{
	NORMAL(1, "正常"),
	FROZEN(2, "冻结"),
	BAN(3, "封禁"),
	CANCELLED(4, "注销"),
	OTHER(999, "other");

	private Integer id;

    private String status;

	public static AccountStatus getByKey(Integer id){
		for (AccountStatus constants : values()) {
			if (constants.getId().equals(id)) {
				return constants;
			}
		}
		return OTHER;
	}

}