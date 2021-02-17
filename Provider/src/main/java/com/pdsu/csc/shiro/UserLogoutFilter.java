package com.pdsu.csc.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.ShiroUtils;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 半梦
 * @create 2020-12-15 13:12
 */
public class UserLogoutFilter extends LogoutFilter{

    private static final Logger log = LoggerFactory.getLogger("用户退出拦截器");

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);
        UserInformation user = null;
        try {
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
            user = ShiroUtils.getUserInformation(
                    HttpUtils.getSessionId(httpServletRequest), httpServletRequest, httpServletResponse);
            log.info("用户: " + user.getUid() + ", 退出登录");
            subject.logout();
        } catch (Exception ise) {
            log.info("用户退出登录失败");
            return false;
        }
        log.info("用户: " + user.getUid() + ", 退出登录成功");
        return false;
    }

}
