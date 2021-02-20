package com.pdsu.csc.zuulDao;

import com.pdsu.csc.bean.MyImage;
import com.pdsu.csc.bean.MyImageExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyImageMapper {
    long countByExample(MyImageExample example);

    int deleteByExample(MyImageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MyImage record);

    int insertSelective(MyImage record);

    List<MyImage> selectByExample(MyImageExample example);

    MyImage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MyImage record, @Param("example") MyImageExample example);

    int updateByExample(@Param("record") MyImage record, @Param("example") MyImageExample example);

    int updateByPrimaryKeySelective(MyImage record);

    int updateByPrimaryKey(MyImage record);
}