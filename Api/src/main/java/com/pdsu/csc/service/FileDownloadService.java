package com.pdsu.csc.service;

import java.util.List;

import com.pdsu.csc.bean.FileDownload;
import com.pdsu.csc.bean.FileDownloadExample;
import org.springframework.lang.NonNull;

/**
 * @author 半梦
 */
public interface FileDownloadService extends TemplateService<FileDownload, FileDownloadExample> {
	
	/**
	 * 获取用户文件被下载量
	 * @param uid
	 * @return
	 */
	public Integer countByBid(@NonNull Integer uid);

	/**
	 * 获取文件下载量
	 * @param fileids
	 * @return
	 */
	public List<Integer> selectDownloadsByFileIds(@NonNull List<Integer> fileids);
	
}
	