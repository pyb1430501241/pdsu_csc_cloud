package com.pdsu.csc.handler;

import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.config.dao.InitDao;
import com.pdsu.csc.config.dao.impl.PropertiesDefinition;
import com.pdsu.csc.exception.web.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 半梦
 * @create 2020-08-19 19:51
 *
 * 该类用于提供子类一些常用的常量, 错误提示
 */
@SuppressWarnings("all")
public abstract class InitHandler implements AbstractHandler, AuthenticationHandler {

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
    @Override
     public void loginOrNotLogin(@Nullable UserInformation user) throws UserNotLoginException {
        if(Objects.isNull(user)) {
            throw new UserNotLoginException();
        }
     }

    /**
     * 空方法
     */
    @Override
    public void loginOrNotLogin(@NonNull HttpServletRequest request) throws UserNotLoginException {
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
                synchronized (initSystem) {
                    initSystem.reader("properties/csc.properties");
                    initSystem.initParameters();
                    initSystem.mkdirs();
                }
            } catch (IOException e) {
                log.warn("系统初始化异常...使用默认参数..." + e);
            }
        }
    }

}
