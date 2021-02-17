package com.pdsu.csc.handler;

import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.config.dao.InitDao;
import com.pdsu.csc.config.dao.impl.PropertiesDefinition;
import com.pdsu.csc.exception.web.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Objects;

/**
 * @author 半梦
 * @create 2020-08-19 19:51
 *
 * 该类用于提供子类一些常用的常量, 错误提示
 */
@SuppressWarnings("all")
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
     * 邮箱为 null
     */
    protected static final String EMAIL_NOT_NULL = "邮箱不可为空";

    /**
     * 邮箱已被使用
     */
    protected static final String EMAIL_ALREADY_USE = "邮箱已被绑定";

    /**
     * 用户名为空
     */
    protected static final String USERNAME_NOT_NULL = "用户名不可为空";

    /**
     * 用户名已被使用
     */
    protected static final String USERNAME_ALREADY_USE = "用户名已被使用";

    /**
     * 账号已存在
     */
    protected static final String ACCOUND_ALREADY_USE = "该账号已存在,是否忘记密码?";

    /**
     * 博客
     */
    protected static final Integer BLOB = 1;

    /**
     * 文件
     */
    protected static final Integer FILE = 2;

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
    public static String blobImgFilePath = DEFAULT_BLOB_IMG_FILEPATH;

    /**
     * 用户上传资源储存地址
     */
    public static String fileFilePath = DEFAULT_FILE_FILEPATH;

    /**
     * 用户头像储存地址
     */
    public static String userImgFilePath = DEFAULT_USER_IMG_FILEPATH;

    /**
     * 文件带点后缀名
     * 例: .jpg
     */
    public static String imgSuffix = DEFAULT_IMG_SUFFIX;

    /**
     * 文件后缀名
     * 例: jpg
     */
    public static String imgSuffixExceptPoint = DEFAULT_IMG_SUFFIX_EXCEPT_POINT;

    /**
     * 用户默认头像名
     */
    public static String userImgName = DEFAULT_USER_IMG_NAME;

    /**
     * 默认访问人
     */
    public static final UserInformation DEFAULT_VISTOR = new UserInformation(0);

    /**
     * @param user 用户信息
     * @throws UserNotLoginException
     *  用户未登录时抛出对应异常
     */
     public void loginOrNotLogin(@Nullable UserInformation user) throws UserNotLoginException {
        if(Objects.isNull(user)) {
            throw new UserNotLoginException();
        }
    }

    static {
        InitSystem.initSystem();
    }

    @SuppressWarnings("all")
    private static class InitSystem implements InitDao {

        private InitDao initDao;

        public InitSystem(InitDao initDao) {
            this.initDao = initDao;
        }

        /**
         * 系统初始化
         */
        @Override
        public void initParameters() {
            log.info("系统初始化...初始化参数开始...");
            initDao.initParameters();
            log.info("系统初始化...初始化参数成功...");
        }

        /**
         * 创建程序运行的必要文件
         */
        @Override
        public void mkdirs(){
            log.info("系统初始化...创建系统所需文件开始...");
            initDao.mkdirs();
            log.info("系统初始化...创建系统所需文件成功...");
        }

        /**
         * 读取系统配置信息
         * @param filepath
         * @throws IOException
         * 系统配置初始化, 想要修改对应的信息时, 修改 csc.properties,
         * 详细信息请参考 classpath:properties/csc.properties
         */
        @Override
        public void reader(String filepath) throws IOException {
            log.info("系统初始化...读取系统配置信息开始...");
            initDao.reader(filepath);
            log.info("系统初始化...读取系统配置信息成功...");
        }

        public static void initSystem() {
            InitSystem initSystem = new InitSystem(new PropertiesDefinition());
            try {
                initSystem.reader("properties/csc.properties");
                initSystem.initParameters();
                initSystem.mkdirs();
            } catch (IOException e) {
                log.warn("系统初始化异常...使用默认参数..." + e);
            }
        }
    }

}
