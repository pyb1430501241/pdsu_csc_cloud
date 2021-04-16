package com.pdsu.csc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.pdsu.csc.bean.CrossConfig;
import com.pdsu.csc.cache.MybatisRedisCacheTransfer;
import com.pdsu.csc.es.RestHighLevelClientFactory;
import com.pdsu.csc.utils.StringUtils;
import com.pdsu.csc.web.WebStartInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

/**
 * @author 半梦
 * @create 2020-11-09 14:16
 */
@Configuration
public class CodeSharingCommunityConfig {

    private static final Logger log = LoggerFactory.getLogger("初始化日志");

    @Bean
    public DataSource hikariCP(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    /**
     *  es
     */
    @Bean
    public RestHighLevelClientFactory restHighLevel() {
        return new RestHighLevelClientFactory();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(RestHighLevelClientFactory highLevelClientFactory) {
        return highLevelClientFactory.getRestHighLevelClient();
    }

    @Bean
    public ServletContextListener webStartInterceptor() {
        return new WebStartInterceptor();
    }


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
     * $.axios({
     *     method : "POST",
     *     url : "/login",
     *     param : "xxxx",
     *     success : function(result) {
     *        result.data.at
     *     }
     * });
     */

    /**
     * Hystrix
     */
    @Bean
    @SuppressWarnings("unchecked")
    public ServletRegistrationBean hystrixMetricsStreamServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet, "/hystrix.stream");
        registrationBean.setLoadOnStartup(1);
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }


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

    @Bean
    public MybatisRedisCacheTransfer mybatisRedisCacheTransfer(RedisTemplate<String, Object> redisTemplate){
        MybatisRedisCacheTransfer mybatisRedisCacheTransfer = new MybatisRedisCacheTransfer();
        mybatisRedisCacheTransfer.setRedisTemplate(redisTemplate);
        return mybatisRedisCacheTransfer;
    }


}
