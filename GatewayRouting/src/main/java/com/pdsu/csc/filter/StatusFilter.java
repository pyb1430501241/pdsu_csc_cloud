package com.pdsu.csc.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.bean.ZuulStatus;
import com.pdsu.csc.shiro.WebSessionManager;
import com.pdsu.csc.utils.DateUtils;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.RedisUtils;
import com.pdsu.csc.utils.ShiroUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 半梦
 * @create 2021-02-21 19:21
 */
@Component
public class StatusFilter extends ZuulFilter {

    private static final String BEFORE_REQUEST = ZuulStatus.PRE.getStatus();

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SecurityManager securityManager;

    @Override
    public String filterType() {
        return BEFORE_REQUEST;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        DefaultSecurityManager manager = (DefaultSecurityManager) securityManager;
        // 获取请求头里的 sessionId
        String sessionId = (String) ((WebSessionManager)manager.getSessionManager())
                .getSessionId(request, response);

        if(sessionId == null) {
            return null;
        }

        UserInformation userInformation = ShiroUtils.getUserInformation(sessionId, request, response);

        // 如果用户已登录
        if(userInformation != null) {
            // 添加认证请求头
            context.addZuulRequestHeader(HttpUtils.getSessionHeader(), sessionId);
            // 把用户信息存储到 redis 缓存中
            UserInformation o = (UserInformation) redisUtils.get(sessionId);
            if(o == null) {
                redisUtils.set(sessionId, userInformation, DateUtils.CSC_DAY);
            } else {
                // 如果缓存中有用户数据, 则使用缓存中的数据
                userInformation = o;
            }
        } else {
            // 如果未登录则尝试清除 redis 里当前用户的数据
            redisUtils.del(sessionId);
        }

        return null;
    }

}
