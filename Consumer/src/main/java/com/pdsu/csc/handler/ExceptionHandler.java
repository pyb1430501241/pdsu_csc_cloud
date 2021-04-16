package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 半梦
 * @create 2021-02-22 12:13
 */
@ControllerAdvice
@ResponseBody
@SuppressWarnings("all")
public class ExceptionHandler extends AuthenticatedStorageHandler {

    private static final Logger log = LoggerFactory.getLogger("异常处理日志");

    /**
     * 处理 UserNotLoginException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotLoginException.class)
    public Result processUserNotLoginException(UserNotLoginException e) {
        log.info("使用某些功能时出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, NOT_LOGIN);
    }

    @Override
    public UserInformation compulsionGet(String sessionId) {
        return null;
    }

}
