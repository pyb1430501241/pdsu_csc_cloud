package com.pdsu.csc.utils;

import com.pdsu.csc.bean.UserInformation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.jetbrains.annotations.Contract;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 *  shiro 框架工具类
 * @author 半梦
 *
 */
@SuppressWarnings("all")
public abstract class ShiroUtils {

    @Nullable
    @Contract(pure = true)
	public static UserInformation getUserInformation() {
        Subject subject = SecurityUtils.getSubject();
        //取出身份信息
        UserInformation userInformation = (UserInformation) subject.getPrincipal();
        if(!Objects.isNull(userInformation)) {
        	Session session = subject.getSession();
            UserInformation user = (UserInformation) session.getAttribute("user");
            if(Objects.isNull(user)){
                session.setAttribute("user", userInformation);
            }
            return userInformation;
        } else {
            return null;
        }
	}

    /**
     * 获取 sessionId
     */
    @Nullable
    public static String getSessionId() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        if(Objects.isNull(session)) {
            return null;
        }
        return (String) session.getId();
    }

    /**
     * 获取用户是否已登录
     */
    public static boolean isAuthorization() {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated() || subject.isRemembered();
    }

	
	 /**
	  * 根据 sessionid 获取用户信息
     * @param sessionID
     * @param request
     * @param response
     * @return
     */
    @Nullable
    @Contract("null -> null; !null -> !null")
    public static UserInformation getUserInformation(@Nullable String sessionID, HttpServletRequest request, HttpServletResponse response) {
        try {
            SessionKey key = new WebSessionKey(sessionID, request, response);
            Session se = SecurityUtils.getSecurityManager().getSession(key);
            Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
            UserInformation userInformation = (UserInformation) coll.getPrimaryPrincipal();
            if (!Objects.isNull(userInformation)) {
                UserInformation user = (UserInformation) se.getAttribute("user");
                if (Objects.isNull(user)) {
                    se.setAttribute("user", userInformation);
                }
                return userInformation;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
