package com.pdsu.csc.web;


import com.pdsu.csc.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @author 半梦
 *
 */
public class WebStartInterceptor implements ServletContextListener{

	private static final Logger log = LoggerFactory.getLogger("Web 应用监控器");

	private static String date;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		date = DateUtils.getSimpleDateSecond();
		log.info("Web应用: CodeSharingCommunity 启动, " + "当前时间: " + date);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("Web应用: CodeSharingCommunity 关闭, " + "当前时间: " + DateUtils.getSimpleDateSecond());
		log.info("启动总时间为: " + DateUtils.getSimpleDateDifference(date,
				DateUtils.getSimpleDateSecond()) + " S");
	}


}
