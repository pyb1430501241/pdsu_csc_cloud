package com.pdsu.csc.utils;

import org.elasticsearch.search.SearchHit;
import org.springframework.lang.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 常用的工具类
 * @author 半梦
 *
 */
public final class ElasticsearchUtils {

	/**
	 * 拼装 SearchHit 成对应的实体类
	 */
	@NonNull
	public static List<?> getObjectBySearchHit(@NonNull SearchHit [] searchHits, @NonNull Class<?> clazz) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<?> constructor = ConstructorUtils.getConstructorByClass(clazz);
		if(constructor == null) {
			throw new NoSuchMethodException("无此构造方法");
		}
		return getListBySearchHit(searchHits, constructor);
	}

	/**
	 * 根据类来拼装该类的对象
	 * 仅支持 EsUserInformation, EsBlobInformation, EsFileInformation
	 */
	@NonNull
	public static Object getObjectByMapAndClass(@NonNull Map<String, Object> map, @NonNull Class<?> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Constructor<?> constructor = ConstructorUtils.getConstructorByClass(clazz);
		if(constructor == null) {
			throw new NoSuchMethodException("无此构造方法");
		}
		return ConstructorUtils.getObjectByMap(map, constructor);
	}

	/**
	 * 把数据封装成集合
	 */
	@NonNull
	public static List<Object> getListBySearchHit(@NonNull SearchHit[] searchHits, @NonNull Constructor<?> constructor) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		List<Object> list = new ArrayList<>();
		for(SearchHit hit : searchHits) {
			Map<String, Object> map = hit.getSourceAsMap();
			list.add(ConstructorUtils.getObjectByMap(map, constructor));
		}
		return list;
	}


}
