package com.pdsu.csc.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pdsu.csc.bean.WebLabelControl;
import com.pdsu.csc.bean.WebLabelControlExample;
import com.pdsu.csc.dao.WebLabelControlMapper;
import com.pdsu.csc.service.WebLabelControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


/**
 * @author 半梦
 */
@Service("webLabelControlService")
public class WebLabelControlServiceImpl implements WebLabelControlService {

	@Autowired
	private WebLabelControlMapper webLabelControlMapper;
	
	@Override
	public boolean insert(@NonNull Integer webid, @NonNull List<Integer> labelList) {
		for (Integer labelid : labelList) {
			webLabelControlMapper.insertSelective(new WebLabelControl(webid, labelid));
		}
		return true;
	}

	@Override
	public boolean deleteByWebId(@NonNull Integer webid) {
		WebLabelControlExample example = new WebLabelControlExample();
		WebLabelControlExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		return webLabelControlMapper.deleteByExample(example) > 0;
	}

	@Override
	public List<Integer> selectLabelIdByWebId(@NonNull Integer webid) {
		WebLabelControlExample example = new WebLabelControlExample();
		WebLabelControlExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		List<WebLabelControl> list = webLabelControlMapper.selectByExample(example);
		List<Integer> labelIds = new ArrayList<>();
		for (WebLabelControl label : list) {
			labelIds.add(label.getLid());
		}
		return labelIds;
	}

	@Override
	public List<Integer> selectWebIdsByLid(@NonNull Integer lid) {
		WebLabelControlExample example = new WebLabelControlExample();
		WebLabelControlExample.Criteria criteria = example.createCriteria();
		criteria.andLidEqualTo(lid);
		List<Integer> webids = new ArrayList<Integer>();
		for (WebLabelControl label : webLabelControlMapper.selectByExample(example)) {
			webids.add(label.getWid());
		}
		return webids;
	}

}
