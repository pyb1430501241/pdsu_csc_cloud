package com.pdsu.csc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author 半梦
 * @create 2020-12-08 16:23
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Value("${userImgFilePath}")
    private String userImageFilePath;

    @Value("${blobImgFilePath}")
    private String blobImageFilePath;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler(("/user/image/**"))
                .addResourceLocations("file:" + userImageFilePath);
        registry.addResourceHandler("/blob/image/**")
                .addResourceLocations("file:" + blobImageFilePath);
    }

}
