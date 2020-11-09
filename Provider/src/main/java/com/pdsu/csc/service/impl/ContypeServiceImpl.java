package com.pdsu.csc.service.impl;

import java.util.List;

import com.pdsu.csc.bean.Contype;
import com.pdsu.csc.bean.ContypeExample;
import com.pdsu.csc.dao.ContypeMapper;
import com.pdsu.csc.service.ContypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 半梦
 */
@Service("contypeService")
public class ContypeServiceImpl implements ContypeService {

	@Autowired
	private ContypeMapper contypeMapper;
	
	@Override
	public List<Contype> selectContypes() {
		return contypeMapper.selectByExample(new ContypeExample());
	}

}
