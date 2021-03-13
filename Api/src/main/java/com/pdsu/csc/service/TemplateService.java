package com.pdsu.csc.service;

import com.pdsu.csc.exception.CodeSharingCommunityException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * <p>提供数据库交互的泛用方法</p>
 * <p>与数据库交互的事务 Service 必须实现该接口</p>
 * <li>T 指代该 Service 所处理的数据类型
 * <li>E 指代该 Service 所处理的数据的封装条件
 * <li>ORDER_INCREMENTAL, 数据递增排序
 * <li>ORDER_DECREASING, 数据递减排序
 * </li>
 * @author 半梦
 * @create 2020-04-11 19:12
 */
public interface TemplateService <T, E> {

    /**
     * 递增排序
     */
    String ORDER_INCREMENTAL = " DESC";

    /**
     * 递减排序
     */
    String ORDER_DECREASING = " ASC";

    /**
     * 插入数据
     */
    boolean insert(@NonNull T t) throws CodeSharingCommunityException;

    /**
     * 根据条件删除数据
     */
    boolean deleteByExample(@NonNull E example);

    /**
     * 根据条件更新数据
     */
    boolean updateByExample(@NonNull T t, @Nullable E example);

    /**
     * 根据条件查询数据（单个
     */
    @Nullable
    default T selectByExample(@Nullable E example) {
        List<T> ts = selectListByExample(example);
        return ts.size() == 0 ? null : ts.get(0);
    }

    /**
     * 查询所有
     * 如没有数据则返回一个空的集合（EmptyList
     * @see java.util.Collections#emptyList
     */
    @NonNull
    default List<T> selectAll() {
        return selectListByExample(null);
    }

    /**
     * 根据条件查询
     * 如没有数据则返回一个空的集合（EmptyList
     */
    @NonNull
    List<T> selectListByExample(@Nullable E example);

    /**
     * 判断数据个数
     */
    long countByExample(@Nullable E example);

    /**
     * 拼接查询条件
     * @param key 排序所需要根据的 key
     * @param order 排序规则
     * @return 拼接好的规则
     */
    @NonNull
    default String splicing(@NonNull String key, @NonNull String order) {
        return key + order;
    }

}
