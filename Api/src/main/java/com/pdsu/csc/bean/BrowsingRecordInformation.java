package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * @author 半梦
 * @create 2020-08-12 23:04
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrowsingRecordInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private String username;

    private String imgpath;

    private String title;

    private Integer wfid;

    private Integer type;

    private String createtime;

    public BrowsingRecordInformation(Integer uid, String username, String imgpath, Integer wfid, Integer type, String createtime) {
        this.uid = uid;
        this.username = username;
        this.imgpath = imgpath;
        this.wfid = wfid;
        this.type = type;
        this.createtime = createtime;
    }

}
