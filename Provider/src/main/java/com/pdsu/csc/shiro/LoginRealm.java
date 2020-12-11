package com.pdsu.csc.shiro;

import com.pdsu.csc.bean.AccountStatus;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.exception.web.user.UserAbnormalException;
import com.pdsu.csc.handler.UserHandler;
import com.pdsu.csc.service.MyEmailService;
import com.pdsu.csc.service.MyImageService;
import com.pdsu.csc.service.SystemNotificationService;
import com.pdsu.csc.service.UserInformationService;
import com.pdsu.csc.utils.ElasticsearchUtils;
import com.pdsu.csc.utils.StringUtils;
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

	private UserInformation getUserInformation(Integer uid) {
		if(userInformationService.countByUid(uid) == 0) {
			throw new UnknownAccountException("账号不存在");
		}
		UserInformation user = userInformationService.selectByUid(uid);
		UserInformation userInformation = user.createUserInformationByThis();
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
				throw new UserAbnormalException("未知错误, 请稍候重试");
		}
	}

	private void perfectInformation(UserInformation user) {
		try {
			user.setImgpath(myImageService.selectImagePathByUid(user.getUid()).getImagePath());
		} catch (NotFoundUidException e) {
			throw new UserAbnormalException("未知错误, 请稍候重试");
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
