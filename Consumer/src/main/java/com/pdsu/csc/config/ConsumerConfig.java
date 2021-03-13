package com.pdsu.csc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdsu.csc.bean.CrossConfig;
import com.pdsu.csc.utils.DateUtils;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.StringUtils;
import feign.RequestInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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

//    @Bean
//    public WebMvcConfigurer webMvcConfigurer(CrossConfig crossConfig) {
//        log.info("系统初始化...允许以下 IP 进行访问: " + crossConfig.getAllowIp());
//        log.info("系统初始化...允许添加以下请求头: " + crossConfig.getAllowHeader());
//        log.info("系统初始化...允许以下请求方式访问: " + crossConfig.getAllowMethod());
//        log.info("系统初始化...允许以下请求头暴露: " + crossConfig.getExposedHeader());
//        log.info("系统初始化...是否允许保持用户认证状态: " + Boolean.TRUE);
//        log.info("系统初始化...跨域预检间隔时间为: " + crossConfig.getMaxAge() + "s");
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")	// 允许跨域访问的路径
//                        .allowedOrigins(crossConfig.getAllowIpOrigin())	// 允许跨域访问的源
//                        .allowedMethods(crossConfig.getAllowMethodOrigin())
//                        .maxAge(crossConfig.getMaxAge())	// 预检间隔时间
//                        .allowedHeaders(crossConfig.getAllowHeaderOrigin()) // 允许头部设置
//                        .exposedHeaders(crossConfig.getExposedHeaderOrigin())
//                        .allowCredentials(Boolean.TRUE);	// 是否发送cookie
//            }
//        };
//    }

    /**
     *  redis
     */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
