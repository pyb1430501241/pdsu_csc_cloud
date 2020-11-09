package com.pdsu.csc.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pdsu.csc.bean.WebLabel;
import com.pdsu.csc.bean.WebLabelExample;
import com.pdsu.csc.dao.WebLabelMapper;
import com.pdsu.csc.service.WebLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


/**
 * @author 半梦
 */
@Service("webLabelService")
public class WebLabelServiceImpl implements WebLabelService {

	@Autowired
	private WebLabelMapper webLabelMapper;
	
	@Override
	public List<WebLabel> selectLabel() {
		return webLabelMapper.selectByExample(new WebLabelExample());
	}

	@Override
	public List<WebLabel> selectByLabelIds(@NonNull List<Integer> labelids) {
		if(labelids == null || labelids.size() == 0) {
			return new ArrayList<WebLabel>();
		}
		WebLabelExample example = new WebLabelExample();
		WebLabelExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(labelids);
		return webLabelMapper.selectByExample(example);
	}

}
