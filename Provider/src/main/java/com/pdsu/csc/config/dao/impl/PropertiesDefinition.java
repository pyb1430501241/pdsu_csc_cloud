package com.pdsu.csc.config.dao.impl;

import com.pdsu.csc.bean.HandlerValueEnum;
import com.pdsu.csc.config.dao.InitDao;
import com.pdsu.csc.handler.InitHandler;
import com.pdsu.csc.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author 半梦
 * @create 2020-11-28 15:03
 */
@SuppressWarnings("all")
public class PropertiesDefinition implements InitDao {

    private static final Logger log = LoggerFactory.getLogger("初始化日志");

    private Properties properties = new Properties();

    private Map<String, String> initMap = new HashMap<>();

    @Override
    public void reader(String filepath) throws IOException {
        ClassLoader classLoader = InitHandler.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream(filepath);
        properties.load(in);
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            log.info("系统初始化...读取配置文件参数: " + key + ", 其值为: " + value);
            initMap.put(key, value);
        }
    }

    @Override
    public void initParameters() {
        Set<String> keys = initMap.keySet();
        for(String key : keys) {
            String value = initMap.get(key);
            log.info("系统初始化...正在初始化参数: " + key + ", 其值为: " + value);
            switch (HandlerValueEnum.getByKey(key)) {
                case IMG_SUFFIX:
                    InitHandler.imgSuffix = value;
                    InitHandler.imgSuffixExceptPoint = StringUtils.getSuffixNameExceptPoint(value);
                    break;
                case FILE_FILEPATH:
                    InitHandler.fileFilePath = value;
                    break;
                case USER_IMG_FILEPATH:
                    InitHandler.userImgFilePath = value;
                    break;
                case BLOB_IMG_FILEPATH:
                    InitHandler.blobImgFilePath = value;
                    break;
                case USER_IMG_NAME:
                    InitHandler.userImgName = value;
                    break;
                default:
            }
        }
    }

    @Override
    public void mkdirs() {
        File file = new File(InitHandler.userImgFilePath);
        log.info("系统初始化...创建目录: " + InitHandler.userImgFilePath);
        if(!file.exists()) {
            file.mkdirs();
        }
        file = new File(InitHandler.blobImgFilePath);
        log.info("系统初始化...创建目录: " + InitHandler.blobImgFilePath);
        if(!file.exists()) {
            file.mkdirs();
        }
        file = new File(InitHandler.fileFilePath);
        log.info("系统初始化...创建目录: " + InitHandler.fileFilePath);
        if(!file.exists()) {
            file.mkdirs();
        }
    }

}
