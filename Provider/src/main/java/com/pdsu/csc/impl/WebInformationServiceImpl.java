package com.pdsu.csc.impl;

import com.github.pagehelper.PageHelper;
import com.pdsu.csc.bean.*;
import com.pdsu.csc.dao.*;
import com.pdsu.csc.es.dao.EsDao;
import com.pdsu.csc.exception.web.DeleteInforException;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.es.InsertException;
import com.pdsu.csc.service.UserService;
import com.pdsu.csc.service.WebInformationService;
import com.pdsu.csc.utils.ElasticsearchUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理博客页面
 * @author 半梦
 *
 */
@Log4j2
@Service("webInformationService")
public class WebInformationServiceImpl implements
		WebInformationService, UserService {

	private WebInformationMapper webInformationMapper;
	
	private UserInformationMapper userInformationMapper;
	
	private WebThumbsMapper webThumbsMapper;
	
	private MyCollectionMapper myCollectionMapper;
	
	private VisitInformationMapper visitInformationMapper;
	
	private WebCommentMapper webCommentMapper;
	
	private WebCommentReplyMapper webCommentReplyMapper;
	
	private WebLabelControlMapper webLabelControlMapper;
	
	private EsDao esDao;
	
	
	private String getDescriptionByWebData(String webdata) {
		Pattern p = Pattern.compile("[/#`~\r\n\t *]");
		Matcher m = p.matcher(webdata);
		String str = m.replaceAll("");
		return str.length() > 100 ? str.substring(0, 100) : str;
	}
	
	/**
	 * 插入一个网页信息
	 * 	由于 ES 服务器太辣鸡！日常GG, 所以无奈直接摒弃 ES 出错时的
	 * 	事务相关操作, 如发生 ES 相关异常, 但数据库插入成功, 即认为
	 * 	插入成功, 虽抛出 InsertException 异常, 但在异常中添加博客
	 * 	的 ID, 即无论 ES 插入是否成功, 对于返回值无任何影响, 表面
	 * 	上的成功。
	 *
	 * 	如想修复, 只需在 ES 插入失败时, 执行事务 rollback
	 * 	然后修改 ParentHandler 里处理 InsertException 异常
	 * 	的方法。
	 */
	@Override
	public boolean insert(@NonNull WebInformation information) throws InsertException {
		if(webInformationMapper.insertSelective(information) > 0) {
			EsBlobInformation blob = new EsBlobInformation(information.getId(),
						getDescriptionByWebData(information.getWebDataString()), information.getTitle());
			if(esDao.insert(blob, information.getId())) {
				new Thread(()->{
					try {
						UserInformation user = userInformationMapper.selectUserByUid(information.getUid());
						Map<String, Object> map = esDao.queryByTableNameAndId(EsIndex.USER, user.getId());
						EsUserInformation esUser = ElasticsearchUtils.
								getObjectByMapAndClass(map, EsUserInformation.class);
						esUser.setBlobnum(esUser.getBlobnum() + 1);
						esDao.update(esUser, user.getId());
					} catch (Exception e) {
						log.error("es相关操作出现异常", e);
					}
				}).start();
			}
			return true;
		}
		return false;
	}

	/*
	 * 删除一个网页信息
	 */
	@Override
	public boolean deleteById(@NonNull Integer id) throws NotFoundBlobIdException, DeleteInforException {
		if(!isExistByWebId(id)) {
			throw new NotFoundBlobIdException();
		}
		
		/*
		 * 删除文章对应的标签信息
		 */
		WebLabelControlExample webLabelControlExample = new WebLabelControlExample();
		com.pdsu.csc.bean.WebLabelControlExample.Criteria webLabelControlCriteria = webLabelControlExample.createCriteria();
		webLabelControlCriteria.andWidEqualTo(id);
		long webLabelControlCount = webLabelControlMapper.countByExample(webLabelControlExample);
		if(webLabelControlMapper.deleteByExample(webLabelControlExample) != webLabelControlCount) {
			throw new DeleteInforException("删除网页标签信息失败");
		}
		
		/*
		 * 删除网页相关的评论信息
		 */
		WebCommentReplyExample webCommentReplyExample = new WebCommentReplyExample();
		com.pdsu.csc.bean.WebCommentReplyExample.Criteria criteria = webCommentReplyExample.createCriteria();
		criteria.andWidEqualTo(id);
		long webCommentReplyCount = webCommentReplyMapper.countByExample(webCommentReplyExample);
		if(webCommentReplyMapper.deleteByExample(webCommentReplyExample) != webCommentReplyCount) {
			throw new DeleteInforException("删除网页评论信息失败");
		}
		
		WebCommentExample webCommentExample = new WebCommentExample();
		com.pdsu.csc.bean.WebCommentExample.Criteria webCommentCriteria = webCommentExample.createCriteria();
		webCommentCriteria.andWidEqualTo(id);
		long webCommentCount = webCommentMapper.countByExample(webCommentExample);
		if(webCommentMapper.deleteByExample(webCommentExample) != webCommentCount) {
			throw new DeleteInforException("删除网页评论信息失败");
		}
		
		/*
		 * 删除和网页相关的收藏信息 
		 */
		MyCollectionExample myCollectionExample = new MyCollectionExample();
		com.pdsu.csc.bean.MyCollectionExample.Criteria myCollectionCriteria1 = myCollectionExample.createCriteria();
		myCollectionCriteria1.andWidEqualTo(id);
		long mycollectionCount = myCollectionMapper.countByExample(myCollectionExample);
		if(myCollectionMapper.deleteByExample(myCollectionExample) != mycollectionCount) {
			throw new DeleteInforException("删除网页收藏信息失败");
		}

		/*
		 *删除和网页相关的访问信息
		 */
		VisitInformationExample visitInformationExample = new VisitInformationExample();
		com.pdsu.csc.bean.VisitInformationExample.Criteria visitInformationCriteria1 = visitInformationExample.createCriteria();
		visitInformationCriteria1.andWidEqualTo(id);
		long visitCount = visitInformationMapper.countByExample(visitInformationExample);
		if(visitInformationMapper.deleteByExample(visitInformationExample) != visitCount) {
			throw new DeleteInforException("删除网页访问信息失败");
		}

		/* 删除和网页相关的点赞信息 */
		WebThumbsExample webThumbsExample = new WebThumbsExample();
		com.pdsu.csc.bean.WebThumbsExample.Criteria webThumbsCriteria1 = webThumbsExample.createCriteria();
		webThumbsCriteria1.andWebidEqualTo(id);
		long webThumbsCount = webThumbsMapper.countByExample(webThumbsExample);
		if(webThumbsMapper.deleteByExample(webThumbsExample) != webThumbsCount) {
			throw new DeleteInforException("删除用户点赞信息失败");
		}
		WebInformation information = webInformationMapper.selectByPrimaryKey(id);
		int i = webInformationMapper.deleteByPrimaryKey(id);
		if(i > 0) {
			new Thread(()->{
				try {
					UserInformation user = userInformationMapper.selectUserByUid(information.getUid());
					Map<String, Object> map = esDao.queryByTableNameAndId(EsIndex.USER, user.getId());
					EsUserInformation esUser = ElasticsearchUtils.
							getObjectByMapAndClass(map, EsUserInformation.class);
					esUser.setBlobnum(esUser.getBlobnum()-1);
					esDao.update(esUser, user.getId());
				} catch (Exception e) {
					log.error("es 相关操作出现异常", e);
				}
			}).start();
			return true;
		} else {
			throw new DeleteInforException("删除网页失败");
		}
	}

	@Override
	public WebInformation selectById(@NonNull Integer id) throws NotFoundBlobIdException{
		WebInformation key = webInformationMapper.selectByPrimaryKey(id);
		if(key == null) {
			throw new NotFoundBlobIdException();
		}
		return key;
	}

	@Override
	public List<WebInformation> selectWebInformationOrderByTimetest(Integer p) {
		WebInformationExample example = new WebInformationExample();
		example.setOrderByClause(splicing("sub_time", ORDER_INCREMENTAL));
		PageHelper.startPage(p, 10);
		return selectListByExample(example);
	}

	@Override
	public List<WebInformation> selectWebInformationsByUid(@NonNull Integer uid, Integer p) {
		WebInformationExample example = new WebInformationExample();
		WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		example.setOrderByClause(splicing("sub_time", ORDER_INCREMENTAL));
		if(p != null) {
			PageHelper.startPage(p, 15);
		}
		return selectListByExample(example);
	}

	/*
	 *更新文章  
	 */
	@Override
	public boolean updateByWebId(@NonNull WebInformation web) {
		WebInformationExample example = new WebInformationExample();
		WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(web.getId());
		boolean b = updateByExample(web, example);
		if(b) {
			new Thread(()->{
				try {
					Map<String, Object> map = esDao.queryByTableNameAndId(EsIndex.BLOB, web.getId());
					EsBlobInformation blob = ElasticsearchUtils
							.getObjectByMapAndClass(map, EsBlobInformation.class);
					blob.setDescription(getDescriptionByWebData(web.getWebDataString()));
					esDao.update(blob, blob.getWebid());
				} catch (Exception e) {
					log.error("es相关操作出现异常", e);
				}
			}).start();
		}
		return b;
	}

	@Override
	public Integer countOriginalByUidAndContype(@NonNull Integer uid, @NonNull Integer contype) {
		WebInformationExample example = new WebInformationExample();
		WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andContypeEqualTo(contype);
		criteria.andUidEqualTo(uid);
		return (int) countByExample(example);
	}

	@Override
	public List<WebInformation> selectWebInformationsByIds(@Nullable List<Integer> webids, boolean flag, Integer p) {
		if(Objects.isNull(webids) || webids.size() == 0) {
			return Collections.emptyList();
		}
		WebInformationExample example = new WebInformationExample();
		WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(webids);
		if(flag) {
			example.setOrderByClause(splicing("sub_time", ORDER_INCREMENTAL));
		}
		if(p != null) {
			PageHelper.startPage(p, 10);
		}
		return selectListByExample(example);
	}

	@Override
	public boolean deleteByExample(@Nullable WebInformationExample example) {
		return webInformationMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull WebInformation webInformation, @Nullable WebInformationExample example) {
		return webInformationMapper.updateByExampleSelective(webInformation, example) > 0;
	}

	@Override
	@NonNull
	public List<WebInformation> selectListByExample(@Nullable WebInformationExample example) {
		return webInformationMapper.selectByExample(example);
	}

	@Override
	public long countByExample(@Nullable WebInformationExample example) {
		return webInformationMapper.countByExample(example);
	}

	@Override
	public boolean isExistByWebId(@NonNull Integer webId) {
		WebInformationExample example = new WebInformationExample();
		WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(webId);
		return webInformationMapper.countByExample(example) > 0;
	}

	@Override
	public boolean isExistByUid(@NonNull Integer uid) {
		UserInformationExample example = new UserInformationExample();
		com.pdsu.csc.bean.UserInformationExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return userInformationMapper.countByExample(example) > 0;
	}

	@Autowired
	public WebInformationServiceImpl(WebInformationMapper webInformationMapper, UserInformationMapper userInformationMapper, WebThumbsMapper webThumbsMapper, MyCollectionMapper myCollectionMapper, VisitInformationMapper visitInformationMapper, WebCommentMapper webCommentMapper, WebCommentReplyMapper webCommentReplyMapper, WebLabelControlMapper webLabelControlMapper, EsDao esDao) {
		this.webInformationMapper = webInformationMapper;
		this.userInformationMapper = userInformationMapper;
		this.webThumbsMapper = webThumbsMapper;
		this.myCollectionMapper = myCollectionMapper;
		this.visitInformationMapper = visitInformationMapper;
		this.webCommentMapper = webCommentMapper;
		this.webCommentReplyMapper = webCommentReplyMapper;
		this.webLabelControlMapper = webLabelControlMapper;
		this.esDao = esDao;
	}
}
