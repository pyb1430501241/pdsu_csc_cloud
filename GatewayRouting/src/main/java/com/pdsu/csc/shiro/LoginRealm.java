package com.pdsu.csc.shiro;

import com.pdsu.csc.bean.AccountStatus;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.exception.web.user.UserAbnormalException;
import com.pdsu.csc.handler.AbstractHandler;
import com.pdsu.csc.service.MyEmailService;
import com.pdsu.csc.service.MyImageService;
import com.pdsu.csc.service.SystemNotificationService;
import com.pdsu.csc.service.UserInformationService;
import com.pdsu.csc.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

/**
 * @author 半梦
 * @create 2021-02-20 20:35
 */
@Log4j2
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private MyImageService myImageService;

    @Autowired
    private MyEmailService myEmailService;

    @Autowired
    private SystemNotificationService systemNotificationService;

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
        Integer uid = Integer.parseInt(uptoken.getUsername());

        // 获取登录账号的详细信息
        UserInformation user = getUserInformation(uid);

        // 查看其账号状态
        determineAccountStatus(user.getAccountStatus());

        // 完善其信息
        perfectInformation(user);

        Object credentials = user.getPassword();
        String realmName = getName();
        ByteSource credentialsSalt = ByteSource.Util.bytes(uid+"");
        return new SimpleAuthenticationInfo(user, credentials, credentialsSalt, realmName);
    }

    private UserInformation getUserInformation(@NonNull Integer uid) {
        if(!userInformationService.isExistByUid(uid)) {
            throw new UnknownAccountException("账号不存在");
        }
        UserInformation user = userInformationService.selectByUid(uid);
        UserInformation userInformation = user.copy();
        if(userInformation == null) {
            throw new UserAbnormalException("未知错误, 请稍候重试");
        }
        return userInformation;
    }

    private void determineAccountStatus(Integer status) {
        switch (AccountStatus.getByKey(status)) {
            case FROZEN:
                throw new UserAbnormalException("账号已被冻结");
            case BAN:
                throw new UserAbnormalException("账号已被封禁");
            case CANCELLED:
                throw new UserAbnormalException("账号已被注销");
            case OTHER:
                throw new UserAbnormalException(AbstractHandler.DEFAULT_ERROR_PROMPT);
        }
    }

    private void perfectInformation(UserInformation user) {
        try {
            user.setImgpath(myImageService.selectImagePathByUid(user.getUid()).getImagePath());
        } catch (NotFoundUidException e) {
            log.error(e.getMessage(), e);
            throw new UserAbnormalException(AbstractHandler.DEFAULT_ERROR_PROMPT);
        }
        user.setSystemNotifications(systemNotificationService.countSystemNotificationByUidAndUnRead(user.getUid()));
        user.setEmail(StringUtils.getAsteriskForString(myEmailService.selectMyEmailByUid(user.getUid()).getEmail()));
    }

    /**
     * 负责权限分配
     * 舍弃
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

}
