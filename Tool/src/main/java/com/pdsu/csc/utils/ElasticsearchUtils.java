package com.pdsu.csc.utils;

import com.pdsu.csc.bean.EsBlobInformation;
import org.elasticsearch.search.SearchHit;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * 常用的工具类
 * @author 半梦
 * @see BeanUtils
 */
public abstract class ElasticsearchUtils {

	/**
	 * 根据类来拼装该类的对象
	 */
	@NonNull
	public static <T> T getObjectByMapAndClass(@NonNull Map<String, Object> map, @NonNull Class<T> clazz) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		return BeanUtils.getObjectByMapAndClass(map, clazz);
	}

	/**
	 * 把数据封装成集合
	 */
	@NonNull
	public static <T> List<T> getObjectListBySearchHit(@NonNull SearchHit[] searchHits, @NonNull Class<T> clazz) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, NoSuchFieldException {
		List<T> list = new ArrayList<>();
		for(SearchHit hit : searchHits) {
			Map<String, Object> map = hit.getSourceAsMap();
			list.add(getObjectByMapAndClass(map, clazz));
		}
		return list;
	}

}
