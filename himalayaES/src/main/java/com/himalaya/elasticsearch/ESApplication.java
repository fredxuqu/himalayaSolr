package com.himalaya.elasticsearch;

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
public class ESApplication { 

	private static final Logger LOGGER = LoggerFactory.getLogger(ESApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ESApplication.class, args);
		LOGGER.info("ElasticSearch Application is running...");
	}
}
