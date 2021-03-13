package com.pdsu.csc.impl;

import com.pdsu.csc.bean.PenaltyRecord;
import com.pdsu.csc.bean.PenaltyRecordExample;
import com.pdsu.csc.dao.PenaltyRecordMapper;
import com.pdsu.csc.service.PenaltyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author 半梦
 *
 */
@Service("penaltyRecordService")
public class PenaltyRecordServiceImpl implements PenaltyRecordService {

	@Autowired
	private PenaltyRecordMapper penaltyRecordMapper;
	
	@Override
	public PenaltyRecord selectPenaltyRecordByUid(@NonNull Integer uid) {
		return penaltyRecordMapper.selectPenaltyRecordByUid(uid);
	}


	@Override
	public boolean insert(@NonNull PenaltyRecord penaltyRecord) {
		return penaltyRecordMapper.insertSelective(penaltyRecord) > 0;
	}

	@Override
	public boolean deleteByExample(@Nullable PenaltyRecordExample example) {
		return penaltyRecordMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull PenaltyRecord penaltyRecord, @Nullable PenaltyRecordExample example) {
		return penaltyRecordMapper.updateByExampleSelective(penaltyRecord, example) > 0;
	}

	@Override
	@NonNull
	public List<PenaltyRecord> selectListByExample(@Nullable PenaltyRecordExample example) {
		return penaltyRecordMapper.selectByExample(example);
	}

	@Override
	public long countByExample(@Nullable PenaltyRecordExample example) {
		return penaltyRecordMapper.countByExample(example);
	}

}
