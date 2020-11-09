package com.pdsu.csc.shiro;

import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.exception.web.user.UserAbnormalException;
import com.pdsu.csc.handler.UserHandler;
import com.pdsu.csc.service.MyEmailService;
import com.pdsu.csc.service.MyImageService;
import com.pdsu.csc.service.SystemNotificationService;
import com.pdsu.csc.service.UserInformationService;
import com.pdsu.csc.utils.SimpleUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author 半梦
 *
 */
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
		if(userInformationService.countByUid(uid) == 0) {
			throw new UnknownAccountException("账号不存在");
		}
		UserInformation user = userInformationService.selectByUid(uid);
		UserInformation userInformation = user.createUserInformationByThis();
		if(userInformation == null) {
			throw new UserAbnormalException("未知错误, 请稍候重试");
		}
		if(user.getAccountStatus().equals(UserHandler.USER_STATUS_FROZEN)) {
			throw new UserAbnormalException("账号被冻结");
		}
		if(user.getAccountStatus().equals(UserHandler.USER_STATUS_BAN)) {
			throw new UserAbnormalException("账号被封禁");
		}
		if(user.getAccountStatus().equals(UserHandler.USER_STATUS_CANCELLED)) {
			throw new UserAbnormalException("账号已注销");
		}
		Object credentials = userInformation.getPassword();
		String realmName = getName();
		ByteSource credentialsSalt = ByteSource.Util.bytes(uid+"");
		try {
			userInformation.setImgpath(myImageService.selectImagePathByUid(userInformation.getUid()).getImagePath());
		} catch (NotFoundUidException e) {
		}
		userInformation.setSystemNotifications(systemNotificationService.countSystemNotificationByUidAndUnRead(userInformation.getUid()));
		userInformation.setEmail(SimpleUtils.getAsteriskForString(myEmailService.selectMyEmailByUid(userInformation.getUid()).getEmail()));
		return new SimpleAuthenticationInfo(userInformation, credentials, credentialsSalt, realmName);
	}
	
	/**
	 * 负责权限分配
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

}
