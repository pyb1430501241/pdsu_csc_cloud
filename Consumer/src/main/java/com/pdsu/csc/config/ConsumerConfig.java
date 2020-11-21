package com.pdsu.csc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author 半梦
 * @create 2020-11-11 11:39
 */
@Configuration
public class ConsumerConfig {

    /**
     *  跨域
     */
    @Bean
    public CorsConfiguration buildConfig(@Value("${csc.cors.allow-ip}")String allowIp) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        if(allowIp.equals("all")) {
            allowIp = "*";
        }
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


}
