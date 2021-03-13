package com.pdsu.csc.shiro;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.web.mgt.CookieRememberMeManager;

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
