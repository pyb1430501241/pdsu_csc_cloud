package com.pdsu.csc.service.impl;

import com.github.pagehelper.PageHelper;
import com.pdsu.csc.bean.*;
import com.pdsu.csc.dao.*;
import com.pdsu.csc.es.dao.EsDao;
import com.pdsu.csc.exception.web.DeleteInforException;
import com.pdsu.csc.exception.web.es.InsertException;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.exception.web.user.UidRepetitionException;
import com.pdsu.csc.service.UserInformationService;
import com.pdsu.csc.utils.HashUtils;
import com.pdsu.csc.utils.ElasticsearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 该类继承 UserInformationService 接口, 用于处理与用户有关的逻辑
 * @author 半梦
 *
 */
@Service("userInformationService")
public class UserInformationServiceImpl implements UserInformationService {
	
	@Autowired
	private UserInformationMapper userInformationMapper;
	
	@Autowired
	private MyLikeMapper myLikeMapper;
	
	@Autowired
	private MyCollectionMapper myCollectionMapper;
	
	@Autowired
	private VisitInformationMapper visitInformationMapper;
	
	@Autowired
	private MyEmailMapper myEmailMapper;
	
	@Autowired
	private MyImageMapper myImageMapper;
	
	@Autowired
	private WebThumbsMapper webThumbsMapper;
	
	@Autowired
	private WebFileMapper webFileMapper;
	
	@Autowired
	private WebInformationMapper webInformationMapper;
	
	@Autowired
	private WebCommentMapper webCommentMapper;
	
	@Autowired
	private WebCommentReplyMapper webCommentReplyMapper;
	
	@Autowired
	private EsDao esDao;
	
	/**
	 * 插入一个用户信息
	 * @throws UidRepetitionException 
	 * @throws InsertException 
	 */
	@Override
	public boolean insert(@NonNull UserInformation information) throws UidRepetitionException {
		Integer uid = information.getUid();
		if(countByUid(uid) != 0) {
			throw new UidRepetitionException("学号不可重复");
		}
		String password = information.getPassword();
		password = HashUtils.getPasswordHash(uid, password);
		information.setPassword(password);
		if(userInformationMapper.insertSelective(information) != 0) {
			Integer id = information.getId();
			EsUserInformation user = new EsUserInformation(information.getUid(),
					0, information.getImgpath(), 0, information.getUsername());
			try {
				esDao.insert(user, id);
			} catch (InsertException e) {
			}
			return true;
		}
		return false;
	}

