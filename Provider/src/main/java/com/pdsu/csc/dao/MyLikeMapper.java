package com.pdsu.csc.dao;

import java.util.List;

import com.pdsu.csc.bean.MyLike;
import com.pdsu.csc.bean.MyLikeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyLikeMapper {
    long countByExample(MyLikeExample example);

    int deleteByExample(MyLikeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MyLike record);

    int insertSelective(MyLike record);

    List<MyLike> selectByExample(MyLikeExample example);

    MyLike selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MyLike record, @Param("example") MyLikeExample example);

    int updateByExample(@Param("record") MyLike record, @Param("example") MyLikeExample example);

    int updateByPrimaryKeySelective(MyLike record);

    int updateByPrimaryKey(MyLike record);
    
	List<Integer> selectLikeIdByUid(Integer uid);

	List<Integer> selectUidByLikeId(Integer likeId);
}