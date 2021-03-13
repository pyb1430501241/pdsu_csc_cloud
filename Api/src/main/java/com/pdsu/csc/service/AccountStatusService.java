package com.pdsu.csc.service;

import com.pdsu.csc.bean.AccountStatus;
import com.pdsu.csc.bean.AccountStatusExample;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 该接口提供与账号状态相关的方法
 * @author 半梦
 *
 */
public interface AccountStatusService extends
		TemplateService<AccountStatus, AccountStatusExample>, UserService {
	
	//根据 id 删除记录
	public boolean deleteById(@NonNull Integer id);
	
}
