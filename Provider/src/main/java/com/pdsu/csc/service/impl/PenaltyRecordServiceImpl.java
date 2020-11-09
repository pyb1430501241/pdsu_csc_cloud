package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.PenaltyRecord;
import com.pdsu.csc.dao.PenaltyRecordMapper;
import com.pdsu.csc.service.PenaltyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


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

}
