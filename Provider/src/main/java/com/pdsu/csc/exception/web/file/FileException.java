package com.pdsu.csc.exception.web.file;


import com.pdsu.csc.exception.web.WebException;

/**
 * 文件相关异常
 * @author 半梦
 *
 */
public class FileException extends WebException {

	private static final long serialVersionUID = 1L;

	
	public FileException(String ex) {
		super(ex);
	}
}
