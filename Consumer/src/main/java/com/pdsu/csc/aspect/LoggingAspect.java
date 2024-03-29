package com.pdsu.csc.aspect;

import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
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
    public void before(HttpServletRequest request) {
        String sessionId = HttpUtils.getSessionId(request);
        log.info("当前用户 SessionId为: "
                + (StringUtils.isBlank(sessionId) ? "用户未登录" : sessionId)
                + ", IP地址为: " + HttpUtils.getIpAddr(request));
    }

    @AfterThrowing(value = "execution(public * com.pdsu.csc.service..*(..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        String str = joinPoint.getTarget().getClass().getName() + "."
                + ((MethodSignature)joinPoint.getSignature()).getMethod().getName();
        log.error("执行方法 " + str + " 出现异常, 异常信息为: " + ex + ", 由 Feign 降级返回相应信息");
    }

}