	/**
	 * 该方法不提供使用
	 * 删除一个用户信息
	 * 由于用户信息绑定了自身发布的博客页面
	 * 以及自身的访问量，头像，实名认证，邮箱绑定
	 * 上传文件，下载量， 收藏量，关注以及被关注等等，所以要先删除
	 * 所依赖于它的相关信息
	 * @throws NotFoundUidException, DeleteInforException
	 */
	@Override
	public boolean deleteByUid(@NonNull Integer uid) throws NotFoundUidException, DeleteInforException {
		if(countByUid(uid) == 0) {
			throw new NotFoundUidException("该用户不存在");
		}
		
		/**
		 * 删除和用户相关的收藏信息 
		 */
		MyCollectionExample myCollectionExample = new MyCollectionExample();
		com.pdsu.csc.bean.MyCollectionExample.Criteria myCollectionCriteria1 = myCollectionExample.createCriteria();
		myCollectionCriteria1.andBidEqualTo(uid);
		com.pdsu.csc.bean.MyCollectionExample.Criteria myCollectionCriteria2 = myCollectionExample.createCriteria();
		myCollectionCriteria2.andUidEqualTo(uid);
		myCollectionExample.or(myCollectionCriteria2);
		//获取和用户相关的收藏信息总数
		long mycollectionCount = myCollectionMapper.countByExample(myCollectionExample);
		if(myCollectionMapper.deleteByExample(myCollectionExample) != mycollectionCount) {
			throw new DeleteInforException("删除用户收藏信息失败");
		}
		
		/**
		 *删除和用户相关的访问信息 
		 */
		VisitInformationExample visitInformationExample = new VisitInformationExample();
		com.pdsu.csc.bean.VisitInformationExample.Criteria visitInformationCriteria1 = visitInformationExample.createCriteria();
		visitInformationCriteria1.andSidEqualTo(uid);
		com.pdsu.csc.bean.VisitInformationExample.Criteria visitInformationCriteria2 = visitInformationExample.createCriteria();
		visitInformationCriteria2.andVidEqualTo(uid);
		visitInformationExample.or(visitInformationCriteria2);
		long visitCount = visitInformationMapper.countByExample(visitInformationExample);
		if(visitInformationMapper.deleteByExample(visitInformationExample) != visitCount) {
			throw new DeleteInforException("删除用户访问信息失败");
		}
		
		/**
		 *删除用户相关的点赞信息
		 */
		WebThumbsExample webThumbsExample = new WebThumbsExample();
		com.pdsu.csc.bean.WebThumbsExample.Criteria webThumbsCriteria1 = webThumbsExample.createCriteria();
		webThumbsCriteria1.andBidEqualTo(uid);
		com.pdsu.csc.bean.WebThumbsExample.Criteria webThumbsCriteria2 = webThumbsExample.createCriteria();
		webThumbsCriteria2.andUidEqualTo(uid);
		webThumbsExample.or(webThumbsCriteria2);
		long webThumbsCount = webThumbsMapper.countByExample(webThumbsExample);
		if(webThumbsMapper.deleteByExample(webThumbsExample) != webThumbsCount) {
			throw new DeleteInforException("删除用户点赞信息失败");
		}
		
		/**
		 *删除用户相关的邮箱信息 
		 */
		MyEmailExample myEmailExample = new MyEmailExample();
		com.pdsu.csc.bean.MyEmailExample.Criteria myEmailCriteria = myEmailExample.createCriteria();
		myEmailCriteria.andUidEqualTo(uid);
		long myEmailCount = myEmailMapper.countByExample(myEmailExample);
		if(myEmailMapper.deleteByExample(myEmailExample) != myEmailCount) {
			throw new DeleteInforException("删除用户邮箱失败");
		}
		
		/**
		 * 删除用户相关的头像信息
		 */
		MyImageExample myImageExample = new MyImageExample();
		com.pdsu.csc.bean.MyImageExample.Criteria myImageCriteria = myImageExample.createCriteria();
		myImageCriteria.andUidEqualTo(uid);
		long myImageCount = myImageMapper.countByExample(myImageExample);
		if(myImageMapper.deleteByExample(myImageExample) != myImageCount) {
			throw new DeleteInforException("删除用户头像失败");
		}
		
		/**
		 * 删除用户上传的文件信息
		 */
		WebFileExample webFileExample = new WebFileExample();
		com.pdsu.csc.bean.WebFileExample.Criteria webFileCriteria = webFileExample.createCriteria();
		webFileCriteria.andUidEqualTo(uid);
		long webFileCount = webFileMapper.countByExample(webFileExample);
		if(webFileMapper.deleteByExample(webFileExample) != webFileCount) {
			throw new DeleteInforException("删除用户文件失败");
		}
		
		/**
		 * 删除用户相关的关注信息
		 */
		MyLikeExample myLikeExample = new MyLikeExample();
		com.pdsu.csc.bean.MyLikeExample.Criteria myLikeCriteria1 = myLikeExample.createCriteria();
		myLikeCriteria1.andUidEqualTo(uid);
		com.pdsu.csc.bean.MyLikeExample.Criteria myLikeCriteria2 = myLikeExample.createCriteria();
		myLikeCriteria2.andLikeIdEqualTo(uid);
		myLikeExample.or(myLikeCriteria2);
		long myLikeCount = myLikeMapper.countByExample(myLikeExample);
		if(myLikeMapper.deleteByExample(myLikeExample) != myLikeCount) {
			throw new DeleteInforException("删除用户关注信息失败");
		}
		
		/**
		 * 删除用户相关的评论信息
		 */
		WebCommentReplyExample webCommentReplyExample = new WebCommentReplyExample();
		com.pdsu.csc.bean.WebCommentReplyExample.Criteria webCommentReplyCriteria1 = webCommentReplyExample.createCriteria();
		webCommentReplyCriteria1.andUidEqualTo(uid);
		com.pdsu.csc.bean.WebCommentReplyExample.Criteria webCommentReplyCriteria2 = webCommentReplyExample.createCriteria();
		webCommentReplyCriteria2.andBidEqualTo(uid);
		webCommentReplyExample.or(webCommentReplyCriteria2);
		long webCommentReplyCount = webCommentReplyMapper.countByExample(webCommentReplyExample);
		if(webCommentReplyMapper.deleteByExample(webCommentReplyExample) != webCommentReplyCount) {
			throw new DeleteInforException("删除用户评论信息失败");
		}
		
		WebCommentExample webCommentExample = new WebCommentExample();
		com.pdsu.csc.bean.WebCommentExample.Criteria webCommentCriteria = webCommentExample.createCriteria();
		webCommentCriteria.andUidEqualTo(uid);
		long webCommentCount = webCommentMapper.countByExample(webCommentExample);
		if(webCommentMapper.deleteByExample(webCommentExample) != webCommentCount) {
			throw new DeleteInforException("删除用户评论信息失败");
		}
		
		/**
		 * 删除用户博客相关的信息		
		 */
		WebInformationExample webInformationExample = new WebInformationExample();
		com.pdsu.csc.bean.WebInformationExample.Criteria webInforCriteria = webInformationExample.createCriteria();
		webInforCriteria.andUidEqualTo(uid);
		long webInforCount = webInformationMapper.countByExample(webInformationExample);
		if(webInformationMapper.deleteByExample(webInformationExample) != webInforCount) {
			throw new DeleteInforException("删除用户博客失败");
		}
		
		UserInformationExample example = new UserInformationExample();
		UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		if(userInformationMapper.deleteByExample(example) > 0) {
			return true;
		}
		throw new DeleteInforException("删除用户失败");
	}

