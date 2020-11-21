package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * @author 半梦
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer roleid;

    public UserRole(Integer uid, Integer roleid) {
        this.uid = uid;
        this.roleid = roleid;
    }
}