package com.pdsu.csc.service;

import com.pdsu.csc.bean.PenaltyRecord;
import com.pdsu.csc.bean.PenaltyRecordExample;
import org.springframework.lang.NonNull;

/**
 * 该接口提供处罚相关的方法
 * @author 半梦
 *
 */
public interface PenaltyRecordService extends TemplateService<PenaltyRecord, PenaltyRecordExample> {
	
	//根据学号获取处罚信息
	public PenaltyRecord selectPenaltyRecordByUid(@NonNull Integer uid);
	
}
