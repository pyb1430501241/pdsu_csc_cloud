package com.pdsu.csc;

import com.pdsu.csc.config.ConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 半梦
 * @create 2020-11-09 18:59
 */
@SpringCloudApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.pdsu.csc.service"}, defaultConfiguration = ConsumerConfig.class)
@PropertySource("classpath:properties/csc.properties")
public class CodeSharingCommunityConsumerRun {

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingCommunityConsumerRun.class, args);
    }

}
