package com.pdsu.csc.service;

import java.util.List;

import com.pdsu.csc.bean.WebLabel;
import com.pdsu.csc.bean.WebLabelExample;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author 半梦
 */
public interface WebLabelService extends TemplateService<WebLabel, WebLabelExample>{
	
	/**
	 * 获取所有标签
	 * @return
	 */
	public List<WebLabel> selectLabel(@Nullable Integer p);

	/**
	 * 获取对应的标签信息
	 * @param labelids
	 * @return
	 */
	public List<WebLabel> selectByLabelIds(@NonNull List<Integer> labelids, Integer p);
	
}
