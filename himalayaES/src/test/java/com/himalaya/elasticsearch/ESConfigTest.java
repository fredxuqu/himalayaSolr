package com.himalaya.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月28日 上午11:30:06
* Description
*/
public class ESConfigTest extends BaseTestCase {

	@Autowired
	TransportClient client;
	
	@Test 
	public void testConfig(){
		
		System.out.println(client.hashCode());
		Assert.assertNotNull(client);
	}
}
