package com.pdsu.csc.bean;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author 半梦
 * @create 2021-02-19 17:33
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "cross")
@PropertySource("classpath:properties/cross.properties")
public class CrossConfig {

    private static final String ALL = "*";

    private static final String ALL_IP = "all";

    private static final String STRING_ALLOW_METHOD = "POST, GET, OPTIONS";
    
    private static final String STRING_EXPOSED_HEADER = "Set-Cookie, Authorization, Cookie, rememberMe";

    /**
     * 默认允许访问的请求方式
     */
    private static final String [] ALLOW_METHOD = new String [] {"POST", "GET", "OPTIONS"};

    /**
     * 默认允许对外暴露的请求头
     */
    private static final String [] EXPOSED_HEADER = new String [] {"Set-Cookie", "Authorization",
                                        "Cookie", "rememberMe"};

    /**
     * 默认允许的 IP
     */
    private static final String [] ALLOW_IP = new String [] {ALL};

    /**
     * 默认允许携带的请求头
     */
    private static final String [] ALLOW_HEADER = new String [] {ALL};

    /**
     * 跨域预检间隔时间
     */
    private static final long MAX_AGE = 60 * 30;

    @Nullable
    private String allowIp;

    @Nullable
    private String allowMethod;

    @Nullable
    private String exposedHeader;

    @Nullable
    private String allowHeader;

    @Nullable
    private Long maxAge;

    @Nullable
    private String [] allowIpOrigin;

    @Nullable
    private String [] allowMethodOrigin;

    @Nullable
    private String [] exposedHeaderOrigin;

    @Nullable
    private String [] allowHeaderOrigin;

    @NonNull
    private static String [] splitString(@NonNull String str) {
        return str.split(",");
    }

    public void setAllowIp(@Nullable String allowIp) {
        if(allowIp == null || allowIp.trim().length() == 0 || allowIp.trim().toLowerCase().equals(ALL_IP)) {
            allowIpOrigin = new String [] {ALL};
            this.allowIp = ALL;
        } else {
            allowIpOrigin = splitString(allowIp);
            this.allowIp = allowIp;
        }
    }

    public void setAllowMethod(@Nullable String allowMethod) {
        if(allowMethod == null || allowMethod.trim().length() == 0) {
            allowMethodOrigin = ALLOW_METHOD;
            this.allowMethod = STRING_ALLOW_METHOD;
        } else {
            allowMethodOrigin = splitString(allowMethod);
            this.allowMethod = allowMethod;
        }
    }

    public void setExposedHeader(@Nullable String exposedHeader) {
        if(exposedHeader == null || exposedHeader.trim().length() == 0) {
            exposedHeaderOrigin = EXPOSED_HEADER;
            this.exposedHeader = STRING_EXPOSED_HEADER;
        } else {
            exposedHeaderOrigin = splitString(exposedHeader);
            this.exposedHeader = exposedHeader;
        }
    }

    public void setAllowHeader(@Nullable String allowHeader) {
        if(allowHeader == null || allowHeader.trim().length() == 0 || allowHeader.trim().toLowerCase().equals(ALL_IP)) {
            allowHeaderOrigin = ALLOW_HEADER;
            this.allowHeader = ALL;
        } else {
            allowHeaderOrigin = splitString(allowHeader);
            this.allowHeader = allowHeader;
        }
    }

    public void setMaxAge(@Nullable Long maxAge) {
        if(maxAge == null || maxAge < 30) {
            this.maxAge = MAX_AGE;
        } else {
            this.maxAge = maxAge;
        }
    }

    /**
     * @return 一个请求所需要被跨域检测的时间间隔
     */
    @NonNull
    public Long getMaxAge() {
        if(maxAge == null) {
            return MAX_AGE;
        }
        return maxAge;
    }

    /**
     * @return 被允许访问的 IP
     */
    @NonNull
    public String[] getAllowIpOrigin() {
        if(allowIpOrigin == null) {
            return ALLOW_IP;
        }
        return allowIpOrigin;
    }

    /**
     * @return 被允许访问的请求方式
     */
    @NonNull
    public String[] getAllowMethodOrigin() {
        if(allowMethodOrigin == null) {
            return ALLOW_METHOD;
        }
        return allowMethodOrigin;
    }

    /**
     * @return 被允许暴露的请求头
     */
    @NonNull
    public String[] getExposedHeaderOrigin() {
        if(exposedHeaderOrigin == null) {
            return EXPOSED_HEADER;
        }
        return exposedHeaderOrigin;
    }

    /**
     * @return 字符串形式, 被允许访问的 IP
     */
    @NonNull
    public String getAllowIp() {
        if(allowIp == null){
            return ALL;
        }
        return allowIp;
    }

    /**
     * @return 字符串形式, 被允许访问的请求方式
     */
    @NonNull
    public String getAllowMethod() {
        if(allowMethod == null) {
            return STRING_ALLOW_METHOD;
        }
        return allowMethod;
    }

    /**
     * @return 字符串形式, 被允许暴露的请求头
     */
    @NonNull
    public String getExposedHeader() {
        if(exposedHeader == null) {
            return STRING_EXPOSED_HEADER;
        }
        return exposedHeader;
    }

    /**
     * @return 字符串形式, 被允许添加的请求头
     */
    @NonNull
    public String getAllowHeader() {
        if(allowHeader == null) {
            return ALL;
        }
        return allowHeader;
    }

    /**
     * @return 被允许添加的请求头
     */
    @NonNull
    public String[] getAllowHeaderOrigin() {
        if(allowHeaderOrigin == null) {
            return ALLOW_HEADER;
        }
        return allowHeaderOrigin;
    }

}
