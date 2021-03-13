package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import com.pdsu.csc.service.ProviderService;
import com.pdsu.csc.utils.DateUtils;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.RedisUtils;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 半梦
 * @create 2020-11-09 19:04
 */
@RestController
@RequestMapping("/user")
public class UserHandler extends AuthenticatedStorageHandler{

    @Autowired
    private ProviderService providerService;

    @Autowired
    private RedisUtils redisUtils;

    private static final String CODE = "code";
    private static final Integer SUCCESS_CODE = 200;

    private boolean isHttpSuccess(Result result) {
        return result.getJson().get(CODE).equals(SUCCESS_CODE);
    }

//    @PostMapping("/login")
//    
//    public Result login(@RequestParam String uid, @RequestParam String password,
//                        @RequestParam String hit, @RequestParam String code,
//                        @RequestParam(value = "flag", defaultValue = "0")Integer flag,
//                        HttpServletResponse response,
//                        HttpServletRequest request) {
//        Result login = providerService.login(uid, password, hit, code, flag);
//        // 如果请求成功
//        if(isHttpSuccess(login)) {
//            /*
//                由于 feign 转发请求时会默认清理 response 里的 Cookie, 请求头信息,
//                故选择将其添加到服务提供放的返回值里, 供消费方拿到后, 添加到请求头里。
//                    // 如在本项目中认证, 通过对应的参数进行调用, 则省去该步骤
//                    // 但由于架子已成, 如修改, 幅度巨大, 故放弃。
//            */
//            String setCookieName = HttpUtils.getSetCookieName();
//            String setCookieValue = (String) login.getJson().get(setCookieName);
//            response.addHeader(setCookieName, setCookieValue);
//            login.getJson().remove(setCookieName);
//
//            String rememberName = HttpUtils.getRememberCookieName();
//            String rememberValue = (String) login.getJson().get(rememberName);
//            // 用户可能选择不记住
//            if(!StringUtils.isBlank(rememberValue)) {
//                // setCookieName 为给浏览器添加 cookie
//                response.addHeader(setCookieName, rememberValue);
//            }
//            login.getJson().remove(rememberName);
//        }
//        return login;
//    }

    @GetMapping("/getcodeforlogin")
    public Result getCodeForLogin(HttpServletRequest request) {
        return providerService.getCodeForLogin();
    }

//    @RequestMapping(value = "/loginstatus", method = RequestMethod.GET)
//    public Result getLoginStatus(HttpServletRequest request, HttpServletResponse response){
//        Result result = providerService.loginStatus();
//        // 如果请求成功, 由于用户未登录时, 返回值 code 为500, 即代表请求成功时
//        // cookie 中必有 Authorization 或 rememberMe
//        if(isHttpSuccess(result)) {
//            // 由于 rememberMe 认证的用户, sessionId 丢失, 故在第一个请求中加入 sessionId
//            Cookie[] cookies = request.getCookies();
//            boolean f = true;
//            // 查询用户是否已认证, 即为已登录状态
//            for(Cookie cookie : cookies) {
//                // 如果 cookie 里有 Authorization, 则 f 为 false
//                if(cookie.getName().equals(HttpUtils.getSessionHeader())) {
//                    f = false;
//                    break;
//                }
//            }
//            String sessionHeader = HttpUtils.getSessionHeader();
//            // 如果有 cookie 中没有 Authorization, 则获取并添加进 cookie
//            if(f) {
//                String setCookieName = HttpUtils.getSetCookieName();
//                String sessionIdValue = (String) result.getJson().get(sessionHeader);
//                // Authorization 不为空时
//                if(!StringUtils.isBlank(sessionIdValue)) {
//                    String path = "/";
//                    String endValue = CookieUtils.buildHeaderValue(sessionHeader, sessionIdValue, null,
//                            null, path, -1, CookieUtils.DEFAULT_VERSION, false, false,
//                            org.apache.shiro.web.servlet.Cookie.SameSiteOptions.LAX);
//                    response.addHeader(setCookieName, endValue);
//                }
//            }
//            result.getJson().remove(sessionHeader);
//        }
//        return result;
//    }

