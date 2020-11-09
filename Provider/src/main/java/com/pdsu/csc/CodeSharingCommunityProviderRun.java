package com.pdsu.csc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 半梦
 * @create 2020-11-09 15:14
 */

@SpringCloudApplication
@EnableEurekaClient
@MapperScan({"com.pdsu.csc.dao"})
public class CodeSharingCommunityProviderRun {

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingCommunityProviderRun.class, args);
    }

}
