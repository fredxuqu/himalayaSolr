package com.himalaya.lucene;

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
public class LuceneApplication { 

	private static final Logger LOGGER = LoggerFactory.getLogger(LuceneApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(LuceneApplication.class, args);
		LOGGER.info("Lucene Application is running...");
	}
}
