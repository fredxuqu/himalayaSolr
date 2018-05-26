package com.himalaya.solr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年5月22日 上午10:46:07
* Description
*/
@SpringBootApplication
public class SolrApplication { 

	private static final Logger LOGGER = LoggerFactory.getLogger(SolrApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SolrApplication.class, args);
		LOGGER.info("Solr Application is running...");
	}
}
