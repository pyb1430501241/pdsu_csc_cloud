package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.exception.web.DeleteInforException;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.blob.RepetitionThumbsException;
import com.pdsu.csc.exception.web.blob.comment.NotFoundCommentIdException;
import com.pdsu.csc.exception.web.es.InsertException;
import com.pdsu.csc.exception.web.es.QueryException;
import com.pdsu.csc.exception.web.file.UidAndTitleRepetitionException;
import com.pdsu.csc.exception.web.user.*;
import com.pdsu.csc.exception.web.user.email.NotFoundEmailException;
import org.apache.commons.mail.EmailException;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author 半梦
 * @create 2020-11-09 15:06
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandler extends ParentHandler {

    private static final Logger log = LoggerFactory.getLogger("异常处理日志");

    /**
     * 处理 NotFoundBlobIdException 异常
     * @param e 异常名
     * @return
     *  错误信息
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundBlobIdException.class)
    public Result processNotFoundBlobIdException(NotFoundBlobIdException e) {
        log.info("文章相关操作时出现未知错误, 原因: " + e.getMessage());
        return Result.notFound().add(EXCEPTION, e.getMessage());
    }

    /**
     * 处理 UnsupportedEncodingException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(UnsupportedEncodingException.class)
    public Result processUnsupportedEncodingException(UnsupportedEncodingException e) {
        log.info("编码转换失败, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, DEFAULT_ERROR_PROMPT);
    }

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
        log.info("使用某些功能时出现未知错误, 原因: " + NOT_LOGIN);
        return Result.fail().add(EXCEPTION, NOT_LOGIN);
    }

    /**
     * 处理 UidAndWebIdRepetitionException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(UidAndWebIdRepetitionException.class)
    public Result processUidAndWebIdRepetitionException(UidAndWebIdRepetitionException e) {
        log.info("点赞和收藏出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, e.getMessage());
    }

    /**
     * 处理 DeleteInforException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(DeleteInforException.class)
    public Result processDeleteInforException(DeleteInforException e) {
        log.info("进行删除相关操作时出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, DEFAULT_ERROR_PROMPT);
    }

    /**
     * 处理 NotFoundCommentIdException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundCommentIdException.class)
    public Result processNotFoundCommentIdException(NotFoundCommentIdException e) {
        log.info("评论相关操作出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, e.getMessage());
    }

    /**
     * 处理 NotFoundUidException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundUidException.class)
    public Result processNotFoundUidException(NotFoundUidException e) {
        log.info("用户不存在");
        return Result.fail().add(EXCEPTION, e.getMessage());
    }

    /**
     * 处理 RepetitionThumbsException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(RepetitionThumbsException.class)
    public Result processRepetitionThumbsException(RepetitionThumbsException e) {
        log.info("用户点赞操作时出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, e.getMessage());
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
     * 处理 EmailException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(EmailException.class)
    public Result processEmailException(EmailException e) {
        log.info("发送邮箱验证码出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, "邮箱地址不正确");
    }

    /**
     * 处理 UidRepetitionException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(UidRepetitionException.class)
    public Result processUidRepetitionException(UidRepetitionException e) {
        log.info("用户申请账号时出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, "该学号已被注册");
    }

    /**
     * 处理 NotFoundEmailException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundEmailException.class)
    public Result processNotFoundEmailException(NotFoundEmailException e) {
        log.info("发送邮箱验证码出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, "未找到与该邮箱绑定的账号");
    }

    /**
     * 处理 UidAndLikeIdRepetitionException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(UidAndLikeIdRepetitionException.class)
    public Result processUidAndLikeIdRepetitionException(UidAndLikeIdRepetitionException e) {
        log.info("用户进行关注相关操作时出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, e.getMessage());
    }

    /**
     * 处理 NotFoundUidAndLikeIdException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundUidAndLikeIdException.class)
    public Result processNotFoundUidAndLikeIdException(NotFoundUidAndLikeIdException e) {
        log.info("用户进行关注相关操作时出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, e.getMessage());
    }

    /**
     * 处理 QueryException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(QueryException.class)
    public Result processQueryException(QueryException e) {
        log.info("ES进行查询时出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, NETWORK_BUSY);
    }

    /**
     * 处理 UidAndTitleRepetitionException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(UidAndTitleRepetitionException.class)
    public Result processUidAndTitleRepetitionException(UidAndTitleRepetitionException e) {
        log.info("用户上传资源时出现未知错误, 原因: " + e.getMessage());
        return Result.fail().add(EXCEPTION, "无法上传同名文件, 请修改名称");
    }

    /**
     * 处理 InsertException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(InsertException.class)
    public Result processInsertException(InsertException e) {
        log.info("ES进行插入操作时出现未知错误, 原因: " + e.getMessage());
        return Result.success().add(e.getName(), e.getId());
    }

    /**
     * 处理 MethodArgumentTypeMismatchException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
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
     * 处理 BindException 异常
     * @ResponseStatus(HttpStatus.BAD_REQUEST)
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
    public Result processMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("请求API时发生未知错误, 原因: " + e.getMessage());
        return Result.bedRequest().add(EXCEPTION, "参数类型错误");
    }

    /**
     * 处理 BindException 异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public Result processMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.info("请求API时发生未知错误, 原因: " + e.getMessage());
        return Result.bedRequest().add(EXCEPTION, "无效的请求地址或参数错误");
    }

    @Override
    public Result advertising() {
        return null;
    }
}
