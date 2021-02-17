package com.pdsu.csc.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.pdsu.csc.bean.MyCollection;
import com.pdsu.csc.bean.MyCollectionExample;
import com.pdsu.csc.bean.WebInformationExample;
import com.pdsu.csc.dao.MyCollectionMapper;
import com.pdsu.csc.dao.WebInformationMapper;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.user.UidAndWebIdRepetitionException;
import com.pdsu.csc.service.MyCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


/**
 *
 * @author 半梦
 *
 */
@Service("myCollectionService")
public class MyCollectionServiceImpl implements MyCollectionService {

	@Autowired
	private MyCollectionMapper myCollectionMapper;
	
	@Autowired
	private WebInformationMapper webInformationMapper;

	@Override
	public boolean insert(@NonNull MyCollection con) throws UidAndWebIdRepetitionException {
		if(countByUidAndWebId(con.getUid(), con.getWid())) {
			throw new UidAndWebIdRepetitionException("不可重复收藏同一篇博客");
		}
		if(myCollectionMapper.insertSelective(con) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer selectCollectionsByWebId(@NonNull Integer webid) throws NotFoundBlobIdException {
		if(!countByWebId(webid)) {
			throw new NotFoundBlobIdException("该文章不存在");
		}
		MyCollectionExample example = new MyCollectionExample();
		MyCollectionExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		return (int) myCollectionMapper.countByExample(example);
	}

	@Override
	public List<Integer> selectCollectionssByWebIds(@NonNull List<Integer> webids){
		List<Integer> collectionss = new ArrayList<Integer>();
		for(Integer webid : webids) {
			try {
				collectionss.add(selectCollectionsByWebId(webid));
			} catch (NotFoundBlobIdException e) {
			}
		}
		return collectionss;
	}

	@Override
	public boolean delete(@NonNull Integer uid, @NonNull Integer webid) throws UidAndWebIdRepetitionException {
		if(!countByUidAndWebId(uid, webid)) {
			throw new UidAndWebIdRepetitionException("你并没有收藏该文章");
		}
		MyCollectionExample example = new MyCollectionExample();
		MyCollectionExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andWidEqualTo(webid);
		if(myCollectionMapper.deleteByExample(example) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean countByUidAndWebId(@NonNull Integer uid, @NonNull Integer webid) {
		MyCollectionExample example = new MyCollectionExample();
		MyCollectionExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		criteria.andUidEqualTo(uid);
		return myCollectionMapper.countByExample(example) > 0 ? true : false;
	}
	

	/**
	 * 查询博客是否存在
	 */
	@Override
	public boolean countByWebId(@NonNull Integer webid) {
		WebInformationExample example = new WebInformationExample();
		com.pdsu.csc.bean.WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(webid);
		long l = webInformationMapper.countByExample(example);
		return l <= 0 ? false : true;
	}

	@Override
	public Integer countCollectionByUid(@NonNull Integer uid) {
		MyCollectionExample example = new MyCollectionExample();
		MyCollectionExample.Criteria criteria = example.createCriteria();
		criteria.andBidEqualTo(uid);
		return (int) myCollectionMapper.countByExample(example);
	}

	@Override
	public List<MyCollection> selectWebIdsByUid(@NonNull Integer uid, Integer p) {
		MyCollectionExample example = new MyCollectionExample();
		MyCollectionExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		if(p != null) {
			PageHelper.startPage(p, 10);
		}
		return myCollectionMapper.selectByExample(example);
	}
}
