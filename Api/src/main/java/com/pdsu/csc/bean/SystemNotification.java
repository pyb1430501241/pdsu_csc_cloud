package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 系统通知
 * @author 半梦
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemNotification implements Serializable {
    private Integer id;

    private Integer uid;

    private String content;

    private Integer sid;

    private Integer unread;

    private String createtime;

    public SystemNotification(Integer uid, String content, Integer sid, Integer unread, String createtime) {
        this.uid = uid;
        this.content = content;
        this.sid = sid;
        this.unread = unread;
        this.createtime = createtime;
    }
}