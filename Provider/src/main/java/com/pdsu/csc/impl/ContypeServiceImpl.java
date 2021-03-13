package com.pdsu.csc.impl;

import java.util.List;

import com.pdsu.csc.bean.Contype;
import com.pdsu.csc.bean.ContypeExample;
import com.pdsu.csc.dao.ContypeMapper;
import com.pdsu.csc.service.ContypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;

/**
 * @author 半梦
 */
@Service("contypeService")
public class ContypeServiceImpl implements ContypeService {

	@Autowired
	private ContypeMapper contypeMapper;
	
	@Override
	@NonNull
	public List<Contype> selectContypes() {
		return selectAll();
	}

	@Override
	public boolean insert(@NonNull Contype contype) {
		return contypeMapper.insertSelective(contype) > 0;
	}

	@Override
	public boolean deleteByExample(@Nullable ContypeExample example) {
		return contypeMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull Contype contype, @Nullable ContypeExample example) {
		return contypeMapper.updateByExampleSelective(contype, example) > 0;
	}

	@Override
	@NonNull
	public List<Contype> selectListByExample(@Nullable ContypeExample example) {
		return contypeMapper.selectByExample(example);
	}

	@Override
	public long countByExample(@Nullable ContypeExample example) {
		return contypeMapper.countByExample(example);
	}


}
