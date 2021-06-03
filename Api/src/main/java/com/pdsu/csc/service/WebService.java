package com.pdsu.csc.service;

import org.springframework.lang.NonNull;

/**
 * 所有网页信息相关均需要实现的接口
 * @author 半梦
 * @create 2021-03-12 19:17
 */
public interface WebService {

    /**
     * 查询文章是否存在
     * @param webId 文章 id
     * @return
     * <li>如存在则 true</li>
     * <li>反之 false</li>
     */
    @SuppressWarnings("all")
    boolean isExistByWebId(@NonNull Integer webId);

}
