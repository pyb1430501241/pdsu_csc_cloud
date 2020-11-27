/*
很难优化吧？
 */
package com.pdsu.csc.handler;

import com.pdsu.csc.bean.HandlerValueEnum;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.*;
import com.pdsu.csc.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Properties;

/**
 * @author 半梦
 * @create 2020-08-19 19:51
 *
 * 该类用于提供子类一些常用的常量, 错误提示, 异常处理方案的实现
 */
public abstract class ParentHandler implements AbstractHandler {

    /**
     * 错误提示名
     */
    protected static final String EXCEPTION = "exception";

    /**
     * 最大错误, 用于无需用户知道错误原因时
     */
    protected static final String DEFAULT_ERROR_PROMPT = "未定义类型错误";

    /**
     * 用户未登录
     */
    protected static final String NOT_LOGIN = "未登录";

    /**
     * 用户已登录
     */
    protected static final String ALREADY_LOGIN = "已登录";

    /**
     * 验证码过期
     */
    protected static final String CODE_EXPIRED = "验证码已过期, 请重新获取";

    /**
     * 验证码错误
     */
    protected static final String CODE_ERROR = "验证码错误";

    /**
     * 常用于请求数据库失败时
     */
    protected static final String NETWORK_BUSY = "网络繁忙, 请稍候重试";

    /**
     * 用户权限不足
     */
    protected static final String INSUFFICIENT_PERMISSION = "权限不足";

    /**
     * 博客
     */
    public static final Integer BLOB = 1;

    /**
     * 文件
     */
    public static final Integer FILE = 2;

    /**
     * 系统通知已读
     */
    public static final Integer SYSTEM_NOTIFICATION_READ = 2;

    /**
     * 系统通知未读
     */
    public static final Integer SYSTEM_NOTIFICATION_UNREAD = 1;

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger("初始化日志");

    /**
     * 博客页面图片储存地址
     */
    protected static String Blob_Img_FilePath = DEFAULT_BLOB_IMG_FILEPATH;

    /**
     * 用户上传资源储存地址
     */
    protected static String File_FilePath = DEFAULT_FILE_FILEPATH;

    /**
     * 用户头像储存地址
     */
    protected static String User_Img_FilePath = DEFAULT_USER_IMG_FILEPATH;

    /**
     * 文件带点后缀名
     * 例: .jpg
     */
    protected static String Img_Suffix = DEFAULT_IMG_SUFFIX;

    /**
     * 文件后缀名
     * 例: jpg
     */
    protected static String Img_Suffix_Except_Point = DEFAULT_IMG_SUFFIX_EXCEPT_POINT;

    /**
     * 用户默认头像名
     */
    protected static String Default_User_Img_Name = DEFAULT_USER_IMG_NAME;

    /**
     *
     * @param user 用户信息
     * @throws UserNotLoginException
     *  用户未登录时抛出对应异常
     */
    protected void loginOrNotLogin(UserInformation user) throws UserNotLoginException {
        if(Objects.isNull(user)) {
            throw new UserNotLoginException();
        }
    }

    static {
        InitSystem.init();
    }

    @SuppressWarnings("all")
    private static class InitSystem {
        /**
         * 系统配置初始化, 想要修改对应的信息时, 修改 csc.properties,
         * 详细信息请参考 classpath:properties/csc.properties
         */
        private static void initProperties() {
            log.info("系统配置初始化...");
            Properties properties = new Properties();
            try {
                ClassLoader classLoader = ParentHandler.class.getClassLoader();
                InputStream in = classLoader.getResourceAsStream("properties/csc.properties");
                properties.load(in);
                Enumeration enumeration = properties.propertyNames();
                while (enumeration.hasMoreElements()) {
                    String key = (String) enumeration.nextElement();
                    String value = properties.getProperty(key);
                    switch (HandlerValueEnum.getByKey(key)) {
                        case IMG_SUFFIX :
                            Img_Suffix = value;
                            Img_Suffix_Except_Point = StringUtils.getSuffixNameExceptPoint(value);
                            break;
                        case FILE_FILEPATH :
                            File_FilePath = value;
                            break;
                        case USER_IMG_FILEPATH:
                            User_Img_FilePath = value;
                            break;
                        case BLOB_IMG_FILEPATH:
                            Blob_Img_FilePath = value;
                            break;
                        case USER_IMG_NAME:
                            Default_User_Img_Name = value;
                            break;
                        default:
                    }
                }
            } catch (IOException e) {
                log.warn("初始化配置失败...", e);
            }
            log.info("系统初始化成功...");
        }

        /**
         * 创建程序运行的必要文件
         */
        private static void mkdirs(){
            log.info("创建系统所需文件...");
            File file = new File(User_Img_FilePath);
            if(!file.exists()) {
                file.mkdirs();
            }
            file = new File(Blob_Img_FilePath);
            if(!file.exists()) {
                file.mkdirs();
            }
            file = new File(File_FilePath);
            if(!file.exists()) {
                file.mkdirs();
            }
            log.info("文件创建成功...");
        }

        public static void init() {
            initProperties();
            mkdirs();
        }
    }

}
