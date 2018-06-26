package com.himalaya.solr.util;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月25日 下午3:42:51
* Description
*/
@Component
public class SolrClientUtil {
	
	@Value("${solr.url}")
	private String solrURL;
	
	public SolrClient getSolrClient(String coreName){
		try {
	        String baseURL = solrURL + "/" + coreName; 
	        System.out.println("server address:" + baseURL);
	        HttpSolrClient client = new HttpSolrClient(baseURL);
	        client.setConnectionTimeout(30000);
	        client.setDefaultMaxConnectionsPerHost(100);
	        client.setMaxTotalConnections(100);	        
	        client.setSoTimeout(3000); // socket read timeout 
	        client.setFollowRedirects(false); // defaults to false  
	        client.setAllowCompression(true); // 允许压缩，减少数据量	        
	        return client;
	    } catch (Exception ex) {
	        throw new RuntimeException(ex);
	    }
	}
}
