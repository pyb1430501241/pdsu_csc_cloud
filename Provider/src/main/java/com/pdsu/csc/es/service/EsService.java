package com.pdsu.csc.es.service;


import com.pdsu.csc.exception.web.es.QueryException;

import java.util.List;

public interface EsService<T> {

	public List<T> queryByText(String text)throws QueryException;
	
}
