package com.pdsu.csc.bean;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户信息
 * @author Admin
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class UserInformation implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull(message = "学号必须为纯数字")
    private Integer uid;

	@NotBlank(message = "密码必须为6~20个字母或数字特殊字符的组合")
	@Size(min = 6, max = 20, message = "密码必须为6~20个字母或数字特殊字符的组合")
    private String password;

	@NotBlank(message = "用户名为4~16英文字符, 数字, 或2~8个汉字")
	@Size(min = 2, max = 16, message = "用户名为4~16英文字符, 数字, 或2~8个汉字")
    private String username;

    private String college;

    private String clazz;

    private String time;

    private Integer accountStatus;
    
    private String imgpath;
    
    private String email;

    private Integer systemNotifications;

	public UserInformation createUserInformationByThis() {
		try {
			return (UserInformation) clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public UserInformation(Integer uid, String password, String username, String college, String clazz, String time, Integer accountStatus, String imgpath, String email, Integer systemNotifications) {
		this(null, uid, password, username, college, clazz, time, accountStatus, imgpath, email, systemNotifications);
	}

	public UserInformation(Integer id, Integer uid, String password, String username, String college, String clazz,
						   String time, Integer accountStatus, String imgpath, String email) {
		this(id, uid, password, username, college, clazz, time, accountStatus, imgpath, email, null);
	}

	public UserInformation(Integer id, Integer uid, String password, String username, String college, String clazz,
			String time, Integer accountStatus, String imgpath) {
		this(id, uid, password, username, college, clazz, time, accountStatus, imgpath, null);
	}

	public UserInformation(Integer id, Integer uid, String password, String username, String college, String clazz,
			String time, Integer accountStatus) {
		this(id, uid, password, username, college, clazz, time, accountStatus, null);
	}
    
    public UserInformation(Integer uid) {
    	this(null, uid, null, null, null, null , null, null);
    }
}