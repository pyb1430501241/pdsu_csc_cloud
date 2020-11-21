package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * @author 半梦
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    private Integer id;

    private String role;

    public static final Integer ROLE_USER = 1;

    public static final Integer ROLE_TEACHER = 2;

    public static final Integer ROLE_ADMIN = 3;

    public static final String SYSTEM_USER = "学生";

    public static final String SYSTEM_TEACHER = "教师";

    public static final String SYSTEM_ADMIN = "管理员";

    @AllArgsConstructor
    @Getter
    private enum RoleFactory {
        USER(new Role(ROLE_USER, SYSTEM_USER)),
        TEACHER(new Role(ROLE_TEACHER, SYSTEM_TEACHER)),
        ADMIN(new Role(ROLE_ADMIN, SYSTEM_ADMIN));
        Role role;
    }

    public Role getUser() {
        return RoleFactory.USER.getRole();
    }

    public Role getTeacher() {
        return RoleFactory.TEACHER.getRole();
    }

    public Role getAdmin() {
        return RoleFactory.ADMIN.getRole();
    }

}