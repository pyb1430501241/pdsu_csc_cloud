package com.pdsu.csc.service;

import com.pdsu.csc.bean.Contype;
import com.pdsu.csc.bean.ContypeExample;
import org.springframework.lang.NonNull;

import java.util.List;


/**
 * @author 半梦
 */
public interface ContypeService extends TemplateService<Contype, ContypeExample> {

	/**
	 * 获取文章类型列表
	 * @return
	 */
	@NonNull
	public List<Contype> selectContypes();
	
}
