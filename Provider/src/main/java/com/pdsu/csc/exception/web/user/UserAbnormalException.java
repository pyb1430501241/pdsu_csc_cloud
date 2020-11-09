package com.pdsu.csc.exception.web.user;

import org.apache.shiro.authc.CredentialsException;

/**
 * 账号登录相关
 * @author 半梦
 * @create 2020-08-20 16:05
 */
public class UserAbnormalException extends CredentialsException {

    public UserAbnormalException() {
    }

    public UserAbnormalException(String message) {
        super(message);
    }
}
