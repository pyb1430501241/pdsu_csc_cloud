package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import com.pdsu.csc.service.ProviderService;
import com.pdsu.csc.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 半梦
 * @create 2020-11-10 18:02
 */
@RestController
@RequestMapping("/file")
public class FileHandler extends AuthenticatedStorageHandler{

    @Autowired
    private ProviderService providerService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     *
     * @param file  上传的文件
     * @param title  文件名称
     * @param description  文件描述
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam String title,
                         @RequestParam String description,
                         HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.upload(file, title, description, compulsionGet(request));
    }

    /**
     * 下载文件
     * @param uid 作者 uid
     * @param title 文件名
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public Result download(@RequestParam Integer uid, @RequestParam String title,
                           HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.download(uid, title, compulsionGet(request));
    }

    /**
     *
     * @param p
     * @return
     */
    @GetMapping("/getfileindex")
    public Result getFileIndex(@RequestParam(defaultValue = "1") Integer p, HttpServletRequest request) {
        return providerService.getFileIndex(p);
    }

    @Override
    public UserInformation compulsionGet(String sessionId) {
        if(!contains(sessionId)) {
            UserInformation user = (UserInformation) redisUtils.get(sessionId);
            add(sessionId, user);
        }
        return get(sessionId);
    }

}
