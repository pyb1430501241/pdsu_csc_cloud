package com.pdsu.csc.config.dao;

import java.io.IOException;

/**
 * @author 半梦
 * @create 2020-11-28 15:00
 */
public interface InitDao {

    /**
     *  读取文件
     * @param filepath 文件地址
     * @throws IOException
     *  读取失败或文件未找到
     */
    public void reader(String filepath) throws IOException;

    /**
     * 初始化系统参数
     */
    public void initParameters();

    /**
     * 创建系统必要文件
     */
    public void mkdirs();

}
