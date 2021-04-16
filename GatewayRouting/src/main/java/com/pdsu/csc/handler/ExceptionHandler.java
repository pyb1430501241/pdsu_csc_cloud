package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserAbnormalException;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 半梦
 * @create 2021-02-21 17:00
 */
@ControllerAdvice
@ResponseBody
@SuppressWarnings("all")
public class ExceptionHandler implements AbstractHandler {

    private static final Logger log = LoggerFactory.getLogger("异常处理日志");


    /**
     * 处理 Exception 异常, 优先级最低
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result processException(Exception e) {
        log.error("项目运行发生未知错误, 原因: " + e);
        return Result.fail().add(EXCEPTION, DEFAULT_ERROR_PROMPT);
    }

    /**
     * 处理 UserNotLoginException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotLoginException.class)
    public Result processUserNotLoginException(UserNotLoginException e) {
        log.error("使用某些功能时出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, NOT_LOGIN);
    }

    /**
     * 处理 IOException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
    public Result processIOException(IOException e) {
        log.error("文件操作出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, DEFAULT_ERROR_PROMPT);
    }

    /**
     * 处理 AuthenticationException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public Result processAuthenticationException(AuthenticationException e) {
        if(e instanceof IncorrectCredentialsException) {
            log.info("用户登录时出现未知错误, 原因: 账号或密码错误");
            return Result.fail().add(EXCEPTION, "账号或密码错误");
        } else if (e instanceof UnknownAccountException) {
            log.info("用户登录时出现未知错误, 原因: 账号不存在");
            return Result.fail().add(EXCEPTION, "账号不存在");
        } else if (e instanceof UserAbnormalException) {
            log.info("用户登录时出现未知错误, 原因: " + e.getMessage());
            return Result.fail().add(EXCEPTION, e.getMessage());
        }
        log.info("用户登录时出现未知错误, 原因: " + e.getMessage());
        SecurityUtils.getSubject().logout();
        return Result.fail().add(EXCEPTION, DEFAULT_ERROR_PROMPT);
    }

    /**
     * 处理 BindException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
    public Result processBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = "";
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMessage += error.getDefaultMessage() + " ";

        }
        log.info("请求API时, 参数不符合规范, 原因: " + errorMessage);
        return Result.bedRequest().add(EXCEPTION, errorMessage);
    }

    /**
     * 处理 MethodArgumentTypeMismatchException 异常
     * @ResponseStatus(HttpStatus.BAD_REQUEST)
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result processMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("请求API时发生未知错误, 原因: " + e.getMessage());
        return Result.bedRequest().add(EXCEPTION, "参数类型错误");
    }

    /**
     * 处理 MissingServletRequestParameterException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public Result processMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.info("请求API时发生未知错误, 原因: " + e.getMessage());
        return Result.bedRequest().add(EXCEPTION, "无效的请求地址或参数错误");
    }

}
