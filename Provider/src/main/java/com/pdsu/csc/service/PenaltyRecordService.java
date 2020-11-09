package com.pdsu.csc.service;

import com.pdsu.csc.bean.PenaltyRecord;
import org.springframework.lang.NonNull;

/**
 * 该接口提供处罚相关的方法
 * @author 半梦
 *
 */
public interface PenaltyRecordService {
	
	//根据学号获取处罚信息
	public PenaltyRecord selectPenaltyRecordByUid(@NonNull Integer uid);
	
}
