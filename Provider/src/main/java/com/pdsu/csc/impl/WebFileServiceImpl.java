package com.pdsu.csc.impl;

import com.github.pagehelper.PageHelper;
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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
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
			esDao.insert(file, webFile.getId());
			return true;
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
		return selectByExample(example);
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
		return countByExample(example) > 0;
	}

	@Override
	public Integer countByUid(@NonNull Integer uid) {
		WebFileExample example = new WebFileExample();
		WebFileExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return (int) countByExample(example);
	}

	@Override
	public List<WebFile> selectFilesOrderByTime(Integer p) {
		WebFileExample example = new WebFileExample();
		example.setOrderByClause("creattime DESC");
		if(p != null) {
			PageHelper.startPage(p, 15);
		}
		return selectListByExample(example);
	}

	@Override
	public List<WebFile> selectFilesByFileIds(@NonNull List<Integer> fileids) {
		if(fileids.size() == 0) {
			return new ArrayList<>();
		}
		WebFileExample example = new WebFileExample();
		WebFileExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(fileids);
		return selectListByExample(example);
	}

	@Override
	public boolean deleteByExample(@Nullable WebFileExample example) {
		return webFileMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull WebFile webFile, @Nullable WebFileExample example) {
		return webFileMapper.updateByExampleSelective(webFile, example) > 0;
	}

	@Override
	@NonNull
	public List<WebFile> selectListByExample(@Nullable WebFileExample example) {
		return webFileMapper.selectByExample(example);
	}

	@Override
	public long countByExample(@Nullable WebFileExample example) {
		return webFileMapper.countByExample(example);
	}

}
