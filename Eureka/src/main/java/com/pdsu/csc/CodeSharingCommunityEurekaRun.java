package com.pdsu.csc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 半梦
 * @create 2020-11-09 18:38
 */

@SpringBootApplication
@EnableEurekaServer
public class CodeSharingCommunityEurekaRun {

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingCommunityEurekaRun.class, args);
    }

}
