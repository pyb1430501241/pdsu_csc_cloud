package com.pdsu.csc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 半梦
 * @create 2020-11-09 18:59
 */
@SpringCloudApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.pdsu.csc"})
public class CodeSharingCommunityConsumerRun {

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingCommunityConsumerRun.class, args);
    }

}
