package com.himalaya.solr.util;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.himalaya.solr.BaseTestCase;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月25日 下午5:14:05
* Description
*/
public class SolrClientUtilTest extends BaseTestCase {

	@Autowired
	SolrClientUtil solrClientUtil;
	
	@Test
	public void testGetSolrClientPerformance(){
		
		long startTime = System.currentTimeMillis();
		SolrClient client = solrClientUtil.getSolrClient("news");
		System.out.println(client.hashCode() + " Cost " + (System.currentTimeMillis() - startTime));
		
		startTime = System.currentTimeMillis();
		SolrClient client1 = solrClientUtil.getSolrClient("news");
		System.out.println(client.hashCode() + " Cost " + (System.currentTimeMillis() - startTime));
		
		startTime = System.currentTimeMillis();
		SolrClient client2 = solrClientUtil.getSolrClient("news");
		System.out.println(client.hashCode() + " Cost " + (System.currentTimeMillis() - startTime));
	}
}
