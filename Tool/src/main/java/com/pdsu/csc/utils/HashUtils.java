package com.pdsu.csc.utils;

import com.pdsu.csc.bean.EncryptConfig;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

/**
 * Hash加密相关的工具类 
 * @author 半梦
 *
 */
public abstract class HashUtils {


	/**
	 * 根据账号密码去加密密码
	 */
	@NonNull
	public static String getPasswordHash(@NonNull Integer uid, @NonNull String password) {
		SimpleHash simpleHash = new SimpleHash("MD5", password, uid+"", 2);
		return simpleHash.toString();
	}
	
	/**
	 * 根据文件名生成一个hash名称，去代表该文件名
	 */
	@NonNull
	public static String getFileNameForHash(@NonNull String title) {
		SimpleHash simpleHash = new SimpleHash("MD5", title);
		return simpleHash.toString();
	}
	
}
