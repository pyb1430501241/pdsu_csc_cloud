package com.pdsu.csc.service;

import com.pdsu.csc.bean.WebInformation;
import com.pdsu.csc.bean.WebInformationExample;
import com.pdsu.csc.exception.web.DeleteInforException;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.es.InsertException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 与博客网页相关的方法
 * @author 半梦
 *
 */
public interface WebInformationService extends
		TemplateService<WebInformation, WebInformationExample>, WebService {

	/**
	 * 根据网页id删除一个网页
	 * @param id
	 * @return
	 */
	public boolean deleteById(@NonNull Integer id) throws NotFoundBlobIdException, DeleteInforException;
	
	/**
	 * 根据网页id查询网页信息
	 * @param id   网页编号
	 * @return
	 */
	public WebInformation selectById(@NonNull Integer id) throws NotFoundBlobIdException;
	
	/**
	 * 根据时间查询网页集合
	 * @return
	 */
	public List<WebInformation> selectWebInformationOrderByTimetest(Integer p);

	/**
	 * 根据作者学号查询网页集
	 * @param uid  学号
	 * @return
	 */
	public List<WebInformation> selectWebInformationsByUid(@NonNull Integer uid, @Nullable Integer p);

	/**
	 * 更新文章
	 * @param web 文章信息
	 * @return
	 */
	public boolean updateByWebId(@NonNull WebInformation web);
	
	/**
	 * 获取用户原创数量
	 * @param uid
	 * @param contype
	 * @return
	 */
	public Integer countOriginalByUidAndContype(@NonNull Integer uid, @NonNull Integer contype);

	/**
	 * 根据博客id获取博客
	 * @param webids
	 * @return
	 */
	public List<WebInformation> selectWebInformationsByIds(@Nullable List<Integer> webids, boolean flag, @Nullable Integer p);
	
}
