package com.pdsu.csc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author 半梦
 * @create 2020-11-16 21:09
 */
@SpringBootApplication
@EnableZuulProxy
@MapperScan({"com.pdsu.csc.dao"})
public class CodeSharingCommunityGatewayRun {

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingCommunityGatewayRun.class, args);
    }

}
