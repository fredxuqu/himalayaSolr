package com.himalaya.solr.index.impl;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.himalaya.solr.BaseTestCase;
import com.himalaya.solr.domain.NewsDO;
import com.himalaya.solr.index.IndexService;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月25日 下午4:04:00
* Description
*/
public class IndexServiceImplTest extends BaseTestCase {
	
	@Autowired
	IndexService indexService;
	
	@Test
	public void testIndex() throws Exception{
		
		NewsDO newsDO = new NewsDO();
		newsDO.setId(6);
		newsDO.setTitle("新项目规划6");
		newsDO.setAuthor("Fred");
		newsDO.setContent("SolrJ是操作Solr的JAVA客户端，它提供了增加、修改、删除、查询Solr索引的JAVA接口。SolrJ针对 Solr提供了Rest 的HTTP接口进行了封装， SolrJ底层是通过使用httpClient中的方法来完成Solr的操作。");
		newsDO.setType(1);
		newsDO.setGrade(1);
		newsDO.setPublishTime(new Date());
		newsDO.setCreateTime(new Date());
		newsDO.setUpdateTime(new Date());
		indexService.index(newsDO);
		
		NewsDO newsDO1 = new NewsDO();
		newsDO1.setId(7);
		newsDO1.setTitle("新项目规划7");
		newsDO1.setAuthor("Fred");
		newsDO1.setContent("SolrJ是操作Solr的JAVA客户端，它提供了增加、修改、删除、查询Solr索引的JAVA接口。SolrJ针对 Solr提供了Rest 的HTTP接口进行了封装， SolrJ底层是通过使用httpClient中的方法来完成Solr的操作。");
		newsDO1.setType(1);
		newsDO1.setGrade(1);
		newsDO1.setPublishTime(new Date());
		newsDO1.setCreateTime(new Date());
		newsDO1.setUpdateTime(new Date());
		indexService.index(newsDO1);
	}
}
