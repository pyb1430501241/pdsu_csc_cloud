package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author 半梦
 * @create 2020-11-09 19:04
 */
@RestController
@RequestMapping("/user")
public class UserHandler {

    @Autowired
    private ProviderService providerService;

    @PostMapping("/login")
    @CrossOrigin
    public Result login(@RequestParam String uid, @RequestParam String password,
                        @RequestParam String hit, @RequestParam String code,
                        @RequestParam(value = "flag", defaultValue = "0")Integer flag) {
        return providerService.login(uid, password, hit, code, flag);
    }

    @GetMapping("/getcodeforlogin")
    @CrossOrigin
    public Result getCodeForLogin() {
        return providerService.getCodeForLogin();
    }

    @RequestMapping(value = "/loginstatus", method = RequestMethod.GET)
    @CrossOrigin
    public Result getLoginStatus(){
        return providerService.loginStatus();
    }

    /**
     * 申请账号时发送邮箱验证码
     * @param email 前端输入邮箱
     * @param name  前端输入网名
     * @return
     * 	邮箱验证码所对应的 token
     */
    @RequestMapping(value = "/getcodeforapply", method = RequestMethod.GET)
    @CrossOrigin
    public Result sendEmailforApply(@RequestParam("email")String email, @RequestParam("name")String name) throws Exception {
        return providerService.getCodeFoyApply(email, name);
    }

