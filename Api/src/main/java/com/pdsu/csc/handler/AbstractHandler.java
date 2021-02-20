package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import org.springframework.lang.Nullable;

/**
 * @author 半梦
 * @create 2021-02-20 20:53
 */
@SuppressWarnings("all")
public interface AbstractHandler {

    /**
     * 默认博客图片储存地址
     */
    String DEFAULT_BLOB_IMG_FILEPATH = "/pdsu/web/blob/img/";

    /**
     * 默认上传文件储存地址
     */
    String DEFAULT_FILE_FILEPATH = "/pdsu/web/file/";

    /**
     * 默认头像储存地址
     */
    String DEFAULT_USER_IMG_FILEPATH = "/pdsu/web/img/";

    /**
     * 默认图片后缀名
     */
    String DEFAULT_IMG_SUFFIX = ".jpg";

    /**
     * 默认图片后缀名
     */
    String DEFAULT_IMG_SUFFIX_EXCEPT_POINT = "jpg";

    /**
     * 默认头像
     */
    String DEFAULT_USER_IMG_NAME = "422696839bb3222a73a48d7c97b1bba4.jpg";

    /**
     * 是否有下一页的参数名
     */
    String HAS_NEXT_PAGE = "hasNextPage";

    /**
     * 默认通知用户名
     */
    String SYSTEM_NAME = "平顶山学院——信息工程学院";

    String DEFAULT_CHARACTER = "UTF-8";

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
     * 错误提示名
     */
    public static final String EXCEPTION = "exception";

    /**
     * 最大错误, 用于无需用户知道错误原因时
     */
    public static final String DEFAULT_ERROR_PROMPT = "未定义类型错误";

    /**
     * 用户未登录
     */
    public static final String NOT_LOGIN = "未登录";

    /**
     * 用户已登录
     */
    public static final String ALREADY_LOGIN = "已登录";

    /**
     * 验证码过期
     */
    public static final String CODE_EXPIRED = "验证码已过期, 请重新获取";

    /**
     * 验证码错误
     */
    public static final String CODE_ERROR = "验证码错误";

    /**
     * 常用于请求数据库失败时
     */
    public static final String NETWORK_BUSY = "网络繁忙, 请稍候重试";

    /**
     * 用户权限不足
     */
    public static final String INSUFFICIENT_PERMISSION = "权限不足";

    /**
     * 邮箱为 null
     */
    public static final String EMAIL_NOT_NULL = "邮箱不可为空";

    /**
     * 邮箱已被使用
     */
    public static final String EMAIL_ALREADY_USE = "邮箱已被绑定";

    /**
     * 用户名为空
     */
    public static final String USERNAME_NOT_NULL = "用户名不可为空";

    /**
     * 用户名已被使用
     */
    public static final String USERNAME_ALREADY_USE = "用户名已被使用";

    /**
     * 账号已存在
     */
    public static final String ACCOUND_ALREADY_USE = "该账号已存在,是否忘记密码?";


    /**
     * 广告预留
     * @return
     */
    default Result advertising() {
        return Result.bedRequest();
    }

    /**
     *  通知预留
     */
    default Result globalNotification() {
        return Result.bedRequest();
    }

    void loginOrNotLogin(@Nullable UserInformation user) throws UserNotLoginException;

}
