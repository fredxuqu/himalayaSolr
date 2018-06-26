package com.himalaya.solr.query;

import java.util.Map;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月25日 上午11:05:33
* Description
*/
public interface QueryService {
	
	public String query();
	
	public String query(String condition);
	
	public String queryByHttpClient(String url, Map<String,Object> paramMap);
}
