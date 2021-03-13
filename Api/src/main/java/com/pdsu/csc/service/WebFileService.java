package com.pdsu.csc.service;

import com.pdsu.csc.bean.WebFile;
import com.pdsu.csc.bean.WebFileExample;
import com.pdsu.csc.exception.web.es.InsertException;
import com.pdsu.csc.exception.web.file.UidAndTitleRepetitionException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 
 * @author 半梦
 *
 */
public interface WebFileService extends TemplateService<WebFile, WebFileExample> {

	/**
	 * 查询所有文件
	 * @param uid
	 * @param title
	 * @return
	 */
	public WebFile selectFileByUidAndTitle(@NonNull Integer uid, @NonNull String title);
	
	/**
	 * 查询符合条件的文件是否存在
	 * @param uid
	 * @param title
	 * @return
	 */
	public boolean countByUidAndTitle(@NonNull Integer uid, @NonNull String title);

	/**
	 * 获取用户上传文件总量
	 * @param uid
	 * @return
	 */
	public Integer countByUid(@NonNull Integer uid);

	/**
	 * 获取文件首页数据
	 * @return
	 */
	public List<WebFile> selectFilesOrderByTime(@Nullable Integer p);

	/**
	 * 根据fileid获取文件呢信息
	 * @param fileids
	 * @return
	 */
    public List<WebFile> selectFilesByFileIds(@NonNull List<Integer> fileids);
}
