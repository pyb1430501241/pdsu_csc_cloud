package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 半梦
 * @create 2020-07-13 13:52
 */
@SuppressWarnings("all")
public interface LoginHandler extends AuthenticationHandler {

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
    Result login(@RequestParam String uid, @RequestParam String password,
                        @RequestParam String hit, @RequestParam String code,
                        @RequestParam(value = "flag", defaultValue = "0")Integer flag) throws Exception;

    /**
     * @return  用户的登录状态
     * 如登录, 返回用户信息
     * 反之返回提示语
     *  loginOrNotLogin(user) 通过异常的方式避免了 user 为 null
     */
    Result getLoginStatus() throws Exception;

    /**
     * 退出登录
     */
    Result logout(HttpServletRequest request, HttpServletResponse response);

}
