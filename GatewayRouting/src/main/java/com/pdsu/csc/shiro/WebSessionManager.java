package com.pdsu.csc.shiro;

import com.pdsu.csc.utils.DateUtils;
import com.pdsu.csc.utils.HttpUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * Session 管理器
 * @author 半梦
 *
 */
@Log4j2
public class WebSessionManager extends DefaultWebSessionManager{
	
	private static final String REFERENCED_SESSION_ID_SOURCE  = "Stateless request";

	@SuppressWarnings("all")
	public WebSessionManager() {
		Cookie cookie = new SimpleCookie(HttpUtils.getSessionHeader());
		log.info("系统初始化...Shiro认证cookie名为: " + cookie.getName());
		cookie.setHttpOnly(false);
		log.info("系统初始化...Shiro认证cookie是否为仅 Http 可见: " + cookie.isHttpOnly());
		setSessionIdCookieEnabled(true);
		setSessionIdUrlRewritingEnabled(true);
		setSessionIdCookie(cookie);
		setGlobalSessionTimeout(DateUtils.getSessionTimeout());
		log.info("系统初始化...Shiro认证下的 Session会话时间: " + DateUtils.getSessionTimeout());
	}
	
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String sessionId = HttpUtils.getSessionId(WebUtils.toHttp(request));
		if(!StringUtils.isEmpty(sessionId)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return sessionId;
		}
		return super.getSessionId(request, response);
	}

}
