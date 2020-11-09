package com.pdsu.csc.service;

import com.pdsu.csc.bean.Contype;

import java.util.List;


/**
 * @author 半梦
 */
public interface ContypeService {

	/**
	 * 获取文章类型列表
	 * @return
	 */
	public List<Contype> selectContypes();
	
}
