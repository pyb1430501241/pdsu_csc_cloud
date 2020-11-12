package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.WebInformation;
import com.pdsu.csc.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 半梦
 * @create 2020-11-10 17:40
 */
@RestController
@RequestMapping("/blob")
public class BlobHandler {

    @Autowired
    private ProviderService providerService;

    /**
     * 获取首页的数据
     * @return
     */
    @RequestMapping(value = "/getwebindex", method = RequestMethod.GET)
    @CrossOrigin
    public Result getWebIndex(@RequestParam(value = "p", defaultValue = "1") Integer p
            , @RequestParam(defaultValue = "0") Integer lid) {
        return providerService.getWebIndex(p, lid);
    }

    /**
     * 获取博客的相关信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{webid}", method = RequestMethod.GET)
    @CrossOrigin
    public Result toBlob(@PathVariable("webid")Integer id) {
        return providerService.getBlobInformation(id);
    }

    /**
     * 处理收藏请求
     * @param bid  作者的学号
     * @param webid 网页id
     * @return
     */
    @RequestMapping(value = "/collection", method = RequestMethod.POST)
    @CrossOrigin
    public Result collection(@RequestParam Integer bid, @RequestParam Integer webid) {
        return providerService.collection(bid, webid);
    }

    /**
     * 取消收藏请求
     * @param webid  网页ID
     * @return
     */
    @RequestMapping(value = "/decollection", method = RequestMethod.POST)
    @CrossOrigin
    public Result deCollection(@RequestParam Integer webid) {
        return providerService.deCollection(webid);
    }

    /**
     * 获取收藏状态
     * @param webid
     * @return
     */
    @GetMapping("/collectionstatuts")
    @ResponseBody
    public Result collectionStatus(@RequestParam Integer webid) {
        return providerService.collectionStatus(webid);
    }

    /**
     * 处理投稿请求
     * @param web, labelList
     * @return
     */
    @RequestMapping(value = "/contribution", method = RequestMethod.POST)
    @CrossOrigin
    public Result insert(@Valid WebInformation web, @RequestParam(required = false) List<Integer> labelList) {
        return providerService.insert(web, labelList);
    }

    /**
     * 删除文章
     * @param webid  文章id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @CrossOrigin
    public Result delete(@RequestParam Integer webid) {
        return providerService.delete(webid);
    }

    /**
     * 更新文章
     * @param web  更新后的文章
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @CrossOrigin
    public Result update(@Valid WebInformation web, @RequestParam(required = false)List<Integer> labelList) {
        return providerService.update(web, labelList);
    }

    /**
     * 处理评论请求
     * @param webid
     * @param content
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @CrossOrigin
    public Result postComment(@RequestParam Integer webid, @RequestParam String content) {
        return providerService.postComment(webid, content);
    }

    /**
     * 处理回复评论请求
     * @param cid
     * @param bid
     * @param content
     * @return
     */
    @RequestMapping(value = "/commentreply", method = RequestMethod.POST)
    @CrossOrigin
    public Result postCommentReply(@RequestParam Integer webid,
                                   @RequestParam Integer cid,
                                   @RequestParam Integer bid,
                                   @RequestParam String content) {
        return providerService.postCommentReply(webid, cid, bid, content);
    }

    /**
     * 获取作者信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getauthor", method = RequestMethod.GET)
    @CrossOrigin
    public Result getAuthorByUid(@RequestParam Integer uid) {
        return providerService.getAuthorByUid(uid);
    }

    /**
     * 获取用户收藏的文章
     * @param uid
     * @return
     */
    @GetMapping("/getcollection")
    @CrossOrigin
    public Result getCollectionByUid(@RequestParam Integer uid, @RequestParam(value = "p", defaultValue = "1")Integer p) {
        return providerService.getCollectionByUid(uid, p);
    }

    /**
     * 处理点赞请求
     */
    @RequestMapping(value = "/thumbs", method = RequestMethod.POST)
    @CrossOrigin
    public Result thumbs(@RequestParam Integer webid, @RequestParam Integer bid) {
        return providerService.thumbs(webid, bid);
    }

    /**
     * 处理取消点赞请求
     */
    @RequestMapping(value = "/dethumbs", method = RequestMethod.POST)
    @CrossOrigin
    public Result dethumbs(@RequestParam Integer webid) {
        return providerService.dethumbs(webid);
    }

    /**
     * 查询用户是否点赞
     * @param webid
     * @return
     */
    @RequestMapping(value = "/thumbsstatus", method = RequestMethod.GET)
    @CrossOrigin
    public Result thumbsStatus(@RequestParam Integer webid) {
        return providerService.thumbsStatus(webid);
    }

    /**
     * 处理博客页面图片
     * @param img
     * @return
     */
    @PostMapping(value = "/blobimg")
    @CrossOrigin
    public Result postBlobImg(@RequestParam MultipartFile img) {
        return providerService.postBlobImg(img);
    }

    /**
     * 获取文章标签
     * @return
     */
    @ResponseBody
    @GetMapping("/getlabel")
    public Result getLabel(@RequestParam(defaultValue = "1") Integer p) {
        return providerService.getLabel(p);
    }

    /**
     * 获取文章类型
     * @return
     */
    @GetMapping("/getcontype")
    @CrossOrigin
    public Result getContype() {
        return providerService.getContype();
    }

}
