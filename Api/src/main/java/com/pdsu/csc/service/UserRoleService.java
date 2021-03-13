package com.pdsu.csc.service;

import com.pdsu.csc.bean.UserRole;
import com.pdsu.csc.bean.UserRoleExample;
import org.springframework.lang.NonNull;

/**
 * @author 半梦
 * @create 2020-08-13 16:09
 */
public interface UserRoleService extends TemplateService<UserRole, UserRoleExample> {

    /**
     * 判断用户是否为管理员
     * @param uid
     * @return
     */
    public boolean isAdmin(@NonNull Integer uid);

    /**
     * 判断用户是否为老师
     * @param uid
     * @return
     */
    public boolean isTeacher(@NonNull Integer uid);

}
