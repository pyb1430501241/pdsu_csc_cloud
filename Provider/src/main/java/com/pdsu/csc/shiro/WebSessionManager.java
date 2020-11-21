package com.pdsu.csc.shiro;

import com.pdsu.csc.utils.SimpleUtils;
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
public class WebSessionManager extends DefaultWebSessionManager{
	
	public static final String AUTHORIZATION = "Authorization";
	
	private static final String REFERENCED_SESSION_ID_SOURCE  = "Stateless request";

	public WebSessionManager() {
		Cookie cookie = new SimpleCookie(AUTHORIZATION);
		cookie.setHttpOnly(false);
		setSessionIdCookieEnabled(true);
		setSessionIdUrlRewritingEnabled(true);
		setSessionIdCookie(cookie);
		// 本系统默认单位是秒, 而 Shiro 框架默认以毫秒为单位, 故乘以 1000
		setGlobalSessionTimeout(SimpleUtils.CSC_WEEK * 1000);
	}
	
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
		if(!StringUtils.isEmpty(sessionId)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return sessionId;
		}
		return super.getSessionId(request, response);
	}

}
