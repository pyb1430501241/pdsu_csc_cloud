package com.pdsu.csc.config.dao.impl;

import com.pdsu.csc.bean.HandlerValueEnum;
import com.pdsu.csc.config.dao.InitDao;
import com.pdsu.csc.handler.ParentHandler;
import com.pdsu.csc.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
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
        ClassLoader classLoader = ParentHandler.class.getClassLoader();
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
                    ParentHandler.imgSuffix = value;
                    ParentHandler.imgSuffixExceptPoint = StringUtils.getSuffixNameExceptPoint(value);
                    break;
                case FILE_FILEPATH:
                    ParentHandler.fileFilePath = value;
                    break;
                case USER_IMG_FILEPATH:
                    ParentHandler.userImgFilePath = value;
                    break;
                case BLOB_IMG_FILEPATH:
                    ParentHandler.blobImgFilePath = value;
                    break;
                case USER_IMG_NAME:
                    ParentHandler.userImgName = value;
                    break;
                default:
            }
        }
    }

    @Override
    public void mkdirs() {
        File file = new File(ParentHandler.userImgFilePath);
        log.info("系统初始化...创建目录: " + ParentHandler.userImgFilePath);
        if(!file.exists()) {
            file.mkdirs();
        }
        file = new File(ParentHandler.blobImgFilePath);
        log.info("系统初始化...创建目录: " + ParentHandler.blobImgFilePath);
        if(!file.exists()) {
            file.mkdirs();
        }
        file = new File(ParentHandler.fileFilePath);
        log.info("系统初始化...创建目录: " + ParentHandler.fileFilePath);
        if(!file.exists()) {
            file.mkdirs();
        }
    }

}
