package com.himalaya.solr.query.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himalaya.solr.query.QueryService;
import com.himalaya.solr.util.HttpClientUtil;
import com.himalaya.solr.util.SolrClientUtil;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月25日 上午11:13:00
* Description
*/
@Service
public class QueryServiceImpl implements QueryService {
	
	@Autowired
	private SolrClientUtil solrClientUtil;
	
	@Autowired
	private HttpClientUtil httpClientUtil;
	
	@Override
	public String query() {

		long start = System.currentTimeMillis();
		try {
			
			SolrClient client = solrClientUtil.getSolrClient("news");
			System.out.println("Get Connection cost " + (System.currentTimeMillis() - start));
			
			start = System.currentTimeMillis();
			SolrQuery query = new SolrQuery();
			query.set("q", "title:项目规划");
			query.set("start", 0);
	        query.set("rows",5);
//	        query.set("sort", "grade asc");
//	        query.set("spellcheck.build", "true");  //拼音检查
//	        query.set("hl", "true");  
//	        query.set("spellcheck.build", "true");
//	        query.set("hl.fl","id, title, author, content");
//	        query.set("hl.simple.pre","<font color='red'>");
//	        query.set("hl.simple.post", "</font>"); 
			
			QueryResponse response = client.query(query); 
			SolrDocumentList results = response.getResults();
			System.out.println("Query cost " + (System.currentTimeMillis() - start));
			return results.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String query(String condition) {

		return null;
	}
	
	@Override
	public String queryByHttpClient(String url, Map<String,Object> paramMap) {
		long start = System.currentTimeMillis();
		String response = null;
		try {
			response = httpClientUtil.doPost(url, paramMap);
		}   
        catch (Exception e) {  
            e.printStackTrace();  
        }
		System.out.println("Query cost " + (System.currentTimeMillis() - start));
        return response;  
	}
}
