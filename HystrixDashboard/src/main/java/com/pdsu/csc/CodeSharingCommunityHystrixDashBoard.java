package com.pdsu.csc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author 半梦
 * @create 2020-11-16 19:22
 */
@SpringBootApplication
@EnableHystrixDashboard
public class CodeSharingCommunityHystrixDashBoard {

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingCommunityHystrixDashBoard.class, args);
    }

}
