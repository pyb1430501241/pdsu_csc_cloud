package com.pdsu.csc.dao;

import com.pdsu.csc.bean.WebInformation;
import java.util.List;

import com.pdsu.csc.bean.WebInformationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebInformationMapper {
    long countByExample(WebInformationExample example);

    int deleteByExample(WebInformationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebInformation record);

    int insertSelective(WebInformation record);

    List<WebInformation> selectByExampleWithBLOBs(WebInformationExample example);

    List<WebInformation> selectByExample(WebInformationExample example);

    WebInformation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebInformation record, @Param("example") WebInformationExample example);

    int updateByExampleWithBLOBs(@Param("record") WebInformation record, @Param("example") WebInformationExample example);

    int updateByExample(@Param("record") WebInformation record, @Param("example") WebInformationExample example);

    int updateByPrimaryKeySelective(WebInformation record);

    int updateByPrimaryKeyWithBLOBs(WebInformation record);

    int updateByPrimaryKey(WebInformation record);
}