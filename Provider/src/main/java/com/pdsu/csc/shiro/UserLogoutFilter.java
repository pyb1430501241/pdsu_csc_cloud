package com.pdsu.csc.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.pdsu.csc.handler.AbstractHandler;
import com.pdsu.csc.handler.ParentHandler;
import com.pdsu.csc.utils.ShiroUtils;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

/**
 * 退出请求拦截器
 * @author 半梦
 */
@Log4j2
public class UserLogoutFilter extends LogoutFilter{

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) {
		Subject subject = getSubject(request, response);
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			return false;
		}
		response.setCharacterEncoding(AbstractHandler.DEFAULT_CHARACTER);
        JSONObject json = new JSONObject();
		try {
			log.info("用户: " + ShiroUtils.getUserInformation().getUid() + ", 退出登录");
	        subject.logout();
	    } catch (Exception ise) {
	    	json.appendField("code", 404);
	        json.appendField("msg", "fail");
	        out.println(json);
	        out.flush();
	        out.close();
	        log.error("用户退出登录失败, 原因: " + ise.getMessage());
	        return false;
	    }
        json.appendField("code", 200);
        json.appendField("msg", "success");
        out.println(json);
        out.flush();
        out.close();
        log.info("用户退出登录成功");
	    return false;
	}
	
}
