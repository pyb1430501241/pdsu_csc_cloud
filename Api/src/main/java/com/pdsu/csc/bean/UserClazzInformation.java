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
public class UserClazzInformation implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer cid;

    public UserClazzInformation(Integer uid, Integer cid) {
        this.uid = uid;
        this.cid = cid;
    }

}