package com.pdsu.csc.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.pdsu.csc.bean.WebLabel;
import com.pdsu.csc.bean.WebLabelExample;
import com.pdsu.csc.dao.WebLabelMapper;
import com.pdsu.csc.exception.CodeSharingCommunityException;
import com.pdsu.csc.service.WebLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;


/**
 * @author 半梦
 */
@Service("webLabelService")
public class WebLabelServiceImpl implements WebLabelService {

	@Autowired
	private WebLabelMapper webLabelMapper;
	
	@Override
	public List<WebLabel> selectLabel(Integer p) {
		if(p != null) {
			PageHelper.startPage(p, 16);
		}
		return selectListByExample(new WebLabelExample());
	}

	@Override
	@NonNull
	public List<WebLabel> selectByLabelIds(@Nullable List<Integer> labelids, Integer p) {
		if(labelids == null || labelids.size() == 0) {
			return new ArrayList<>();
		}
		WebLabelExample example = new WebLabelExample();
		WebLabelExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(labelids);
		if(p != null) {
			PageHelper.startPage(p, 16);
		}
		return selectListByExample(example);
	}

	@Override
	public boolean insert(@Nonnull WebLabel webLabel) {
		return webLabelMapper.insertSelective(webLabel) > 0;
	}

	@Override
	public boolean deleteByExample(@Nullable WebLabelExample example) {
		return webLabelMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@Nonnull WebLabel webLabel, @Nullable WebLabelExample example) {
		return webLabelMapper.updateByExampleSelective(webLabel, example) > 0;
	}

	@Override
	@Nonnull
	public List<WebLabel> selectListByExample(@Nullable WebLabelExample example) {
		return webLabelMapper.selectByExample(example);
	}

	@Override
	public long countByExample(@Nullable WebLabelExample example) {
		return webLabelMapper.countByExample(example);
	}

}
