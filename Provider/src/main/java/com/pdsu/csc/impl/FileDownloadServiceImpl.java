package com.pdsu.csc.impl;

import java.util.ArrayList;
import java.util.List;

import com.pdsu.csc.bean.FileDownload;
import com.pdsu.csc.bean.FileDownloadExample;
import com.pdsu.csc.dao.FileDownloadMapper;
import com.pdsu.csc.service.FileDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * @author 半梦
 */
@Service("fileDownloadService")
public class FileDownloadServiceImpl implements FileDownloadService {

	@Autowired
	private FileDownloadMapper fileDownloadMapper;
	
	@Override
	public boolean insert(@NonNull FileDownload fileDownload) {
		return fileDownloadMapper.insertSelective(fileDownload) > 0;
	}

	@Override
	public Integer countByBid(@NonNull Integer uid) {
		FileDownloadExample example = new FileDownloadExample();
		FileDownloadExample.Criteria criteria = example.createCriteria();
		criteria.andBidEqualTo(uid);
		return (int) fileDownloadMapper.countByExample(example);
	}

	@Override
	public List<Integer> selectDownloadsByFileIds(@NonNull List<Integer> fileids) {
		if(fileids == null || fileids.size() == 0) {
			return new ArrayList<Integer>();
		}
		FileDownloadExample example = new FileDownloadExample();
		FileDownloadExample.Criteria criteria = example.createCriteria();
		List<Integer> list = new ArrayList<Integer>();
		for (Integer fileid : fileids) {
			criteria.andFileidEqualTo(fileid);
			list.add((int)fileDownloadMapper.countByExample(example));
		}
		return list;
	}

}
