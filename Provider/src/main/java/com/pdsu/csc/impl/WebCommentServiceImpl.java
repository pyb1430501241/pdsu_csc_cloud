package com.pdsu.csc.impl;

import com.pdsu.csc.bean.WebComment;
import com.pdsu.csc.bean.WebCommentExample;
import com.pdsu.csc.bean.WebInformation;
import com.pdsu.csc.bean.WebInformationExample;
import com.pdsu.csc.dao.WebCommentMapper;
import com.pdsu.csc.dao.WebInformationMapper;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.service.WebCommentService;
import com.pdsu.csc.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 半梦
 *
 */
@Service("webCommentService")
public class WebCommentServiceImpl implements WebCommentService, WebService {

	@Autowired
	private WebCommentMapper webCommentMapper;
	
	@Autowired
	private WebInformationMapper webInformationMapper;
	
	@Override
	public boolean insert(@NonNull WebComment webComment) throws NotFoundBlobIdException {
		if(!isExistByWebId(webComment.getWid())) {
			throw new NotFoundBlobIdException();
		}
		return webCommentMapper.insertSelective(webComment) > 0;
	}

	@Override
	public List<WebComment> selectCommentsByWebId(@NonNull Integer webid) throws NotFoundBlobIdException {
		if(!isExistByWebId(webid)) {
			throw new NotFoundBlobIdException();
		}
		WebCommentExample example = new WebCommentExample();
		WebCommentExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		return selectListByExample(example);
	}

	@Override
	public Integer countByUid(@NonNull Integer uid) {
		WebInformationExample example = new WebInformationExample();
		com.pdsu.csc.bean.WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<WebInformation> list = webInformationMapper.selectByExample(example);
		List<Integer> webids = new ArrayList<Integer>();
		for (WebInformation webInformation : list) {
			webids.add(webInformation.getId());
		}
		if(webids.size() == 0) {
			return 0;
		}
		WebCommentExample example2 = new WebCommentExample();
		WebCommentExample.Criteria createCriteria = example2.createCriteria();
		createCriteria.andWidIn(webids);
		return (int) countByExample(example2);
	}

	@Override
	public WebComment selectCommentById(Integer cid) {
		return webCommentMapper.selectByPrimaryKey(cid);
	}

	@Override
	public boolean isExistByCommentId(@NonNull Integer cid) {
		WebCommentExample example = new WebCommentExample();
		com.pdsu.csc.bean.WebCommentExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(cid);
		return webCommentMapper.countByExample(example) > 0;
	}

	@Override
	public boolean deleteByExample(@Nullable WebCommentExample example) {
		return webCommentMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull WebComment webComment, @Nullable WebCommentExample example) {
		return webCommentMapper.updateByExampleSelective(webComment, example) > 0;
	}

	@Override
	@NonNull
	public List<WebComment> selectListByExample(@Nullable WebCommentExample example) {
		return webCommentMapper.selectByExample(example);
	}

	@Override
	public long countByExample(@Nullable WebCommentExample example) {
		return webCommentMapper.countByExample(example);
	}

	@Override
	public boolean isExistByWebId(@NonNull Integer webId) {
		WebInformationExample example = new WebInformationExample();
		com.pdsu.csc.bean.WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(webId);
		return webInformationMapper.countByExample(example) > 0;
	}

}
