package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 半梦
 * @create 2020-11-10 18:02
 */
@RestController
@RequestMapping("/file")
public class FileHandler {

    @Autowired
    private ProviderService providerService;

    /**
     *
     * @param file  上传的文件
     * @param title  文件名称
     * @param description  文件描述
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @CrossOrigin
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam String title,
                         @RequestParam String description) {
        return providerService.upload(file, title, description);
    }

    /**
     * 下载文件
     * @param uid 作者 uid
     * @param title 文件名
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @CrossOrigin
    public Result download(@RequestParam Integer uid, @RequestParam String title) {
        return providerService.download(uid, title);
    }

    /**
     *
     * @param p
     * @return
     */
    @GetMapping("/getfileindex")
    @CrossOrigin
    public Result getFileIndex(@RequestParam(defaultValue = "1") Integer p) {
        return providerService.getFileIndex(p);
    }

}
