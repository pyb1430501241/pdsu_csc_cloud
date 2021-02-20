package com.pdsu.csc.config;

/**
 * @author 半梦
 * @create 2020-12-07 21:29
 */

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.pdsu.csc.bean.CrossConfig;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 公共拦截器，处理 feign 远程调用过滤 Cookie, Header 问题
 */
@Configuration
public class FeignCookieInterceptor {

    @Nullable
    private HttpServletRequest getHttpServletRequest() {
        try{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (Exception e){
            return null;
        }
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        // feign 在转发请求时会默认创建一个新的 Request, 所以需要将需要的 Header 添加到新的 Request 请求头里
        return requestTemplate -> {
            HttpServletRequest request = getHttpServletRequest();
            if(request == null) {
                return;
            }
            for (String headerName : CrossConfig.EXPOSED_HEADER) {
                String value = request.getHeader(headerName);
                if(!Objects.isNull(value)) {
                    requestTemplate.header(headerName, value);
                }
            }
        };
    }

    /**
     * @see FeignHystrixConcurrencyStrategyIntellif
     */
    @Bean
    public HystrixConcurrencyStrategy feignHystrixConcurrencyStrategy() {
        return new FeignHystrixConcurrencyStrategyIntellif();
    }
}

