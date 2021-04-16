package com.pdsu.csc.handler;

import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录状态
 * @author 半梦
 * @create 2021-03-13 17:47
 */
public interface AuthenticationHandler {

    /**
     * 判断用户是否登录, 如未登录抛出异常
     */
    void loginOrNotLogin(@Nullable UserInformation user) throws UserNotLoginException;

    /**
     * 根据请求头判断是否登录
     */
    void loginOrNotLogin(@NonNull HttpServletRequest request) throws UserNotLoginException;

}
