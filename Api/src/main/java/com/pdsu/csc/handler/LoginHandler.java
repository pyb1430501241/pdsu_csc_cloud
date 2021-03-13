package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 半梦
 * @create 2020-07-13 13:52
 */
public interface LoginHandler {

    /**
     * 判断用户是否登录, 如未登录抛出异常
     */
    void loginOrNotLogin(@Nullable UserInformation user) throws UserNotLoginException;

    /**
     * 根据请求头判断是否登录
     */
    void loginOrNotLogin(@NonNull HttpServletRequest request) throws UserNotLoginException;

    /**
     * 处理登录
     * @param uid 账号
     * @param password 密码
     * @param hit cache获取数据的key
     * @param code 输入的验证码
     * @param flag  是否记住密码 默认为不记住
     * @return
     * 	如登录成功, 返回用户信息及其对应的 sessionId;
     * 	如失败则返回失败原因
     */
    default Result login(@RequestParam String uid, @RequestParam String password,
                        @RequestParam String hit, @RequestParam String code,
                        @RequestParam(value = "flag", defaultValue = "0")Integer flag) throws Exception {
        return Result.fail();
    }

    /**
     * 退出登录
     */
    default Result logout(HttpServletRequest request, HttpServletResponse response) {
        return Result.fail();
    }

}
