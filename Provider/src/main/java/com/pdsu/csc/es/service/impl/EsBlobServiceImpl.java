package com.pdsu.csc.es.service.impl;

import com.pdsu.csc.bean.EsBlobInformation;
import com.pdsu.csc.bean.EsIndex;
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
 * es blob相关
 * @author 半梦
 *
 */
@Service("esBlobService")
public class EsBlobServiceImpl implements EsService<EsBlobInformation> {

	@Autowired
	private EsDao esDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EsBlobInformation> queryByText(String text) throws QueryException {
		BoolQueryBuilder bool = new BoolQueryBuilder();
		bool.should(QueryBuilders.matchQuery("title", text));
		bool.should(QueryBuilders.matchQuery("description", text));
		
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("title");
		highlightBuilder.field("description");
		highlightBuilder.preTags("<font color='red'>");
		highlightBuilder.postTags("</font>");
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(bool);
		builder.highlighter(highlightBuilder);
		
		SearchRequest request = new SearchRequest();
		
		request.indices(EsIndex.BLOB.getName());
		request.source(builder);
		SearchHits hits;
		try {
			hits = esDao.queryBySearchRequest(request);
		} catch (QueryException e1) {
			throw new QueryException("查询博客失败");
		}
		SearchHit[] searchHits = hits.getHits();
		try {
			return ElasticsearchUtils.getObjectListBySearchHit(searchHits, EsBlobInformation.class);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			throw new QueryException("解析博客失败");
		}
	}

}
