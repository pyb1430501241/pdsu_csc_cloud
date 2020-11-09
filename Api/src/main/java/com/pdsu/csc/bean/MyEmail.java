package com.pdsu.csc.bean;

import java.io.Serializable;

/**
 * 邮箱
 * @author Admin
 *
 */
public class MyEmail implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer uid;

    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

	public MyEmail(Integer id, Integer uid, String email) {
		super();
		this.id = id;
		this.uid = uid;
		this.email = email;
	}

    @Override
	public String toString() {
		return "MyEmail [id=" + id + ", uid=" + uid + ", email=" + email + "]";
	}

	public MyEmail() {
	}
}