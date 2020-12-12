package com.pdsu.csc.utils;

import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author 半梦
 * @create 2020-12-12 15:36
 */
public class CookieUtils {

    private static final String NAME_VALUE_DELIMITER = "=";
    private static final String PATH_ATTRIBUTE_NAME = "Path";
    private static final String EXPIRES_ATTRIBUTE_NAME = "Expires";
    @SuppressWarnings("all")
    private static final String MAXAGE_ATTRIBUTE_NAME = "Max-Age";
    private static final String DOMAIN_ATTRIBUTE_NAME = "Domain";
    private static final String VERSION_ATTRIBUTE_NAME = "Version";
    private static final String COMMENT_ATTRIBUTE_NAME = "Comment";
    private static final String SECURE_ATTRIBUTE_NAME = "Secure";
    private static final String HTTP_ONLY_ATTRIBUTE_NAME = "HttpOnly";
    private static final String SAME_SITE_ATTRIBUTE_NAME = "SameSite";
    private static final String ATTRIBUTE_DELIMITER = "; ";
    private static final long DAY_MILLIS = 86400000;
    public static final int DEFAULT_VERSION = -1;
    private static final String GMT_TIME_ZONE_ID = "GMT";
    private static final String COOKIE_DATE_FORMAT_STRING = "EEE, dd-MMM-yyyy HH:mm:ss z";

    public static String calculatePath(HttpServletRequest request, Cookie cookie) {
        String path = org.apache.shiro.util.StringUtils.clean(cookie.getPath());
        if (!org.apache.shiro.util.StringUtils.hasText(path)) {
            path = org.apache.shiro.util.StringUtils.clean(request.getContextPath());
        }

        if (path == null) {
            path = SimpleCookie.ROOT_PATH;
        }
        return path;
    }

    /**
     * 拼装 Set-Cookie 的值
     * @param name Cookie 名字
     * @param value Cookie 值
     * @param maxAge 为负数时, 关闭 session 即删除
     * @see SimpleCookie#buildHeaderValue(String, String, String, String, String, int, int, boolean, boolean, Cookie.SameSiteOptions)
     */
    @Nullable
    @SuppressWarnings("all")
    public static String buildHeaderValue(String name, String value, String comment,
                                    String domain, String path, int maxAge, int version,
                                    boolean secure, boolean httpOnly, Cookie.SameSiteOptions sameSite) {
        if (!org.apache.shiro.util.StringUtils.hasText(name)) {
            return null;
        }

        StringBuilder sb = new StringBuilder(name).append(NAME_VALUE_DELIMITER);

        if (org.apache.shiro.util.StringUtils.hasText(value)) {
            sb.append(value);
        }

        appendComment(sb, comment);
        appendDomain(sb, domain);
        appendPath(sb, path);
        appendExpires(sb, maxAge);
        appendVersion(sb, version);
        appendSecure(sb, secure);
        appendHttpOnly(sb, httpOnly);
        appendSameSite(sb, sameSite);

        return sb.toString();

    }

    private static void appendComment(StringBuilder sb, String comment) {
        if (org.apache.shiro.util.StringUtils.hasText(comment)) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(COMMENT_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(comment);
        }
    }

    private static void appendDomain(StringBuilder sb, String domain) {
        if (org.apache.shiro.util.StringUtils.hasText(domain)) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(DOMAIN_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(domain);
        }
    }

    private static void appendPath(StringBuilder sb, String path) {
        if (StringUtils.hasText(path)) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(PATH_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(path);
        }
    }

    private static void appendExpires(StringBuilder sb, int maxAge) {
        if (maxAge >= 0) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(MAXAGE_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(maxAge);
            sb.append(ATTRIBUTE_DELIMITER);
            Date expires;
            if (maxAge == 0) {
                //delete the cookie by specifying a time in the past (1 day ago):
                expires = new Date(System.currentTimeMillis() - DAY_MILLIS);
            } else {
                //Value is in seconds.  So take 'now' and add that many seconds, and that's our expiration date:
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, maxAge);
                expires = cal.getTime();
            }
            String formatted = toCookieDate(expires);
            sb.append(EXPIRES_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(formatted);
        }
    }

    private static void appendVersion(StringBuilder sb, int version) {
        if (version > DEFAULT_VERSION) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(VERSION_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(version);
        }
    }

    private static void appendSecure(StringBuilder sb, boolean secure) {
        if (secure) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(SECURE_ATTRIBUTE_NAME); //No value for this attribute
        }
    }

    private static void appendHttpOnly(StringBuilder sb, boolean httpOnly) {
        if (httpOnly) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(HTTP_ONLY_ATTRIBUTE_NAME); //No value for this attribute
        }
    }

    private static void appendSameSite(StringBuilder sb, Cookie.SameSiteOptions sameSite) {
        if (sameSite != null) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(SAME_SITE_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(sameSite.toString().toLowerCase(Locale.ENGLISH));
        }
    }

    private static String toCookieDate(Date date) {
        TimeZone tz = TimeZone.getTimeZone(GMT_TIME_ZONE_ID);
        DateFormat fmt = new SimpleDateFormat(COOKIE_DATE_FORMAT_STRING, Locale.US);
        fmt.setTimeZone(tz);
        return fmt.format(date);
    }


}
