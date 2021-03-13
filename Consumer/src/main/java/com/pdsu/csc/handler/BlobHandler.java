package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.bean.WebInformation;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import com.pdsu.csc.service.ProviderService;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 半梦
 * @create 2020-11-10 17:40
 */
@RestController
@RequestMapping("/blob")
@Log4j2
public class BlobHandler extends AuthenticatedStorageHandler {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取首页的数据
     * @return
     */
    @RequestMapping(value = "/getwebindex", method = RequestMethod.GET)
    public Result getWebIndex(@RequestParam(value = "p", defaultValue = "1") Integer p,
                              @RequestParam(defaultValue = "0") Integer lid, HttpServletRequest request) {
        return providerService.getWebIndex(p, lid);
    }

    private static final UserInformation DEFAULT_VISITOR = new UserInformation(181360226);

    /**
     * 获取博客的相关信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{webid}", method = RequestMethod.GET)
    public Result toBlob(@PathVariable("webid")Integer id, HttpServletRequest request) {
        UserInformation user = compulsionGet(request);
        if(user == null) {
            user = DEFAULT_VISITOR;
        }
        return providerService.getBlobInformation(id, user);
    }

    /**
     * 处理收藏请求
     * @param bid  作者的学号
     * @param webid 网页id
     * @return
     */
    @RequestMapping(value = "/collection", method = RequestMethod.POST)
    public Result collection(@RequestParam Integer bid, @RequestParam Integer webid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.collection(bid, webid, compulsionGet(request));
    }

    /**
     * 取消收藏请求
     * @param webid  网页ID
     * @return
     */
    @RequestMapping(value = "/decollection", method = RequestMethod.POST)
    public Result deCollection(@RequestParam Integer webid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.deCollection(webid, compulsionGet(request));
    }

    /**
     * 获取收藏状态
     * @param webid
     * @return
     */
    @GetMapping("/collectionstatuts")
    public Result collectionStatus(@RequestParam Integer webid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.collectionStatus(webid, compulsionGet(request));
    }

    /**
     * 处理投稿请求
     * @param web, labelList
     * @return
     */
    @RequestMapping(value = "/contribution", method = RequestMethod.POST)
    public Result insert(@Valid WebInformation web, @RequestParam(required = false) Integer[] labelList,
                         HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        Map<String, Object> values = new HashMap<>();
        values.put("user", compulsionGet(request));
        values.put("web", web);
        return providerService.insert(values, labelList);
    }

    /**
     * 删除文章
     * @param webid  文章id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestParam Integer webid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.delete(webid, compulsionGet(request));
    }

    /**
     * 更新文章
     * @param web  更新后的文章
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@Valid WebInformation web, @RequestParam(required = false) Integer [] labelList,
                         HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        Map<String, Object> values = new HashMap<>();
        values.put("web", web);
        values.put("user", compulsionGet(request));
        return providerService.update(values, labelList);
    }

    /**
     * 处理评论请求
     * @param webid
     * @param content
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Result postComment(@RequestParam Integer webid, @RequestParam String content, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.postComment(webid, content, compulsionGet(request));
    }

    /**
     * 处理回复评论请求
     * @param cid
     * @param bid
     * @param content
     * @return
     */
    @RequestMapping(value = "/commentreply", method = RequestMethod.POST)
    public Result postCommentReply(@RequestParam Integer webid,
                                   @RequestParam Integer cid,
                                   @RequestParam Integer bid,
                                   @RequestParam String content,
                                   HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.postCommentReply(webid, cid, bid, content, compulsionGet(request));
    }

    /**
     * 获取作者信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getauthor", method = RequestMethod.GET)
    public Result getAuthorByUid(@RequestParam Integer uid, HttpServletRequest request) {
        return providerService.getAuthorByUid(uid);
    }

    /**
     * 获取用户收藏的文章
     * @param uid
     * @return
     */
    @GetMapping("/getcollection")
    public Result getCollectionByUid(@RequestParam Integer uid,
                                     @RequestParam(value = "p", defaultValue = "1")Integer p,
                                     HttpServletRequest request) {
        return providerService.getCollectionByUid(uid, p);
    }

    /**
     * 处理点赞请求
     */
    @RequestMapping(value = "/thumbs", method = RequestMethod.POST)
    public Result thumbs(@RequestParam Integer webid, @RequestParam Integer bid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.thumbs(webid, bid, compulsionGet(request));
    }

    /**
     * 处理取消点赞请求
     */
    @RequestMapping(value = "/dethumbs", method = RequestMethod.POST)
    public Result dethumbs(@RequestParam Integer webid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.dethumbs(webid, compulsionGet(request));
    }

    /**
     * 查询用户是否点赞
     * @param webid
     * @return
     */
    @RequestMapping(value = "/thumbsstatus", method = RequestMethod.GET)
    public Result thumbsStatus(@RequestParam Integer webid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.thumbsStatus(webid, compulsionGet(request));
    }

    /**
     * 处理博客页面图片
     * @param img
     * @return
     */
    @PostMapping(value = "/blobimg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result postBlobImg(@RequestParam MultipartFile img, HttpServletRequest request) {
        return providerService.postBlobImg(img);
    }

    /**
     * 获取文章标签
     * @return
     */
    @GetMapping("/getlabel")
    public Result getLabel(@RequestParam(defaultValue = "1") Integer p, HttpServletRequest request) {
        return providerService.getLabel(p);
    }

    /**
     * 获取文章类型
     * @return
     */
    @GetMapping("/getcontype")
    public Result getContype(HttpServletRequest request) {
        return providerService.getContype();
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
