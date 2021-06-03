package com.pdsu.csc.service;

import com.pdsu.csc.bean.MyEmail;
import com.pdsu.csc.bean.MyEmailExample;
import com.pdsu.csc.exception.CodeSharingCommunityException;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.exception.web.user.UserException;
import com.pdsu.csc.exception.web.user.email.EmailRepetitionException;
import com.pdsu.csc.exception.web.user.email.NotFoundEmailException;
import org.springframework.lang.NonNull;

/**
 * 该接口提供和邮箱相关的方法
 * @author 半梦
 *
 */
public interface MyEmailService extends TemplateService<MyEmail, MyEmailExample> {

	/**
	 *  插入邮箱对象
	 */
	@Override
	boolean insert(MyEmail myEmail) throws CodeSharingCommunityException;

	/**
	 * 查询邮箱是否存在
	 * @param email
	 * @return
	 */
	public boolean countByEmail(@NonNull String email);

	/**
	 * 根据邮箱地址获取一个 MyEamil 的对象
	 * @param email
	 * @return
	 */
	public MyEmail selectMyEmailByEmail(@NonNull String email) throws NotFoundEmailException;

	/**
	 * 根据学号来获取一个 MyEmail 的对象
	 * @param uid
	 * @return
	 */
	public MyEmail selectMyEmailByUid(@NonNull Integer uid);

}
