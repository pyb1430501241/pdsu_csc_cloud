package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 账号状态
 * <li>NORMAL, 账号状态正常
 * <li>FROZEN, 账号状态冻结
 * <li>BAN, 账号状态封禁
 * <li>CANCELLED, 账号状态注销
 * @author Admin
 *
 */
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AccountStatus implements Serializable{
	/**
	 * 账号正常
	 */
	NORMAL(1, "正常"),
	/**
	 * 账号被冻结
	 */
	FROZEN(2, "冻结"),
	/**
	 * 账号被封禁
	 */
	BAN(3, "封禁"),
	/**
	 * 账号注销
	 */
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