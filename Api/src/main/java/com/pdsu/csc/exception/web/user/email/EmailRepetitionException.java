package com.pdsu.csc.exception.web.user.email;

import com.pdsu.csc.exception.web.user.UserException;

/**
 * 邮箱重复
 * @author 半梦
 *
 */
public class EmailRepetitionException extends UserException {

	public EmailRepetitionException(String exceptiopn) {
		super(exceptiopn);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
