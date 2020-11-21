package com.pdsu.csc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.pdsu.csc.es.RestHighLevelClientFactory;
import com.pdsu.csc.shiro.LoginRealm;
import com.pdsu.csc.shiro.WebSessionManager;
import com.pdsu.csc.web.WebStartInterceptor;
import com.thetransactioncompany.cors.CORSFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.aspectj.lang.annotation.Aspect;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.util.*;

/**
 * @author 半梦
 * @create 2020-11-09 14:16
 */
@Configuration
public class CodeSharingCommunityConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    /**
     * 配置 Druid 监控
     * 1. 配置一个管理后台的 Servlet
     */
    @Bean
    @SuppressWarnings("unchecked")
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> map = new HashMap<>();
        map.put("loginUsername", "root");
        map.put("loginPassword", "pdsu_csc_admin");
        bean.setInitParameters(map);
        return bean;
    }
    /**
     * 2. 配置一个监控的 Filter
     */
    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
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

    @Bean
    public Realm loginRealm() {
        LoginRealm realm = new LoginRealm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    @Bean
    public SecurityManager securityManager(net.sf.ehcache.CacheManager cacheManager, Realm loginRealm) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(loginRealm);
        webSecurityManager.setSessionManager(new WebSessionManager());
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        webSecurityManager.setCacheManager(ehCacheManager);
        return webSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
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

    /**
     *  跨域
     */
    @Bean
    public CorsConfiguration buildConfig(@Value("${csc.cors.allow-ip}")String allowIp) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(allowIp);
        corsConfiguration.addAllowedHeader("Authorization, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        corsConfiguration.addAllowedMethod("POST, GET");
        corsConfiguration.addExposedHeader("Set-Cookie");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(CorsConfiguration configuration) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //注册
        return new CorsFilter(source);
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
