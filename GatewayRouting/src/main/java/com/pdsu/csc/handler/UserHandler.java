package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import com.pdsu.csc.service.SystemNotificationService;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.RedisUtils;
import com.pdsu.csc.utils.ShiroUtils;
import com.pdsu.csc.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author 半梦
 * @create 2021-02-20 20:22
 */
@Log4j2
@RestController
@RequestMapping("/user")
public class UserHandler implements AbstractHandler, LoginHandler {

    /**
     * 记住我
     */
    private static final Integer REMEMBER_ME = 1;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SystemNotificationService systemNotificationService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result login(@RequestParam String uid, @RequestParam String password,
                        @RequestParam String hit, @RequestParam String code,
                        @RequestParam(value = "flag", defaultValue = "0")Integer flag) throws Exception {
        log.info("账号: " + uid + "登录开始");
        log.info("参数为: " + StringUtils.toString(uid, password, hit, code, flag));
        String ss = (String) redisUtils.get(hit);

        if(Objects.isNull(ss)) {
            return Result.fail().add(EXCEPTION, CODE_EXPIRED);
        }

        //从缓存中获取验证码
        log.info("比对验证码中..." + " 用户输入验证码为: " + code + ", 服务器端储存的验证码为: " + ss);
        if(!StringUtils.CompareIgnoreCase(ss, code)) {
            log.info(CODE_ERROR);
            return Result.fail().add(EXCEPTION, CODE_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        if(!Objects.isNull(ShiroUtils.getUserInformation())) {
            subject.logout();
        }
        UsernamePasswordToken token = new UsernamePasswordToken(uid+"", password);
        //是否记住我
        if(!flag.equals(REMEMBER_ME)) {
            token.setRememberMe(false);
        } else {
            token.setRememberMe(true);
        }

        /**
         *	开始登陆, 实际调用
         *	@see com.pdsu.csc.shiro.LoginRealm#doGetAuthenticationInfo
         */
        subject.login(token);
        // 如果登录成功, 则 shiro 应已保存用户信息
        UserInformation uu = (UserInformation) subject.getPrincipal();
        // 密码置为空
        uu.setPassword(null);
        log.info("账号: " + uid + "登录成功, " + HttpUtils.getSessionHeader() + "为: " + subject.getSession().getId());

        return Result.success().add("user", uu);
    }

    /**
     * @return  用户的登录状态
     * 如登录, 返回用户信息
     * 反之返回提示语
     *  loginOrNotLogin(user) 通过异常的方式避免了 user 为 null
     *  所以下一句提醒的 user 可能为 null 的提示可以直接无视
     */
    @RequestMapping(value = "/loginstatus", method = RequestMethod.GET)
    public Result getLoginStatus() throws Exception {

        UserInformation user = ShiroUtils.getUserInformation();

        // 这里排除未认证的情况
        loginOrNotLogin(user);

        // 用户提示信息
        user.setSystemNotifications(systemNotificationService.countSystemNotificationByUidAndUnRead(user.getUid()));
        if(!Objects.isNull(user.getPassword())) {
            user.setPassword(null);
        }

        return Result.success().add("user", user);
    }


    @Override
    @Contract("null -> fail")
    public void loginOrNotLogin(UserInformation user) throws UserNotLoginException {
        if(!ShiroUtils.isAuthorization()) {
            if(Objects.isNull(user)) {
                throw new UserNotLoginException(NOT_LOGIN);
            }
        }
    }

    @Override
    public void loginOrNotLogin(@NonNull HttpServletRequest request) throws UserNotLoginException {
        if(HttpUtils.getSessionId(request) == null) {
            loginOrNotLogin(ShiroUtils.getUserInformation());
        }
    }

    @Override
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }


}
