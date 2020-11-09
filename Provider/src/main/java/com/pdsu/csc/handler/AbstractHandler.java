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
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author 半梦
 * @create 2020-08-29 15:19
 * 该接口提供一些系统所需的默认参数
 */
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

}
