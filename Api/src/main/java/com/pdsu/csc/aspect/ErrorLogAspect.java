package com.pdsu.csc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 半梦
 * @create 2020-11-10 17:34
 */
@Aspect
@Component
public class ErrorLogAspect {

    private static final Logger log = LoggerFactory.getLogger("Feign 容错机制");

    @Before("execution(public * com.pdsu.csc.service.fallback.ProviderServiceError.*(..))")
    public void before(JoinPoint joinPoint) throws Throwable {
        log.error("服务端已挂掉，目前服务的是Feign的容错机制");
    }


}
