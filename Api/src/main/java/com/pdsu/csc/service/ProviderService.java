package com.pdsu.csc.service;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.bean.WebInformation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


/**
 * @author 半梦
 * @create 2020-11-09 19:06
 * 使用 Feign 做负载均衡和容错
 */
@FeignClient(value = "CODESHARINGCOMMUNITYPROVIDER")
@Service
public interface ProviderService {

    String USER_API_PREFIX = "/user/";

    String BLOB_API_PREFIX = "/blob/";

    String FILE_API_PREFIX = "/file/";

    /**
     * 登录
     */
    @GetMapping(value = USER_API_PREFIX + "login")
    public Result login(@RequestParam("uid") String uid, @RequestParam("password") String password,
                        @RequestParam("hit") String hit, @RequestParam("code") String code,
                        @RequestParam(value = "flag", defaultValue = "0")Integer flag);

    /**
     *  获取验证码
     */
    @GetMapping(USER_API_PREFIX + "getcodeforlogin")
    public Result getCodeForLogin();

    /**
     * 申请账号时获取邮箱验证码
     */
    @GetMapping(USER_API_PREFIX + "getcodeforapply")
    public Result getCodeFoyApply(@RequestParam(value = "email")String email, @RequestParam("name")String name);

    /**
     * 申请账号
     */
    @PostMapping(USER_API_PREFIX + "applynumber")
    public Result applyUser(UserInformation user, @RequestParam("token") String token, @RequestParam("code") String code);

    /**
     * 登录状态
     */
    @GetMapping(USER_API_PREFIX + "loginstatus")
    public Result loginStatus();

    /**
     * 根据账号获取邮箱
     */
    @GetMapping(USER_API_PREFIX + "isexist")
    public Result getEmailByUid(@RequestParam("uid") Integer uid);

    /**
     * 根据 token 获取邮箱验证码
     */
    @GetMapping(USER_API_PREFIX + "getcodeforretrieve")
    public Result getCodeForRetrieve(@RequestParam("token") String token);

    /**
     * 修改密码
     */
    @PostMapping(USER_API_PREFIX + "retrieve")
    public Result retrieveThePassword(@RequestParam("uid") Integer uid,
                                      @RequestParam("password") String password,
                                      @RequestParam("token") String token,
                                      @RequestParam("code") String code);

    /**
     * 获取修改密码页面数据
     */
    @GetMapping(USER_API_PREFIX + "getmodify")
    public Result getModify();

    /**
     * 获取修改密码的验证码
     */
    @GetMapping(USER_API_PREFIX + "getcodeformodify")
    public Result getCodeForModify(@RequestParam("email") String email);

    /**
     * 验证验证码
     */
    @GetMapping(USER_API_PREFIX + "/modifybefore")
    public Result modifyBefore(@RequestParam("token") String token, @RequestParam("code") String code);

    /**
     * 修改密码
     */
    @PostMapping(USER_API_PREFIX + "modify")
    public Result modifyForPassword(@RequestParam("password") String password);

    /**
     * 关注
     */
    @PostMapping(USER_API_PREFIX + "like")
    public Result like(@RequestParam("uid") Integer uid);

    /**
     * 取消关注
     */
    @PostMapping(USER_API_PREFIX + "dislike")
    public Result disLike(@RequestParam("uid") Integer uid);

    /**
     * 关注状态
     */
    @GetMapping(USER_API_PREFIX + "likestatus")
    public Result getLikeStatus(@RequestParam("uid") Integer uid);

    /**
     * 更换头像
     */
    @PostMapping(USER_API_PREFIX + "changeavatar")
    public Result updateImage(@RequestParam("img") MultipartFile img);

    /**
     * 更新用户信息
     */
    @PostMapping(USER_API_PREFIX + "changeinfor")
    public Result updateUserInformation(UserInformation user);

    /**
     * 获取自己的博客
     */
    @GetMapping(USER_API_PREFIX + "getoneselfblobs")
    public Result getOneselfBlobsByUid(@RequestParam(value = "p", defaultValue = "1")Integer p);

    /**
     * 获取自己的粉丝
     */
    @GetMapping(USER_API_PREFIX + "getoneselffans")
    public Result getOneselfFans(@RequestParam(value = "p", defaultValue = "1") Integer p);

    /**
     * 获取自己的关注
     */
    @GetMapping(USER_API_PREFIX + "getoneselficons")
    public Result getOneselfIcons(@RequestParam(value = "p", defaultValue = "1") Integer p);

    /**
     * 根据账号获取文章
     */
    @GetMapping(USER_API_PREFIX + "getblobs")
    public Result getBlobsByUid(@RequestParam(value = "p", defaultValue = "1")Integer p, @RequestParam("uid") Integer uid);

    /**
     * 根据学号获取粉丝
     */
    @GetMapping(USER_API_PREFIX + "getfans")
    public Result getFans(@RequestParam(value = "p", defaultValue = "1") Integer p, @RequestParam("uid") Integer uid);

    /**
     * 根据学号关注
     */
    @GetMapping(USER_API_PREFIX + "geticons")
    public Result getIcons(@RequestParam(value = "p", defaultValue = "1") Integer p, @RequestParam("uid") Integer uid);

