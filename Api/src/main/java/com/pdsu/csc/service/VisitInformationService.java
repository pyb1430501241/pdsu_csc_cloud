package com.pdsu.csc.service;

import java.util.List;

import com.pdsu.csc.bean.VisitInformation;
import com.pdsu.csc.bean.VisitInformationExample;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import org.springframework.lang.NonNull;

/**
 * 该接口提供访问相关的方法
 * @author 半梦
 *
 */
public interface VisitInformationService extends
		TemplateService<VisitInformation, VisitInformationExample>{
	
	/**
	 * 根据网页id集获取该网页的访问量集
	 * @param webids
	 * @return
	 */
	public List<Integer> selectVisitsByWebIds(@NonNull List<Integer> webids);
	
	/**
	 * 根据一个人的uid来获取其总访问量
	 * @param id
	 * @return
	 * @throws NotFoundUidException
	 */
	public Integer selectVisitsByVid(@NonNull Integer id) throws NotFoundUidException;
	
	/**
	 * 根据网页id获取该网页访问量
	 * @param webid
	 * @return
	 * @throws NotFoundBlobIdException
	 */
	public Integer selectvisitByWebId(@NonNull Integer webid) throws NotFoundBlobIdException;

}
