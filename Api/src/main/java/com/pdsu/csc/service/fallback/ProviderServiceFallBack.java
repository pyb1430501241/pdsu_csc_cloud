package com.pdsu.csc.service.fallback;

import com.pdsu.csc.service.ProviderService;
import feign.hystrix.FallbackFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author 半梦
 * @create 2020-11-10 15:39
 * Feign 检测到服务关闭后, 会自动向客户端发送默认消息
 */
@Repository
public class ProviderServiceFallBack implements FallbackFactory<ProviderService> {

    @Override
    public ProviderService create(Throwable throwable) {
        return new ProviderServiceError();
    }

}
