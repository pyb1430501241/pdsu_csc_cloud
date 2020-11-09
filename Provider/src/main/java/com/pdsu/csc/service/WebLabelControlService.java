package com.pdsu.csc.service;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author 半梦
 */
public interface WebLabelControlService {

	/**
	 * 插入文章和标签的对应
	 * @param webid
	 * @param labelList
	 * @return
	 */
	public boolean insert(@NonNull Integer webid, @NonNull List<Integer> labelList);
	
	/**
	 * 删除文章标签
	 * @param webid
	 * @return
	 */
	public boolean deleteByWebId(@NonNull Integer webid);

	/**
	 * 获取文章标签ID
	 * @param webid
	 * @return
	 */
	public List<Integer> selectLabelIdByWebId(@NonNull Integer webid);

	/**
	 * 根据标签获取文章
	 * @param lid
	 * @return
	 */
	public List<Integer> selectWebIdsByLid(@NonNull Integer lid);

}
