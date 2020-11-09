package com.pdsu.csc.dao;

import java.util.List;

import com.pdsu.csc.bean.PenaltyRecord;
import com.pdsu.csc.bean.PenaltyRecordExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyRecordMapper {
    long countByExample(PenaltyRecordExample example);

    int deleteByExample(PenaltyRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PenaltyRecord record);

    int insertSelective(PenaltyRecord record);

    List<PenaltyRecord> selectByExample(PenaltyRecordExample example);

    PenaltyRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PenaltyRecord record, @Param("example") PenaltyRecordExample example);

    int updateByExample(@Param("record") PenaltyRecord record, @Param("example") PenaltyRecordExample example);

    int updateByPrimaryKeySelective(PenaltyRecord record);

    int updateByPrimaryKey(PenaltyRecord record);

	PenaltyRecord selectPenaltyRecordByUid(@Param("uid") Integer uid);
}