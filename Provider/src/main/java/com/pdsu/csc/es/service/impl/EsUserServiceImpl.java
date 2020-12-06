package com.pdsu.csc.es.service.impl;

import com.pdsu.csc.bean.EsIndex;
import com.pdsu.csc.bean.EsUserInformation;
import com.pdsu.csc.es.dao.EsDao;
import com.pdsu.csc.es.service.EsService;
import com.pdsu.csc.exception.web.es.QueryException;
import com.pdsu.csc.utils.ElasticsearchUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * es user 相关
 * @author 半梦
 *
 */
@Service("esUserService")
public class EsUserServiceImpl implements EsService<EsUserInformation> {

	@Autowired
	private EsDao esDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EsUserInformation> queryByText(String text) throws QueryException {
		BoolQueryBuilder bool = new BoolQueryBuilder();
		bool.should(QueryBuilders.matchQuery("username", text));
		
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("username");
		highlightBuilder.preTags("<font color='red'>");
		highlightBuilder.postTags("</font>");
		
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(bool);
		builder.highlighter(highlightBuilder);
		
		SearchRequest request = new SearchRequest();
		
		request.indices(EsIndex.USER.getName());
		request.source(builder);
		SearchHits hits;
		try {
			hits = esDao.queryBySearchRequest(request);
		} catch (QueryException e1) {
			throw new QueryException("查询用户失败");
		}
		SearchHit[] searchHits = hits.getHits();
		try {
			return ElasticsearchUtils.getObjectListBySearchHit(searchHits, EsUserInformation.class);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			throw new QueryException("解析用户失败");
		}
	}

}