    /**
     * 申请账号时发送邮箱验证码
     * @param email 前端输入邮箱
     * @param name  前端输入网名
     * @return
     * 	邮箱验证码所对应的 token
     */
    @RequestMapping(value = "/getcodeforapply", method = RequestMethod.GET)
    public Result sendEmailforApply(@RequestParam("email")String email,
                                    @RequestParam("name")String name,
                                    HttpServletRequest request) throws Exception {
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
    public Result applyforAccountNumber(@Valid UserInformation user,
                                        @RequestParam String token,
                                        @RequestParam String code,
                                        HttpServletRequest request){
        return providerService.applyUser(user, token, code);
    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public Result getEmail(@RequestParam Integer uid, HttpServletRequest request) {
        return providerService.getEmailByUid(uid);
    }

    /**
     * 找回密码时发送验证码
     * @param token 前端传入
     * @return
     * 	验证码所对应的 token
     */
    @RequestMapping(value = "/getcodeforretrieve", method = RequestMethod.GET)
    public Result sendEmailForRetrieve(@RequestParam String token, HttpServletRequest request) {
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
    public Result retrieveThePassword(@RequestParam Integer uid,
                                      @RequestParam String password,
                                      @RequestParam String token,
                                      @RequestParam String code,
                                      HttpServletRequest request) {
        return providerService.retrieveThePassword(uid, password, token, code);
    }

    /**
     * 获取修改密码页面数据
     * @return
     * 	邮箱
     */
    @RequestMapping(value = "/getmodify", method = RequestMethod.GET)
    public Result getModify(HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.getModify(compulsionGet(request));
    }

    /**
     * 获取验证码
     * @param email 前端获取
     * @return
     * 	验证码的 key
     */
    @RequestMapping(value = "/getcodeformodify", method = RequestMethod.GET)
    public Result sendEmailForModify(@RequestParam String email, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.getCodeForModify(email, compulsionGet(request));
    }

    /**
     * 验证码验证
     * @param token 取出验证码的 key
     * @param code  前端输入验证码
     * @return
     * 	验证码是否正确
     */
    @RequestMapping(value = "/modifybefore", method = RequestMethod.GET)
    public Result modifyBefore(@RequestParam String token,
                               @RequestParam String code,
                               HttpServletRequest request) {
        return providerService.modifyBefore(token, code);
    }

    /**
     * 修改密码
     * @param password 新密码
     * @return
     * 	密码是否修改成功
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Result modifyForPassword(@RequestParam String password, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.modifyForPassword(password, compulsionGet(request));
    }

    /**
     * 处理关注请求, 作者的学号由前端获取, 关注人学号从session里获取
     *
     * @param uid  作者的学号
     * @return
     * 	是否关注成功
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Result like(@RequestParam Integer uid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.like(uid, compulsionGet(request));
    }

    /**
     * 处理取消关注请求, 作者的学号由前端获取, 关注人学号从session里获取
     * @param uid 被关注人 uid
     * @return
     * 	是否取消成功
     */
    @RequestMapping(value = "/delike", method = RequestMethod.POST)
    public Result disLike(@RequestParam Integer uid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.disLike(uid, compulsionGet(request));
    }

    /**
     * 关注状态
     * @param uid 被关注人学号
     * @return
     * 	是否关注
     */
    @GetMapping("/likestatus")
    public Result getLikeStatus(@RequestParam Integer uid, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.getLikeStatus(uid, compulsionGet(request));
    }

    /**
     * 更换头像
     * @param img  上传头像文件
     * @return
     * 	修改成功：返回新的头像名
     * 	修改失败：返回原因
     */
    @RequestMapping(value = "/changeavatar", method = RequestMethod.POST)
    public Result updateImage(@RequestParam("img") MultipartFile img, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.updateImage(img, compulsionGet(request));
    }

    /**
     * 修改用户信息
     * @param user 用户所需要修改的信息---
     * @return
     * 	是否修改成功
     */
    @RequestMapping(value = "/changeinfor", method = RequestMethod.POST)
    public Result updateUserInformation(UserInformation user, HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        Map<String, Object> map = new HashMap<>();
        map.put("oldUser", user);
        map.put("newUser", compulsionGet(request));
        Result result = providerService.updateUserInformation(map);
        UserInformation newUser = (UserInformation) result.getJson().get("user");
        replaceUserInformation(HttpUtils.getSessionId(request), newUser);
        return result;
    }

    /**
     * 修改用户认证信息
     */
    private void replaceUserInformation(String sessionId, UserInformation user) {
        // 从本地缓存删除
        remove(sessionId);
        // 从 redis 删除
        redisUtils.del(sessionId);
        // 添加新的到 redis
        redisUtils.set(sessionId, user, DateUtils.CSC_DAY);
        // 添加到本地缓存
        compulsionGet(sessionId);
    }

    /**
     * 获取自己的博客
     * @param p 第几页
     * @return
     * 	对应页面的文章信息
     */
    @RequestMapping(value = "/getoneselfblobs", method = RequestMethod.GET)
    public Result getOneselfBlobsByUid(@RequestParam(value = "p", defaultValue = "1")Integer p,
                                       HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.getOneselfBlobsByUid(p, compulsionGet(request));
    }

    /**
     * 获取自己的粉丝
     * @param p 第几页
     * @return
     * 	对应页面的粉丝信息
     */
    @GetMapping("/getoneselffans")
    public Result getOneselfFans(@RequestParam(value = "p", defaultValue = "1") Integer p,
                                 HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.getOneselfFans(p, compulsionGet(request));
    }

    /**
     * 获取自己的关注
     * @param p 第几页
     * @return
     * 	对应页面的关注人信息
     */
    @GetMapping("/getoneselficons")
    public Result getOneselfIcons(@RequestParam(value = "p", defaultValue = "1") Integer p,
                                  HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.getOneselfIcons(p, compulsionGet(request));
    }

    /**
     * 获取用户的博客
     * @param p 第几页
     * @param uid 用户 uid
     * @return
     * 	对应用户对应页面的文章信息
     */
    @RequestMapping(value = "/getblobs", method = RequestMethod.GET)
    public Result getBlobsByUid(@RequestParam(value = "p", defaultValue = "1")Integer p,
                                @RequestParam Integer uid,
                                HttpServletRequest request) {
        return providerService.getBlobsByUid(p, uid);
    }

    /**
     * 获取用户粉丝
     * @param p 第几页
     * @param uid 用户 uid
     * @return
     * 	对应用户对应页面的粉丝信息
     */
    @GetMapping("/getfans")
    public Result getFans(@RequestParam(value = "p", defaultValue = "1") Integer p,
                          @RequestParam Integer uid,
                          HttpServletRequest request) {
        return providerService.getFans(p, uid);
    }

    /**
     * 获取用户的关注
     * @param p 第几页
     * @param uid 用户 uid
     * @return
     * 	对应页面对应用户的关注人信息
     */
    @GetMapping("/geticons")
    public Result getIcons(@RequestParam(value = "p", defaultValue = "1") Integer p,
                           @RequestParam Integer uid,
                           HttpServletRequest request) {
        return providerService.getIcons(p, uid);
    }

    /**
     * 获取登录状态用户的邮箱
     * @return
     * 	当前登录用户的邮箱（已加密）
     */
    @RequestMapping("/loginemail")
    public Result getEmailByUid(HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.getEmailByUid(compulsionGet(request));
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
    @GetMapping("/datacheck")
    public Result dataCheck(@RequestParam("data") String data,
                            @RequestParam("type") String type,
                            HttpServletRequest request) {
        return providerService.dataCheck(data, type);
    }

    /**
     * 获取自己的浏览记录
     * @param p 第几页
     * @return
     * 	浏览记录
     */
    @GetMapping("/getoneselfbrowsingrecord")
    public Result getBrowsingRecord(@RequestParam(defaultValue = "1") Integer p,
                                    HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.getBrowsingRecord(p, compulsionGet(request));
    }

    /**
     * 获取自己的通知
     * @param p 第几页
     * @return
     * 	通知信息
     */
    @GetMapping("/getnotification")
    public Result getOneselfNotification(@RequestParam(defaultValue = "1") Integer p,
                                         HttpServletRequest request) throws UserNotLoginException {
        loginOrNotLogin(request);
        return providerService.getOneselfNotification(p, compulsionGet(request));
    }


    @Override
    public UserInformation compulsionGet(@NonNull String sessionId) {
        if(!contains(sessionId)) {
            UserInformation user = (UserInformation) redisUtils.get(sessionId);
            add(sessionId, user);
        }
        return get(sessionId);
    }

}
