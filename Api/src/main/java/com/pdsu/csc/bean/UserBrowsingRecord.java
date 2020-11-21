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
public class UserBrowsingRecord implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer wfid;

    private Integer tpye;

    private String createtime;

    public UserBrowsingRecord(Integer uid, Integer wfid, Integer tpye, String createtime) {
        this.uid = uid;
        this.wfid = wfid;
        this.tpye = tpye;
        this.createtime = createtime;
    }

}