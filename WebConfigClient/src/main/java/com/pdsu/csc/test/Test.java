package com.pdsu.csc.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 半梦
 * @create 2020-11-26 18:05
 */
@RestController
public class Test {

    @Value("${spring.application.name}")
    private String name;

    @RequestMapping("/config")
    public String config() {
        System.out.println(name);
        return name;
    }
}
