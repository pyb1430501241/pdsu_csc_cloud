package com.pdsu.csc.service;

import org.springframework.lang.NonNull;

/**
 * 用户相关功能所需要实现的接口, 意在对所有用户相关功能提供一些信息
 * @author 半梦
 * @create 2021-03-12 19:14
 */
public interface UserService {

    /**
     * 查询用户是否存在
     * @param uid 学号
     * @return
     *  如存在则返回 true
     */
    boolean isExistByUid(@NonNull Integer uid);

}