	/**
	 * 根据用户的uid查询用户信息
	 */
	@Override
	public UserInformation selectByUid(@NonNull Integer uid) {
		if(countByUid(uid) <= 0) {
			return null;
		}
		UserInformationExample example = new UserInformationExample();
		UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return userInformationMapper.selectByExample(example).get(0);
	}
	
	/**
	 * 根据一个用户的uid查询其所有关注人的信息
	 * @throws NotFoundUidException 
	 */
	@Override
	public List<UserInformation> selectUsersByUid(@NonNull Integer uid, Integer p) throws NotFoundUidException {
//		if(countByUid(uid) == 0) {
//			throw new NotFoundUidException("该用户不存在");
//		}
		if(p != null) {
			PageHelper.startPage(p, 20);
		}
		List<Integer> likeids = myLikeMapper.selectLikeIdByUid(uid);
		if(likeids.size() == 0) {
			return new ArrayList<>();
		}
		UserInformationExample example = new UserInformationExample();
		UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidIn(likeids);
		return userInformationMapper.selectByExample(example);
	}
	
	/**
	 * 根据一个用户的 uid 查询一个用户所有粉丝的信息
	 * @throws NotFoundUidException 
	 */
	@Override
	public List<UserInformation> selectUsersByLikeId(@NonNull Integer likeId, Integer p) throws NotFoundUidException {
		if(p != null) {
			PageHelper.startPage(p, 20);
		}
		List<Integer> uids = myLikeMapper.selectUidByLikeId(likeId);
		if(uids.size() == 0) {
			return new ArrayList<>();
		}
		UserInformationExample example = new UserInformationExample();
		UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidIn(uids);
		List<UserInformation> list = userInformationMapper.selectByExample(example);
		return list == null ? new ArrayList<>() : list;
	}

	/**
	 * 根据用户的 uid 集群查询用户信息集群
	 * 如果该用户不存在, 则剔除集合
	 */
	@Override
	public List<UserInformation> selectUsersByUids(@Nullable List<Integer> uids) {
		List<Integer> f = new ArrayList<>();
		if(uids == null) {
			return new ArrayList<>();
		}
		for(Integer uid : uids) {
			if(countByUid(uid) == 0) {
				f.add(uid);
			}
		}
		uids.removeAll(f);
		if(uids.size() == 0) {
			return new ArrayList<>();
		}
		return userInformationMapper.selectUsersByUids(uids);
	}
	
	/**
	 * 查询是否有此账号
	 */
	@Override
	public int countByUid(@NonNull Integer uid) {
		UserInformationExample example = new UserInformationExample();
		UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return (int) userInformationMapper.countByExample(example);
	}
	
	/**
	 * 修改密码
	 */
	@Override
	public boolean modifyThePassword(@NonNull Integer uid, @NonNull String password) {
		UserInformation user = selectByUid(uid);
		user.setPassword(HashUtils.getPasswordHash(uid, password));
		UserInformationExample example = new UserInformationExample();
		UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		int updateByExample = userInformationMapper.updateByExample(user, example);
		return updateByExample != 0;
	}

	@Override
	public int countByUserName(@NonNull String username) {
		UserInformationExample example = new UserInformationExample();
		UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		return (int) userInformationMapper.countByExample(example);
	}

	@Override
	public boolean updateUserInformation(@NonNull Integer uid, @NonNull UserInformation user) throws NotFoundUidException {
		if(countByUid(uid) == 0) {
			throw new NotFoundUidException("该用户不存在");
		}
		UserInformationExample example = new UserInformationExample();
		UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		boolean b = userInformationMapper.updateByExampleSelective(user, example) != 0;
		if(b) {
			new Thread(()->{
				try {
					UserInformation userinfor = userInformationMapper.selectUserByUid(uid);
					Map<String, Object> map = esDao.queryByTableNameAndId(EsIndex.USER, userinfor.getId());
					EsUserInformation esuser = ElasticsearchUtils.
							getObjectByMapAndClass(map, EsUserInformation.class);
					esuser.setUsername(user.getUsername() == null ? userinfor.getUsername() : user.getUsername());
					esDao.update(esuser, user.getId());
				} catch (Exception e) {
				}
			}).start();
		}
		return b;
	}

	@Override
	public List<UserInformation> selectUserInformations() {
		return userInformationMapper.selectByExample(new UserInformationExample());
	}
}
