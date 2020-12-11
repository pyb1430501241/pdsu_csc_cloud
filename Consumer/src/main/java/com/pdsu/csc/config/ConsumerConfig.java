package com.pdsu.csc.config;

import com.pdsu.csc.utils.DateUtils;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.StringUtils;
import feign.RequestInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author 半梦
 * @create 2020-11-11 11:39
 */
@Configuration
@Log4j2
public class ConsumerConfig {

    private static final String ALL = "*";

//    /**
//     *  跨域
//     */
//    @Bean
//    public CorsConfiguration buildConfig(@Value("${csc.cors.allow-ip}")String allowIp) {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        if(allowIp.equals("all")) {
//            allowIp = ALL;
//            corsConfiguration.addAllowedOrigin(allowIp);
//        } else {
//            String[] allowIps = StringUtils.splitString(allowIp);
//            for (String allow : allowIps) {
//                corsConfiguration.addAllowedOrigin(allow.trim());
//            }
//        }
//        log.info("系统初始化...允许以下 IP 进行访问: " + corsConfiguration.getAllowedOrigins());
//        corsConfiguration.addAllowedHeader(ALL);
//        log.info("系统初始化...允许添加以下请求头: " + corsConfiguration.getAllowedHeaders());
//        corsConfiguration.addAllowedMethod("POST");
//        corsConfiguration.addAllowedMethod("GET");
//        corsConfiguration.addAllowedMethod("OPTIONS");
//        log.info("系统初始化...允许以下请求方式访问: " + corsConfiguration.getAllowedMethods());
//        corsConfiguration.addExposedHeader("Set-Cookie");
//        log.info("系统初始化...允许以下请求头暴露: " + corsConfiguration.getExposedHeaders());
//        corsConfiguration.setAllowCredentials(true);
//        log.info("系统初始化...是否允许保持用户认证状态: " + corsConfiguration.getAllowCredentials());
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter(CorsConfiguration configuration) {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); //注册
//        return new CorsFilter(source);
//    }

    /**
     * 允许访问的请求方式
     */
    private static final String [] ALLOW_METHOD = new String[]{"POST", "GET", "OPTIONS"};

    /**
     * 允许对外暴露的请求头
     */
    /**
     * 允许对外暴露的请求头
     */
    public static final String [] EXPOSED_HEADER = new String[]{
            HttpUtils.getSetCookieName(),
            HttpUtils.getSessionHeader(),
            "Cookie",
            HttpUtils.getRememberCookieName()
    };

    /**
     * 跨域预检间隔时间
     */
    private static final long MAX_AGE = DateUtils.CSC_MINUTE * 30;

    @Bean
    public WebMvcConfigurer webMvcConfigurer(@Value("${csc.cors.allow-ip}") String allowIp) {
        String [] allowOrigin;
        if(allowIp.equals("all")) {
            allowOrigin = new String[]{ALL};
        } else {
            allowOrigin = StringUtils.splitString(allowIp);
        }
        log.info("系统初始化...允许以下 IP 进行访问: " + Arrays.asList(allowOrigin));
        log.info("系统初始化...允许添加以下请求头: " + ALL);
        log.info("系统初始化...允许以下请求方式访问: " + Arrays.asList(ALLOW_METHOD));
        log.info("系统初始化...允许以下请求头暴露: " + Arrays.asList(EXPOSED_HEADER));
        log.info("系统初始化...是否允许保持用户认证状态: " + Boolean.TRUE);
        log.info("系统初始化...跨域预检间隔时间为: " + MAX_AGE + "s");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")	// 允许跨域访问的路径
                        .allowedOrigins(allowOrigin)	// 允许跨域访问的源
                        .allowedMethods(ALLOW_METHOD)
                        .maxAge(MAX_AGE)	// 预检间隔时间
                        .allowedHeaders(ALL) // 允许头部设置
                        .exposedHeaders(EXPOSED_HEADER)
                        .allowCredentials(Boolean.TRUE);	// 是否发送cookie
            }
        };
    }
}
