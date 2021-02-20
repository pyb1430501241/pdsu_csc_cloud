package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserException;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.RedisUtils;
import com.pdsu.csc.utils.ShiroUtils;
import com.pdsu.csc.utils.StringUtils;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author 半梦
 * @create 2021-02-20 20:22
 */
@Log4j2
@RestController
@RequestMapping("/user")
public class UserHandler implements AbstractHandler {

    /**
     * 记住我
     */
    private static final Integer REMEMBER_ME = 1;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 处理登录
     * @param uid 账号
     * @param password 密码
     * @param hit cache获取数据的key
     * @param code 输入的验证码
     * @param flag  是否记住密码 默认为不记住
     * @return
     * 	如登录成功, 返回用户信息及其对应的 sessionId;
     * 	如失败则返回失败原因
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @CrossOrigin
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

        return Result.success()
                .add("user", uu);
    }

    @Override
    public void loginOrNotLogin(UserInformation user) throws UserNotLoginException {
    }

}
