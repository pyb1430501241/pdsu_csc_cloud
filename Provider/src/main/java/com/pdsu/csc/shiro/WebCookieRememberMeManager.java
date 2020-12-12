package com.pdsu.csc.shiro;

import com.pdsu.csc.utils.CookieUtils;
import com.pdsu.csc.utils.HttpUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
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

    protected void rememberSerializedIdentity(Subject subject, byte[] serialized) {
        if (!WebUtils.isHttp(subject)) {
            return;
        }
        HttpServletRequest request = WebUtils.getHttpRequest(subject);
        HttpServletResponse response = WebUtils.getHttpResponse(subject);
        //对用户信息进行 base64 加密
        String base64 = Base64.encodeToString(serialized);
        // 成员变量 cookie 只是为了提供一个模板
        Cookie template = getCookie();
        Cookie cookie = new SimpleCookie(template);
        cookie.setValue(base64);

        // 获取 cookie 的各种属性
        String name = cookie.getName();
        String value = cookie.getValue();
        String comment = cookie.getComment();
        String domain = cookie.getDomain();
        String path = CookieUtils.calculatePath(request, getCookie());
        int maxAge = cookie.getMaxAge();
        int version = cookie.getVersion();
        boolean secure = cookie.isSecure();
        boolean httpOnly = cookie.isHttpOnly();
        Cookie.SameSiteOptions sameSite = cookie.getSameSite();

        //讲 cookie 封装程对应的格式
        String endValue = CookieUtils.buildHeaderValue(name, value, comment, domain, path, maxAge, version, secure, httpOnly, sameSite);
        if(com.pdsu.csc.utils.StringUtils.isBlank(endValue)) {
            return;
        }
        // 将其添加到请求头上
        response.addHeader(HttpUtils.getRememberCookieName(), endValue);
    }

}
