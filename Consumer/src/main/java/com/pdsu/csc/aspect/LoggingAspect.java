package com.pdsu.csc.aspect;

import com.pdsu.csc.utils.HttpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 半梦
 * @create 2020-11-27 15:44
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger("用户访问日志");

    @Before(value = "execution(public * com.pdsu.csc.handler..*(..))&&args(.., request)")
    public void before(JoinPoint point, HttpServletRequest request) {
        log.info("当前用户 SessionId为: " + HttpUtils.getSessionId(request)
            + ", IP地址为: " + HttpUtils.getIpAddr(request));

    }

}
