package com.himalaya.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月28日 上午11:06:44
* Description
*/
@Configuration
public class ESConfig {
	
	@Bean
	public TransportClient client() throws UnknownHostException {
		
//		5.5.2 version		
//		InetSocketTransportAddress node = new InetSocketTransportAddress(
//				InetAddress.getByName("127.0.0.1"), 9300
//				);
		
//      6.3.0				
		TransportAddress node = new TransportAddress(
				InetAddress.getByName("localhost"), 9300
				);
		
		Settings settings = Settings.builder()
				.put("cluster.name","es-cluster-01")
				.build(); 
		
		TransportClient client = new PreBuiltTransportClient(settings);
		
		client.addTransportAddress(node);
		
		return client;
	}
}
