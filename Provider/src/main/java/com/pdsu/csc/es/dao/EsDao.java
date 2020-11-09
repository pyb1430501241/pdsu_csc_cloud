package com.pdsu.csc.es.dao;

import java.util.Map;

import com.pdsu.csc.exception.web.es.InsertException;
import com.pdsu.csc.exception.web.es.QueryException;
import com.pdsu.csc.exception.web.es.UpdateException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.SearchHits;

/**
 * es dao 层
 * @author 半梦
 *
 */
public interface EsDao {

	/**
	 * 从 es 查询指定数据
	 * @param request
	 * @return
	 * @throws QueryException
	 */
	public SearchHits queryBySearchRequest(SearchRequest request)throws QueryException;
	
	/**
	 * 往 es 插入数据
	 * @param ob
	 * @param id
	 * @return
	 * @throws InsertException
	 */
	public boolean insert(Object ob, Integer id) throws InsertException;
	
	public boolean update(Object ob, Integer id) throws UpdateException;
	
	public Map<String, Object> queryByTableNameAndId(String str, Integer id) throws QueryException;
	
}
