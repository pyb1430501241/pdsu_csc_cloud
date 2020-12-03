package com.pdsu.csc.config.dao.impl;

import com.pdsu.csc.bean.HandlerValueEnum;
import com.pdsu.csc.config.dao.InitDao;
import com.pdsu.csc.handler.ParentHandler;
import com.pdsu.csc.utils.StringUtils;

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
            initMap.put(key, value);
        }
    }

    @Override
    public void initParameters() {
        Set<String> keys = initMap.keySet();
        for(String key : keys) {
            String value = initMap.get(key);
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
        if(!file.exists()) {
            file.mkdirs();
        }
        file = new File(ParentHandler.blobImgFilePath);
        if(!file.exists()) {
            file.mkdirs();
        }
        file = new File(ParentHandler.fileFilePath);
        if(!file.exists()) {
            file.mkdirs();
        }
    }

}
