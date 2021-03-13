package com.pdsu.csc.impl;

import java.util.ArrayList;
import java.util.List;

import com.pdsu.csc.bean.UserInformationExample;
import com.pdsu.csc.bean.VisitInformation;
import com.pdsu.csc.bean.VisitInformationExample;
import com.pdsu.csc.bean.WebInformationExample;
import com.pdsu.csc.dao.UserInformationMapper;
import com.pdsu.csc.dao.VisitInformationMapper;
import com.pdsu.csc.dao.WebInformationMapper;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.service.UserService;
import com.pdsu.csc.service.VisitInformationService;
import com.pdsu.csc.service.WebService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;


/**
 * 
 * @author 半梦
 *
 */
@Service("visitInformationService")
@Log4j2
public class VisitInformationServiceImpl implements
		VisitInformationService, UserService, WebService {

	@Autowired
	private VisitInformationMapper visitInformationMapper;
	
	@Autowired
	private WebInformationMapper webInformationMapper;
	
	@Autowired
	private UserInformationMapper userInformationMapper;
	
	/**
	 * 根据网页的ID集合获取这些文章的访问量
	 * 如果文章不存在, 则剔除集合
	 */
	@Override
	public List<Integer> selectVisitsByWebIds(@NonNull List<Integer> webids) {
		List<Integer> visits = new ArrayList<>();
		for(Integer id : webids) {
			try {
				visits.add(selectvisitByWebId(id));
			} catch (NotFoundBlobIdException e) {
				log.debug(e.getMessage() + ": " + id);
			}
		}
		return visits;
	}
	
	/**
	 * 获取一个人的总访问量
	 * @throws NotFoundUidException 
	 */
	public Integer selectVisitsByVid(@NonNull Integer id) throws NotFoundUidException {
		if(!isExistByUid(id)) {
			throw new NotFoundUidException("该用户不存在");
		}
		VisitInformationExample example = new VisitInformationExample();
		VisitInformationExample.Criteria criteria = example.createCriteria();
		criteria.andVidEqualTo(id);
		return (int) countByExample(example);
	}

	/**
	 * 插入一次访问记录
	 */
	@Override
	public boolean insert(@NonNull VisitInformation visit) {
		return visitInformationMapper.insertSelective(visit) > 0;
	}
	
	/**
	 * 根据网页ID获取网页访问量
	 * @throws NotFoundBlobIdException 
	 */
	@Override
	public Integer selectvisitByWebId(@NonNull Integer webid) throws NotFoundBlobIdException {
		if(!isExistByWebId(webid)) {
			throw new NotFoundBlobIdException("该文章不存在");
		}
		VisitInformationExample example = new VisitInformationExample();
		VisitInformationExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		return (int) countByExample(example);
	}

	@Override
	public boolean deleteByExample(@Nullable VisitInformationExample example) {
		return visitInformationMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull VisitInformation visitInformation, @Nullable VisitInformationExample example) {
		return visitInformationMapper.updateByExample(visitInformation, example) > 0;
	}

	@Override
	@NonNull
	public List<VisitInformation> selectListByExample(@Nullable VisitInformationExample example) {
		return visitInformationMapper.selectByExample(example);
	}

	@Override
	public long countByExample(@Nullable VisitInformationExample example) {
		return visitInformationMapper.countByExample(example);
	}

	@Override
	public boolean isExistByUid(@NonNull Integer uid) {
		UserInformationExample example = new UserInformationExample();
		com.pdsu.csc.bean.UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return userInformationMapper.countByExample(example) > 0;
	}

	@Override
	public boolean isExistByWebId(@NonNull Integer webId) {
		WebInformationExample example = new WebInformationExample();
		com.pdsu.csc.bean.WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(webId);
		return webInformationMapper.countByExample(example) > 0;
	}
}
