package com.pdsu.csc.impl;

import com.pdsu.csc.bean.*;
import com.pdsu.csc.dao.MyImageMapper;
import com.pdsu.csc.dao.UserInformationMapper;
import com.pdsu.csc.es.dao.EsDao;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.handler.UserHandler;
import com.pdsu.csc.service.impl.AbstractMyImageService;
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
 * 
 * @author 半梦
 *
 */
@Log4j2
@Service("myImageService")
public class MyImageServiceImpl extends AbstractMyImageService {

	@Autowired
	private MyImageMapper myImageMapper;
	
	@Autowired
	private UserInformationMapper userInformationMapper;
	
	@Autowired
	private EsDao esDao;

	@Override
	public List<MyImage> selectImagePathByUids(@NonNull List<Integer> uids) {
		List<Integer> ids = new ArrayList<>();
		// 判断当前用户是否存在, 如不存在这舍弃
		for(Integer id : uids) {
			if(isExistByUid(id)) {
				ids.add(id);
			}
		}
		if(ids.size() == 0) {
			return new ArrayList<>();
		}
		MyImageExample example = new MyImageExample();
		MyImageExample.Criteria criteria = example.createCriteria();
		criteria.andUidIn(ids);
		return selectListByExample(example);
	}

	@Override
	public MyImage selectImagePathByUid(@NonNull Integer uid) throws NotFoundUidException {
		if(!isExistByUid(uid)) {
			throw new NotFoundUidException("该用户不存在");
		}
		MyImageExample example = new MyImageExample();
		MyImageExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		MyImage myImage = selectByExample(example);
		return myImage == null ? new MyImage(uid, UserHandler.userImgName) : myImage;
	}
	
	/**
	 * 查询是否有此账号
	 */
	@Override
	public boolean isExistByUid(@NonNull Integer uid) {
		UserInformationExample example = new UserInformationExample();
		com.pdsu.csc.bean.UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return userInformationMapper.countByExample(example) > 0;
	}

	@Override
	public boolean insert(@NonNull MyImage myImage) throws NotFoundUidException {
		if(!isExistByUid(myImage.getUid())) {
			throw new NotFoundUidException("该用户不存在");
		}
		return myImageMapper.insertSelective(myImage) != 0;
	}

	@Override
	public boolean update(@NonNull MyImage myImage) throws NotFoundUidException {
		if(!isExistByUid(myImage.getUid())) {
			throw new NotFoundUidException("该用户不存在");
		}
		MyImageExample example = new MyImageExample();
		MyImageExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(myImage.getUid());
		boolean b = updateByExample(myImage, example);
		if(b) {
			new Thread(()->{
				try {
					UserInformation user = userInformationMapper.selectUserByUid(myImage.getUid());
					Map<String, Object> map = esDao.queryByTableNameAndId(EsIndex.USER, user.getId());
					EsUserInformation esUser = ElasticsearchUtils.
							getObjectByMapAndClass(map, EsUserInformation.class);
					esUser.setImgpath(myImage.getImagePath());
					esDao.update(esUser, user.getId());
				} catch (Exception e) {
					log.error("es相关操作出现异常", e);
				}
			}).start();
		}
		return b;
	}

	@Override
	public boolean deleteByExample(@Nullable MyImageExample example) {
		return myImageMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull MyImage myImage, @Nullable MyImageExample example) {
		return myImageMapper.updateByExampleSelective(myImage, example) != 0;
	}

	@Override
	@NonNull
	public List<MyImage> selectListByExample(@Nullable MyImageExample example) {
		return myImageMapper.selectByExample(example);
	}

	@Override
	public long countByExample(MyImageExample example) {
		return myImageMapper.countByExample(example);
	}

}