    /**
     * 申请账号
     * @param user  POJO 类
     * @param token 获取验证码的 key
     * @param code  前端输入验证码
     * @return json字符串
     */
    @RequestMapping(value = "/applynumber", method = RequestMethod.POST)
    @CrossOrigin
    public Result applyforAccountNumber(@Valid UserInformation user,
                                        @RequestParam String token,
                                        @RequestParam String code){
        return providerService.applyUser(user, token, code);
    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    @CrossOrigin
    public Result getEmail(@RequestParam Integer uid) {
        return providerService.getEmailByUid(uid);
    }

    /**
     * 找回密码时发送验证码
     * @param token 前端传入
     * @return
     * 	验证码所对应的 token
     */
    @RequestMapping(value = "/getcodeforretrieve", method = RequestMethod.GET)
    @CrossOrigin
    public Result sendEmailForRetrieve(@RequestParam String token) {
        return providerService.getCodeForRetrieve(token);
    }

    /**
     * 找回密码
     * @param uid 账号
     * @param password 密码
     * @param token 验证码的 key
     * @param code 验证码
     * @return
     * 	是否修改成功
     */
    @RequestMapping(value = "/retrieve", method = RequestMethod.POST)
    @CrossOrigin
    public Result retrieveThePassword(@RequestParam Integer uid,
                                      @RequestParam String password,
                                      @RequestParam String token,
                                      @RequestParam String code) {
        return providerService.retrieveThePassword(uid, password, token, code);
    }

    /**
     * 获取修改密码页面数据
     * @return
     * 	邮箱
     */
    @RequestMapping(value = "/getmodify", method = RequestMethod.GET)
    @CrossOrigin
    public Result getModify() {
        return providerService.getModify();
    }

    /**
     * 获取验证码
     * @param email 前端获取
     * @return
     * 	验证码的 key
     */
    @RequestMapping(value = "/getcodeformodify", method = RequestMethod.GET)
    @CrossOrigin
    public Result sendEmailForModify(@RequestParam String email) {
        return providerService.getCodeForModify(email);
    }

    /**
     * 验证码验证
     * @param token 取出验证码的 key
     * @param code  前端输入验证码
     * @return
     * 	验证码是否正确
     */
    @RequestMapping(value = "/modifybefore", method = RequestMethod.GET)
    @CrossOrigin
    public Result modifyBefore(@RequestParam String token, @RequestParam String code) {
        return providerService.modifyBefore(token, code);
    }

    /**
     * 修改密码
     * @param password 新密码
     * @return
     * 	密码是否修改成功
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @CrossOrigin
    public Result modifyForPassword(@RequestParam String password) {
        return providerService.modifyForPassword(password);
    }

    /**
     * 处理关注请求, 作者的学号由前端获取, 关注人学号从session里获取
     *
     * @param uid  作者的学号
     * @return
     * 	是否关注成功
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @CrossOrigin
    public Result like(@RequestParam Integer uid) {
        return providerService.like(uid);
    }

    /**
     * 处理取消关注请求, 作者的学号由前端获取, 关注人学号从session里获取
     * @param uid 被关注人 uid
     * @return
     * 	是否取消成功
     */
    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    @CrossOrigin
    public Result disLike(@RequestParam Integer uid) {
        return providerService.disLike(uid);
    }

    /**
     * 关注状态
     * @param uid 被关注人学号
     * @return
     * 	是否关注
     */
    @GetMapping("/likestatus")
    @CrossOrigin
    public Result getLikeStatus(@RequestParam Integer uid) {
        return providerService.getLikeStatus(uid);
    }

    /**
     * 更换头像
     * @param img  上传头像文件
     * @return
     * 	修改成功：返回新的头像名
     * 	修改失败：返回原因
     */
    @RequestMapping(value = "/changeavatar", method = RequestMethod.POST)
    @CrossOrigin
    public Result updateImage(@RequestParam("img") MultipartFile img) {
        return providerService.updateImage(img);
    }

    /**
     * 修改用户信息
     * @param user 用户所需要修改的信息---
     * @return
     * 	是否修改成功
     */
    @RequestMapping(value = "/changeinfor", method = RequestMethod.POST)
    @CrossOrigin
    public Result updateUserInformation(UserInformation user) {
        return providerService.updateUserInformation(user);
    }

    /**
     * 获取自己的博客
     * @param p 第几页
     * @return
     * 	对应页面的文章信息
     */
    @RequestMapping(value = "/getoneselfblobs", method = RequestMethod.GET)
    @CrossOrigin
    public Result getOneselfBlobsByUid(@RequestParam(value = "p", defaultValue = "1")Integer p) {
        return providerService.getOneselfBlobsByUid(p);
    }

    /**
     * 获取自己的粉丝
     * @param p 第几页
     * @return
     * 	对应页面的粉丝信息
     */
    @CrossOrigin
    @GetMapping("/getoneselffans")
    public Result getOneselfFans(@RequestParam(value = "p", defaultValue = "1") Integer p) {
        return providerService.getOneselfFans(p);
    }

    /**
     * 获取自己的关注
     * @param p 第几页
     * @return
     * 	对应页面的关注人信息
     */
    @CrossOrigin
    @GetMapping("/getoneselficons")
    public Result getOneselfIcons(@RequestParam(value = "p", defaultValue = "1") Integer p) {
        return providerService.getOneselfIcons(p);
    }

    /**
     * 获取用户的博客
     * @param p 第几页
     * @param uid 用户 uid
     * @return
     * 	对应用户对应页面的文章信息
     */
    @RequestMapping(value = "/getblobs", method = RequestMethod.GET)
    @CrossOrigin
    public Result getBlobsByUid(@RequestParam(value = "p", defaultValue = "1")Integer p, @RequestParam Integer uid) {
        return providerService.getBlobsByUid(p, uid);
    }

    /**
     * 获取用户粉丝
     * @param p 第几页
     * @param uid 用户 uid
     * @return
     * 	对应用户对应页面的粉丝信息
     */
    @CrossOrigin
    @GetMapping("/getfans")
    public Result getFans(@RequestParam(value = "p", defaultValue = "1") Integer p, @RequestParam Integer uid) {
        return providerService.getFans(p, uid);
    }

    /**
     * 获取用户的关注
     * @param p 第几页
     * @param uid 用户 uid
     * @return
     * 	对应页面对应用户的关注人信息
     */
    @CrossOrigin
    @GetMapping("/geticons")
    public Result getIcons(@RequestParam(value = "p", defaultValue = "1") Integer p, @RequestParam Integer uid) {
        return providerService.getIcons(p, uid);
    }

    /**
     * 获取登录状态用户的邮箱
     * @return
     * 	当前登录用户的邮箱（已加密）
     */
    @RequestMapping("/loginemail")
    @CrossOrigin
    public Result getEmailByUid() {
        return providerService.getEmailByUid();
    }

    /**
     * 数据校验
     * @param data  需要校验的数据
     * @param type  数据校验的类型
     * 		取值为 4 种
     * 			1. uid 校验学号
     * 			2. username 校验用户名
     * 			3. password 校验密码
     * 			4. email 校验邮箱
     * @return
     * 	是否通过校验
     */
    @ResponseBody
    @GetMapping("/datacheck")
    @CrossOrigin
    public Result dataCheck(@RequestParam String data, @RequestParam String type) {
        return providerService.dataCheck(data, type);
    }

    /**
     * 获取自己的浏览记录
     * @param p 第几页
     * @return
     * 	浏览记录
     */
    @ResponseBody
    @GetMapping("/getoneselfbrowsingrecord")
    @CrossOrigin
    public Result getBrowsingRecord(@RequestParam(defaultValue = "1") Integer p) {
        return providerService.getBrowsingRecord(p);
    }

    /**
     * 获取自己的通知
     * @param p 第几页
     * @return
     * 	通知信息
     */
    @GetMapping("/getnotification")
    @ResponseBody
    @CrossOrigin
    public Result getOneselfNotification(@RequestParam(defaultValue = "1") Integer p) {
        return providerService.getOneselfNotification(p);
    }


}
