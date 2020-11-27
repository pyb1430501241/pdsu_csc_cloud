package com.pdsu.csc.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;

/**
 * @author 半梦
 * @create 2020-11-26 19:24
 */
public final class StringUtils {

    /**
     * 测试字符串是否为 null 或长度为0
     * 如为 null 或长度为0 返回 true
     */
    public static boolean isBlank(@Nullable String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 拼装字符串
     * @param args
     * @return
     */
    @NonNull
    public static String toString(@NonNull Object... args) {
        return Arrays.asList(args).toString();
    }

    /**
     * 获取文件后缀名
     * @param name
     * @return
     */
    @Nullable
    public static String getSuffixName(@NonNull String name) {
        String point = getSuffixNameExceptPoint(name);
        if(point == null) {
            return null;
        }
        return "." + point;
    }

    /**
     * 获取文件后缀名, 除去点
     * @param name
     * @return
     */
    @Nullable
    public static String getSuffixNameExceptPoint(@NonNull String name) {
        return name.lastIndexOf(".") == -1 || name.lastIndexOf(".") == name.length() - 1
                ? null : name.substring(name.lastIndexOf(".") + 1);
    }

    /**
     * 邮箱加密
     * @param str
     * @return
     */
    @NonNull
    @SuppressWarnings("all")
    public static String getAsteriskForString(@NonNull String str) {
        StringBuilder builder = new StringBuilder(str);
        int length = builder.length();
        if (length > 8) {
            builder.replace(2, 8, "******");
        } else if(length < 6) {
        } else {
            builder.replace(1, 5, "******");
        }
        return builder.toString();
    }

}
