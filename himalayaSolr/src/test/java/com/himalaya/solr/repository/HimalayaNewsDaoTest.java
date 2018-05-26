package com.himalaya.solr.repository;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.himalaya.solr.BaseTestCase;
import com.himalaya.solr.domain.NewsDO;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2017年7月26日 上午11:05:36
* Description
*/
public class HimalayaNewsDaoTest extends BaseTestCase {
	
	@Autowired
	HimalayaNewsDao himalayaNewsDao;
	
	@Test
	public void testGetAll(){
		
		List<NewsDO> list = himalayaNewsDao.getAll();
		LOGGER.info("Got news " + list.size());
		for (NewsDO newsDO : list) {
			LOGGER.info("--" + newsDO.toString());
		}
	}
    
    @Test
    public void testGetOne(){
    	NewsDO news = himalayaNewsDao.getById(new Integer(1));
    	LOGGER.info(news.toString());
    }
	
    @Test
    public void testInsert(){
    	NewsDO news = new NewsDO();
    	news.setId(5);
    	news.setTitle("BitCoin related news");
    	news.setContent("----------");
    	news.setType(3);
    	news.setAuthor("Fred");
    	news.setPublishTime(new Date());
    	himalayaNewsDao.insert(news);
    }
    
    @Test
    public void testUpdate(){
    	NewsDO news = new NewsDO();
    	news.setId(5);
    	news.setContent("$$$$$$$$$$");
    	himalayaNewsDao.update(news);
    }
 
    @Test
    public void testDelete(){
    	himalayaNewsDao.delete(5);
    }
}
