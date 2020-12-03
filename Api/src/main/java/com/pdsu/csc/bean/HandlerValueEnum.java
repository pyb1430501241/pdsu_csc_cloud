package com.pdsu.csc.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 半梦
 * @create 2020-08-30 21:37
 */
@AllArgsConstructor
@Getter
public enum HandlerValueEnum {

    /**
     * 图片后缀名
     */
    IMG_SUFFIX("imgSuffix"),
    /**
     * 文件上传地址
     */
    FILE_FILEPATH("fileFilePath"),
    /**
     * 头像上传地址
     */
    USER_IMG_FILEPATH("userImgFilePath"),
    /**
     * 博客页面图片上传地址
     */
    BLOB_IMG_FILEPATH("blobImgFilePath"),
    /**
     * 用户默认头像名
     */
    USER_IMG_NAME("userImgName"),
    /**
     * 未知情况
     */
    OTHER("default");
    private String key;

    public static HandlerValueEnum getByKey(String key){
        for (HandlerValueEnum constants : values()) {
            if (constants.getKey().equalsIgnoreCase(key)) {
                return constants;
            }
        }
        return OTHER;
    }

}
