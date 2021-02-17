package com.pdsu.csc.impl;

import com.pdsu.csc.bean.WebInformationExample;
import com.pdsu.csc.bean.WebThumbs;
import com.pdsu.csc.bean.WebThumbsExample;
import com.pdsu.csc.dao.WebInformationMapper;
import com.pdsu.csc.dao.WebThumbsMapper;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.blob.RepetitionThumbsException;
import com.pdsu.csc.service.WebThumbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 半梦
 *
 */
@Service("webThumbsService")
public class WebThumbsServiceImpl implements WebThumbsService {
	
	@Autowired
	private WebThumbsMapper webThumbsMapper;
	
	@Autowired
	private WebInformationMapper webinformationMapper;
	
	/**
	 * 根据网页ID集合获取这些文章的点赞数
	 */
	@Override
	public List<Integer> selectThumbssForWebId(@NonNull List<Integer> webids) {
		List<Integer> thumbss = new ArrayList<Integer>();
		for(Integer webid : webids) {
			thumbss.add(selectThumbsForWebId(webid));
		}
		return thumbss;
	}

	/**
	 * 根据网页ID获取文章的点赞数
	 */
	@Override
	public Integer selectThumbsForWebId(@NonNull Integer webid) {
		WebThumbsExample example = new WebThumbsExample();
		WebThumbsExample.Criteria criteria = example.createCriteria();
		criteria.andWebidEqualTo(webid);
		long l = webThumbsMapper.countByExample(example);
		return (int)l;
	}

	/**
	 * 根据学号获取一个人的总点赞数
	 */
	@Override
	public Integer countThumbsByUid(@NonNull Integer uid) {
		WebThumbsExample example = new WebThumbsExample();
		WebThumbsExample.Criteria criteria = example.createCriteria();
		criteria.andBidEqualTo(uid);
		return (int) webThumbsMapper.countByExample(example);
	}

	@Override
	public boolean insert(@NonNull WebThumbs webThumbs) throws NotFoundBlobIdException, RepetitionThumbsException {
		if(!countByWebId(webThumbs.getWebid())) {
			throw new NotFoundBlobIdException();
		}
		if(countByWebIdAndUid(webThumbs.getWebid(), webThumbs.getUid())) {
			throw new RepetitionThumbsException();
		}
		return webThumbsMapper.insertSelective(webThumbs) > 0;
	}

	@Override
	public boolean countByWebId(@NonNull Integer webid) {
		WebInformationExample example = new WebInformationExample();
		com.pdsu.csc.bean.WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(webid);
		return webinformationMapper.countByExample(example) != 0;
	}

	@Override
	public boolean deletebyWebIdAndUid(@NonNull Integer webid, @NonNull Integer uid) throws RepetitionThumbsException {
		if(!countByWebIdAndUid(webid, uid)) {
			throw new RepetitionThumbsException("你并未点赞该文章");
		}
		WebThumbsExample example = new WebThumbsExample();
		WebThumbsExample.Criteria criteria = example.createCriteria();
		criteria.andWebidEqualTo(webid);
		criteria.andUidEqualTo(uid);
		return webThumbsMapper.deleteByExample(example) > 0;
	}
	
	@Override
	public boolean countByWebIdAndUid(@NonNull Integer webid, @NonNull Integer uid) {
		WebThumbsExample example = new WebThumbsExample();
		WebThumbsExample.Criteria criteria = example.createCriteria();
		criteria.andWebidEqualTo(webid);
		criteria.andUidEqualTo(uid);
		return webThumbsMapper.countByExample(example) != 0;
	}
}
