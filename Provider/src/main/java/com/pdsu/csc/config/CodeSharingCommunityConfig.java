package com.pdsu.csc.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.pdsu.csc.es.RestHighLevelClientFactory;
import com.pdsu.csc.shiro.LoginRealm;
import com.pdsu.csc.shiro.UserLogoutFilter;
import com.pdsu.csc.shiro.WebCookieRememberMeManager;
import com.pdsu.csc.shiro.WebSessionManager;
import com.pdsu.csc.utils.DateUtils;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.StringUtils;
import com.pdsu.csc.web.WebStartInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.User;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.util.*;

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

    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache/ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    @Bean
    public net.sf.ehcache.CacheManager cacheManager(EhCacheManagerFactoryBean cacheManagerFactoryBean) {
        return cacheManagerFactoryBean.getObject();
    }

    @Bean
    public CacheManager springCacheManager(net.sf.ehcache.CacheManager cacheManager) {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(cacheManager);
        return ehCacheCacheManager;
    }

    /**
     *  Shiro
     */
    @Bean
    public ModularRealmAuthenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
        return authenticator;
    }

    private static final String DEFAULT_ENCRYPTION_MODE = "MD5";
    private static final Integer DEFAULT_ENCRYPTION_NUMBER = 2;

    @Bean
    public Realm loginRealm() {
        LoginRealm realm = new LoginRealm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(DEFAULT_ENCRYPTION_MODE);
        log.info("系统初始化...使用默认加密规则: " + DEFAULT_ENCRYPTION_MODE);
        credentialsMatcher.setHashIterations(DEFAULT_ENCRYPTION_NUMBER);
        log.info("系统初始化...使用默认加密规则, 默认加密次数: " + DEFAULT_ENCRYPTION_NUMBER);
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    @Bean
    public SecurityManager securityManager(net.sf.ehcache.CacheManager cacheManager, Realm loginRealm) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(loginRealm);
        webSecurityManager.setSessionManager(new WebSessionManager());
        webSecurityManager.setRememberMeManager(new WebCookieRememberMeManager());
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        webSecurityManager.setCacheManager(ehCacheManager);
        return webSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("logout", new UserLogoutFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/user/logout", "logout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
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

    private static final String ALL = "*";

    /**
     *  跨域
     */
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
    private static final String [] EXPOSED_HEADER = new String[]{
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





}
