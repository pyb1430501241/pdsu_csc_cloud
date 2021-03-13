package com.pdsu.csc.impl;

import com.pdsu.csc.bean.*;
import com.pdsu.csc.dao.MyLikeMapper;
import com.pdsu.csc.dao.UserInformationMapper;
import com.pdsu.csc.es.dao.EsDao;
import com.pdsu.csc.exception.web.user.NotFoundUidAndLikeIdException;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.exception.web.user.UidAndLikeIdRepetitionException;
import com.pdsu.csc.service.MyLikeService;
import com.pdsu.csc.service.UserService;
import com.pdsu.csc.utils.ElasticsearchUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理关注相关
 * @author 半梦
 *
 */
@Log4j2
@Service("myLikeService")
public class MyLikeServiceImpl implements MyLikeService, UserService {

	@Autowired
	private MyLikeMapper myLikeMapper;
	
	@Autowired
	private UserInformationMapper userInformationMapper;
	
	@Autowired
	private EsDao esDao;
	
	@Override
	public long countByUid(@NonNull Integer uid) throws NotFoundUidException {
		if(!isExistByUid(uid)) {
			throw new NotFoundUidException("该用户不存在");
		}
		MyLikeExample example = new MyLikeExample();
		MyLikeExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return countByExample(example);
	}

	@Override
	public long countByLikeId(@NonNull Integer likeId) throws NotFoundUidException {
		if(!isExistByUid(likeId)) {
			throw new NotFoundUidException("该用户不存在");
		}
		MyLikeExample example = new MyLikeExample();
		MyLikeExample.Criteria criteria = example.createCriteria();
		criteria.andLikeIdEqualTo(likeId);
		return countByExample(example);
	}

	@Override
	public boolean insert(@NonNull MyLike myLike) throws UidAndLikeIdRepetitionException {
		if(countByUidAndLikeId(myLike.getUid(), myLike.getLikeId())) {
			throw new UidAndLikeIdRepetitionException("你已关注此用户, 无法重复关注");
		}
		if(myLikeMapper.insertSelective(myLike) > 0) {
			new Thread(()->{
				try {
					UserInformation user = userInformationMapper.selectUserByUid(myLike.getLikeId());
					Map<String, Object> map = esDao.queryByTableNameAndId(EsIndex.USER, user.getId());
					EsUserInformation esuser = ElasticsearchUtils.
							getObjectByMapAndClass(map, EsUserInformation.class);
					esuser.setLikenum(esuser.getLikenum()+1);
					esDao.update(esuser, user.getId());
				} catch (Exception e) {
					log.error("es相关操作出现异常", e);
				}
			}).start();
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteByExample(@Nullable MyLikeExample example) {
		return myLikeMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull MyLike myLike, @Nullable MyLikeExample example) {
		return myLikeMapper.updateByExampleSelective(myLike, example) > 0;
	}

	@Override
	@NonNull
	public List<MyLike> selectListByExample(@Nullable MyLikeExample example) {
		return myLikeMapper.selectByExample(example);
	}

	@Override
	public long countByExample(MyLikeExample example) {
		return myLikeMapper.countByExample(example);
	}

	@Override
	public List<Integer> selectLikeIdByUid(@NonNull Integer uid) throws NotFoundUidException {
		if(!isExistByUid(uid)) {
			throw new NotFoundUidException("该用户不存在");
		}
		return myLikeMapper.selectLikeIdByUid(uid);
	}
	
	@Override
	public List<Integer> selectUidByLikeId(@NonNull Integer likeId) throws NotFoundUidException {
		if(!isExistByUid(likeId)) {
			throw new NotFoundUidException("该用户不存在");
		}
		return myLikeMapper.selectUidByLikeId(likeId);
	}

	@Override
	public boolean isExistByUid(@NonNull Integer uid) {
		UserInformationExample example = new UserInformationExample();
		com.pdsu.csc.bean.UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return userInformationMapper.countByExample(example) > 0;
	}

	@Override
	public boolean countByUidAndLikeId(@NonNull Integer uid, @NonNull Integer likeId) {
		MyLikeExample example = new MyLikeExample();
		MyLikeExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andLikeIdEqualTo(likeId);
		return countByExample(example) > 0;
	}
	
	@Override
	public boolean deleteByLikeIdAndUid(@NonNull Integer likeId, @NonNull Integer uid) throws NotFoundUidAndLikeIdException {
		if(!countByUidAndLikeId(likeId, uid)) {
			throw new NotFoundUidAndLikeIdException("您并未关注该用户");
		}
		MyLikeExample example = new MyLikeExample();
		MyLikeExample.Criteria criteria = example.createCriteria();
		criteria.andLikeIdEqualTo(uid);
		criteria.andUidEqualTo(likeId);
		boolean b = deleteByExample(example);
		if(b) {
			new Thread(()->{
				try {
					UserInformation user = userInformationMapper.selectUserByUid(uid);
					Map<String, Object> map = esDao.queryByTableNameAndId(EsIndex.USER, user.getId());
					EsUserInformation esuser =  ElasticsearchUtils.
							getObjectByMapAndClass(map, EsUserInformation.class);
					esuser.setLikenum(esuser.getLikenum()-1);
					esDao.update(esuser, user.getId());
				} catch (Exception e) {

				}
			}).start();
		}
		return b;
	}

	@Override
	public List<Boolean> countByUidAndLikeId(@NonNull Integer uid, @NonNull List<Integer> uids) throws NotFoundUidException {
		if(!isExistByUid(uid)) {
			throw new NotFoundUidException("该用户不存在");
		}
		List<Boolean> islikes = new ArrayList<>();
		for(Integer likeId : uids) {
			islikes.add(countByUidAndLikeId(uid, likeId));
		}
		return islikes;
	}

}