    /**
     * 获取登录状态的邮箱
     */
    @GetMapping(USER_API_PREFIX + "loginemail")
    public Result getEmailByUid();

    /**
     * 数据校验
     */
    @GetMapping(USER_API_PREFIX + "/datacheck")
    public Result dataCheck(@RequestParam("data") String data, @RequestParam("type") String type);

    /**
     * 获取用户浏览记录
     */
    @GetMapping(USER_API_PREFIX + "getoneselfbrowsingrecord")
    public Result getBrowsingRecord(@RequestParam(value = "p", defaultValue = "1") Integer p);

    /**
     * 获取用户通知
     */
    @GetMapping(USER_API_PREFIX + "getnotification")
    public Result getOneselfNotification(@RequestParam(value = "p", defaultValue = "1") Integer p);

    /**
     * 获取博客首页
     */
    @GetMapping(BLOB_API_PREFIX + "getwebindex")
    public Result getWebIndex(@RequestParam(value = "p", defaultValue = "1") Integer p
            , @RequestParam(value = "lid",defaultValue = "0") Integer lid);

    /**
     * 获取博客页面信息
     */
    @GetMapping(BLOB_API_PREFIX + "/{webid}")
    public Result getBlobInformation(@PathVariable("webid")Integer id);

    /**
     * 收藏文章
     */
    @PostMapping(BLOB_API_PREFIX + "collection")
    public Result collection(@RequestParam("bid") Integer bid, @RequestParam("webid") Integer webid);

    /**
     * 取消收藏
     */
    @PostMapping(BLOB_API_PREFIX + "decollection")
    public Result deCollection(@RequestParam("webid") Integer webid);

    /**
     *  收藏状态
     */
    @GetMapping(BLOB_API_PREFIX + "collectionstatuts")
    public Result collectionStatus(@RequestParam("webid") Integer webid);

    /**
     * 发布文章
     */
    @PostMapping(BLOB_API_PREFIX + "contribution")
    public Result insert(WebInformation web, @RequestParam(value = "labelList", required = false) List<Integer> labelList);

    /**
     * 删除文章
     */
    @PostMapping(BLOB_API_PREFIX + "delete")
    public Result delete(@RequestParam("webid") Integer webid);

    /**
     * 更新文章
     */
    @PostMapping(BLOB_API_PREFIX + "update")
    public Result update(WebInformation web, @RequestParam(value = "labelList", required = false)List<Integer> labelList);

    /**
     * 发布评论
     */
    @PostMapping(BLOB_API_PREFIX + "comment")
    public Result postComment(@RequestParam("webid") Integer webid, @RequestParam("content") String content);

    /**
     * 回复评论
     */
    @PostMapping(BLOB_API_PREFIX + "commentreply")
    public Result postCommentReply(@RequestParam("webid") Integer webid,
                                   @RequestParam("cid") Integer cid,
                                   @RequestParam("bid") Integer bid,
                                   @RequestParam("content") String content);

    /**
     * 获取作者信息
     */
    @GetMapping(BLOB_API_PREFIX + "getauthor")
    public Result getAuthorByUid(@RequestParam("uid") Integer uid, HttpServletRequest request);

    /**
     * 获取收藏
     */
    @GetMapping(BLOB_API_PREFIX + "getcollection")
    public Result getCollectionByUid(@RequestParam("uid") Integer uid, @RequestParam(value = "p", defaultValue = "1")Integer p);

    /**
     * 点赞
     */
    @PostMapping(BLOB_API_PREFIX + "thumbs")
    public Result thumbs(@RequestParam("webid") Integer webid, @RequestParam("bid") Integer bid);

    /**
     * 取消点赞
     */
    @PostMapping(BLOB_API_PREFIX + "dethumbs")
    public Result dethumbs(@RequestParam("webid") Integer webid);

    /**
     * 点赞状态
     */
    @GetMapping(BLOB_API_PREFIX + "thumbsstatus")
    public Result thumbsStatus(@RequestParam("webid") Integer webid);

    /**
     * 博客页面上传图片
     */
    @PostMapping(BLOB_API_PREFIX + "blobimg")
    public Result postBlobImg(@RequestParam("img") MultipartFile img);

    /**
     * 获取文章标签
     */
    @GetMapping(BLOB_API_PREFIX + "getlabel")
    public Result getLabel(@RequestParam(value = "p", defaultValue = "1") Integer p);

    /**
     * 获取文章类型
     */
    @GetMapping(BLOB_API_PREFIX + "getcontype")
    public Result getContype();

    /**
     * 上传文件
     */
    @PostMapping(FILE_API_PREFIX + "upload")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
                         @RequestParam("description") String description);


    /**
     * 下载文件
     */
    @GetMapping(FILE_API_PREFIX + "download")
    public void download(@RequestParam("uid") Integer uid, @RequestParam("title") String title, HttpServletResponse response);

    /**
     *  获取文件首页
     */
    @GetMapping(FILE_API_PREFIX + "getfileindex")
    public Result getFileIndex(@RequestParam(value = "p", defaultValue = "1") Integer p);

    @GetMapping("/index")
    public Result index();

    /**
     * 查询
     */
    @GetMapping("/search")
    public Result searchByText(@RequestParam(value = "p")String text);
}
