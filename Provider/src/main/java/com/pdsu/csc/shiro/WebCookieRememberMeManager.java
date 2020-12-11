package com.pdsu.csc.shiro;

import com.pdsu.csc.utils.HttpUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author 半梦
 * @create 2020-12-11 16:02
 */
@Log4j2
public class WebCookieRememberMeManager extends CookieRememberMeManager {

    protected void rememberSerializedIdentity(Subject subject, byte[] serialized) {
        if (!WebUtils.isHttp(subject)) {
            return;
        }
        HttpServletRequest request = WebUtils.getHttpRequest(subject);
        HttpServletResponse response = WebUtils.getHttpResponse(subject);
        //base 64 encode it and store as a cookie:
        String base64 = Base64.encodeToString(serialized);
        Cookie template = getCookie(); //the class attribute is really a template for the outgoing cookies
        Cookie cookie = new SimpleCookie(template);
        cookie.setValue(base64);

        /*
            获取 cookie 的各种属性
         */
        String name = cookie.getName();
        String value = cookie.getValue();
        String comment = cookie.getComment();
        String domain = cookie.getDomain();
        String path = calculatePath(request);
        int maxAge = cookie.getMaxAge();
        int version = cookie.getVersion();
        boolean secure = cookie.isSecure();
        boolean httpOnly = cookie.isHttpOnly();
        Cookie.SameSiteOptions sameSite = cookie.getSameSite();

        /*
            讲 cookie 封装程对应的信息
         */
        String endValue = buildHeaderValue(name, value, comment, domain, path, maxAge, version, secure, httpOnly, sameSite);

        /*
            讲其添加到请求头上
         */
        response.addHeader(HttpUtils.getRememberCookieName(), endValue);
    }

    private String calculatePath(HttpServletRequest request) {
        String path = StringUtils.clean(getCookie().getPath());
        if (!StringUtils.hasText(path)) {
            path = StringUtils.clean(request.getContextPath());
        }

        if (path == null) {
            path = SimpleCookie.ROOT_PATH;
        }
        return path;
    }

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
    private static final int DEFAULT_VERSION = -1;
    private static final String GMT_TIME_ZONE_ID = "GMT";
    private static final String COOKIE_DATE_FORMAT_STRING = "EEE, dd-MMM-yyyy HH:mm:ss z";

    private String buildHeaderValue(String name, String value, String comment,
                                      String domain, String path, int maxAge, int version,
                                      boolean secure, boolean httpOnly, Cookie.SameSiteOptions sameSite) {

        if (!StringUtils.hasText(name)) {
            throw new IllegalStateException("Cookie name cannot be null/empty.");
        }

        StringBuilder sb = new StringBuilder(name).append(NAME_VALUE_DELIMITER);

        if (StringUtils.hasText(value)) {
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

    private void appendComment(StringBuilder sb, String comment) {
        if (StringUtils.hasText(comment)) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(COMMENT_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(comment);
        }
    }

    private void appendDomain(StringBuilder sb, String domain) {
        if (StringUtils.hasText(domain)) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(DOMAIN_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(domain);
        }
    }

    private void appendPath(StringBuilder sb, String path) {
        if (StringUtils.hasText(path)) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(PATH_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(path);
        }
    }

    private void appendExpires(StringBuilder sb, int maxAge) {
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

    private void appendVersion(StringBuilder sb, int version) {
        if (version > DEFAULT_VERSION) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(VERSION_ATTRIBUTE_NAME).append(NAME_VALUE_DELIMITER).append(version);
        }
    }

    private void appendSecure(StringBuilder sb, boolean secure) {
        if (secure) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(SECURE_ATTRIBUTE_NAME); //No value for this attribute
        }
    }

    private void appendHttpOnly(StringBuilder sb, boolean httpOnly) {
        if (httpOnly) {
            sb.append(ATTRIBUTE_DELIMITER);
            sb.append(HTTP_ONLY_ATTRIBUTE_NAME); //No value for this attribute
        }
    }

    private void appendSameSite(StringBuilder sb, Cookie.SameSiteOptions sameSite) {
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
