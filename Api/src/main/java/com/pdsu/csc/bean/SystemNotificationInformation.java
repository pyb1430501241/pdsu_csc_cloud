package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * @author 半梦
 * @create 2020-08-13 14:35
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemNotificationInformation implements Serializable {

    private SystemNotification systemNotification;

    private String username;

    private String identity;

}
