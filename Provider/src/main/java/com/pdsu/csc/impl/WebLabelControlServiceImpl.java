package com.pdsu.csc.impl;

import java.util.ArrayList;
import java.util.List;

import com.pdsu.csc.bean.WebLabelControl;
import com.pdsu.csc.bean.WebLabelControlExample;
import com.pdsu.csc.dao.WebLabelControlMapper;
import com.pdsu.csc.exception.CodeSharingCommunityException;
import com.pdsu.csc.service.WebLabelControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
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
			insert(new WebLabelControl(webid, labelid));
		}
		return true;
	}

	@Override
	public boolean deleteByWebId(@NonNull Integer webid) {
		WebLabelControlExample example = new WebLabelControlExample();
		WebLabelControlExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		return deleteByExample(example);
	}

	@Override
	public List<Integer> selectLabelIdByWebId(@NonNull Integer webid) {
		WebLabelControlExample example = new WebLabelControlExample();
		WebLabelControlExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		List<WebLabelControl> list = selectListByExample(example);
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
		List<Integer> webids = new ArrayList<>();
		for (WebLabelControl label : selectListByExample(example)) {
			webids.add(label.getWid());
		}
		return webids;
	}

	@Override
	public boolean insert(@NonNull WebLabelControl webLabelControl) {
		return webLabelControlMapper.insertSelective(webLabelControl) > 0;
	}

	@Override
	public boolean deleteByExample(@Nullable WebLabelControlExample example) {
		return webLabelControlMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull WebLabelControl webLabelControl, @Nullable WebLabelControlExample example) {
		return webLabelControlMapper.updateByExampleSelective(webLabelControl, example) > 0;
	}

	@Override
	@NonNull
	public List<WebLabelControl> selectListByExample(@Nullable WebLabelControlExample example) {
		return webLabelControlMapper.selectByExample(example);
	}

	@Override
	public long countByExample(WebLabelControlExample example) {
		return webLabelControlMapper.countByExample(example);
	}

}
