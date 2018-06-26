package com.himalaya.solr.query.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.himalaya.solr.BaseTestCase;
import com.himalaya.solr.query.QueryService;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月25日 下午5:33:01
* Description
*/
public class QueryServiceImplTest extends BaseTestCase {
	
	@Value("${solr.url}")
	private String solrURL;
	
	@Autowired
	QueryService queryService;
		
	@Test
	public void testQuery() throws Exception{
		
		System.out.println(queryService.query());
		
		System.out.println(queryService.query());
	}
	
	@Test
	public void testQueryPerformance() throws Exception{
		
		String url = solrURL + "/news/select?";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("q", "title:项目计划");
		paramMap.put("fq","grade:1");
		paramMap.put("indent", "on");
		paramMap.put("wt","json");
		String result = queryService.queryByHttpClient(url, paramMap);
		
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("q", "title:项目计划");
		paramMap1.put("fq","type:1");
		paramMap1.put("indent", "on");
		paramMap1.put("wt","json");
		String result1 = queryService.queryByHttpClient(url, paramMap1);
		
		//System.out.println(result);
	}
}
