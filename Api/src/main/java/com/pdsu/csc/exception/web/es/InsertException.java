package com.pdsu.csc.exception.web.es;

/**
 * es 插入数据错误
 * @author Admin
 *
 */
public class InsertException extends EsException{

	private Integer id;

	private String name;

	public InsertException(String exception, Integer id, String name) {
		super(exception);
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public InsertException(String exception) {
		super(exception);
	}

	public InsertException(String exception, Integer id) {
		super(exception);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
