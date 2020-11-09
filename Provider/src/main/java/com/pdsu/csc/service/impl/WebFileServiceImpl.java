package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.EsFileInformation;
import com.pdsu.csc.bean.WebFile;
import com.pdsu.csc.bean.WebFileExample;
import com.pdsu.csc.dao.WebFileMapper;
import com.pdsu.csc.es.dao.EsDao;
import com.pdsu.csc.exception.web.es.InsertException;
import com.pdsu.csc.exception.web.file.UidAndTitleRepetitionException;
import com.pdsu.csc.service.WebFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("webFileService")
public class WebFileServiceImpl implements WebFileService {

	@Autowired
	private WebFileMapper webFileMapper;
	
	@Autowired
	private EsDao esDao;
	
	/**
	 * 插入一个文件记录
	 * @param webFile
	 * @return  true false
	 * @throws UidAndTitleRepetitionException
	 * @throws InsertException 
	 */
	@Override
	public boolean insert(@NonNull WebFile webFile) throws UidAndTitleRepetitionException, InsertException {
		if(countByUidAndTitle(webFile.getUid(), webFile.getTitle())) {
			throw new UidAndTitleRepetitionException("用户无法重复上传同名文件");
		}
		int t = webFileMapper.insertSelective(webFile);
		if(t > 0) {
			EsFileInformation file = new EsFileInformation(webFile.getDescription(),
					webFile.getTitle(), webFile.getId());
			return esDao.insert(file, webFile.getId());
		}
		return false;
	}

	/**
	 * 查询指定文件
	 * @param uid
	 * @param title
	 * @return
	 */
	@Override
	public WebFile selectFileByUidAndTitle(@NonNull Integer uid, @NonNull String title) {
		WebFileExample example = new WebFileExample();
		WebFileExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andTitleEqualTo(title);
		List<WebFile> list = webFileMapper.selectByExample(example);
		if(list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 查询符合条件的文件是否存在
	 * @param uid
	 * @param title
	 * @return
	 */
	@Override
	public boolean countByUidAndTitle(@NonNull Integer uid, @NonNull String title) {
		WebFileExample example = new WebFileExample();
		WebFileExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andTitleEqualTo(title);
		return webFileMapper.countByExample(example) <= 0 ? false : true;
	}

	@Override
	public Integer countByUid(@NonNull Integer uid) {
		WebFileExample example = new WebFileExample();
		WebFileExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return (int) webFileMapper.countByExample(example);
	}

	@Override
	public List<WebFile> selectFilesOrderByTime() {
		WebFileExample example = new WebFileExample();
		example.setOrderByClause("creattime DESC");
		return webFileMapper.selectByExample(example);
	}

	@Override
	public List<WebFile> selectFilesByFileIds(@NonNull List<Integer> fileids) {
		if(fileids == null || fileids.size() == 0) {
			return new ArrayList<>();
		}
		WebFileExample example = new WebFileExample();
		WebFileExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(fileids);
		return webFileMapper.selectByExample(example);
	}

}
