/*
 * 权限模型采用 RBAC 模型
 * 总共分为三个等级
 * 3. admin
 * 2. teacher
 * 1. student
 */
package com.pdsu.csc.handler;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdsu.csc.bean.ClazzInformation;
import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.service.ClazzInformationService;
import com.pdsu.csc.service.UserClazzInformationService;
import com.pdsu.csc.service.UserInformationService;
import com.pdsu.csc.service.UserRoleService;
import com.pdsu.csc.utils.ShiroUtils;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author 半梦
 * 班级方案已被舍弃
 * 		by 庞亚彬 2020-09-21
 * 1. 该页面负责管理员的相关操作
 * 	如, 封号, 修改用户信息, 删除用户文章等等
 *
 */
@RestController
@RequestMapping("/admin")
@Log4j2
public class AdminHandler extends ParentHandler{

	/**
	 * 权限
	 */
	private UserRoleService userRoleService;

	/**
	 * 用户
	 */
	private UserInformationService userInformationService;

	/**
	 *班级信息
	 */
	@Deprecated
	private ClazzInformationService clazzInformationService;

	/**
	 * 用户班级对应
	 */
	@Deprecated
	private UserClazzInformationService userClazzInformationService;

	/**
	 * 获取所有用户信息
	 * 需要权限为: admin
	 * @param p
	 * @return
	 */
	@GetMapping("/getuserinformationlist")
	@CrossOrigin
	public Result getUserInformationList(@RequestParam(value = "p", defaultValue = "1") Integer p) throws Exception{
		UserInformation user = ShiroUtils.getUserInformation();
		loginOrNotLogin(user);
		if(!userRoleService.isAdmin(user.getUid())) {
			log.info("用户: " + user.getUid() + " 无权限");
			return Result.permission();
		}
		log.info("管理员: " + user.getUid() + " 获取所有用户信息");
		PageHelper.startPage(p, 15);
		PageInfo<UserInformation> userList = new PageInfo<>(
				userInformationService.selectUserInformations(),5);
		return Result.success().add("userList", userList);
	}

	/**
	 *
	 * @param clazzName
	 * @param uids
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	@CrossOrigin
	@PostMapping("/applyclass")
	public Result createClazz(String clazzName, @RequestParam(required = false) List<Integer> uids) throws Exception{
		UserInformation user = ShiroUtils.getUserInformation();
		loginOrNotLogin(user);
		if (!userRoleService.isTeacher(user.getUid())) {
			log.info("用户: " + user.getUid() + "无权限");
			return Result.permissionByTeacher();
		}
		log.info("教师或管理员: " + user.getUid() + "开始创建班级");
		ClazzInformation clazzInformation = new ClazzInformation(clazzName);
		boolean b = clazzInformationService.insert(clazzInformation);
		if (b) {
			log.info("创建班级成功, 班级ID为: " + clazzInformation.getId());
			if(!Objects.isNull(uids) && uids.size() > 0) {
				log.info("");
				userClazzInformationService.insertByList(uids, clazzInformation.getId());
			}
			return Result.success().add("clazzId", clazzInformation.getId());
		}
		log.warn("创建班级失败, 原因为: " + "连接数据库失败");
		return Result.fail().add(EXCEPTION, NETWORK_BUSY);
	}



	@Autowired
	public AdminHandler(UserRoleService userRoleService,
						UserInformationService userInformationService,
						ClazzInformationService clazzInformationService,
						UserClazzInformationService userClazzInformationService) {
		this.userRoleService = userRoleService;
		this.userInformationService = userInformationService;
		this.clazzInformationService = clazzInformationService;
		this.userClazzInformationService = userClazzInformationService;
	}

}
