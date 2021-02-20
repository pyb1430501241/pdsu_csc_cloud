package com.pdsu.csc.shiro;

import com.pdsu.csc.utils.CookieUtils;
import com.pdsu.csc.utils.HttpUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 半梦
 * @create 2020-12-11 16:02
 */
@Log4j2
public class WebCookieRememberMeManager extends CookieRememberMeManager {

    @SuppressWarnings("all")
    public WebCookieRememberMeManager() {
        super();
        getCookie().setHttpOnly(false);
        log.info("系统初始化...Shiro记住我 Cookie 是否为仅http访问: " + getCookie().isHttpOnly());
        log.info("系统初始化...Shiro记住我 Cookie 存在时间为: " + getCookie().getMaxAge() + "s");
    }

}
