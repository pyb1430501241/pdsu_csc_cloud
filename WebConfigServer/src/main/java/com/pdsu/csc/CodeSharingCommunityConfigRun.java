package com.pdsu.csc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author 半梦
 * @create 2020-11-21 16:43
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class CodeSharingCommunityConfigRun {

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingCommunityConfigRun.class , args);
    }

}
