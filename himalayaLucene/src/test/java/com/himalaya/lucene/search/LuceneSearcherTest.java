package com.himalaya.lucene.search;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.himalaya.lucene.LuceneApplicationTest;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月5日 上午11:02:06
* Description
*/
public class LuceneSearcherTest extends LuceneApplicationTest{
	
	@Autowired
	LuceneSearcher luceneSearcher;
	
	@Test
	public void testSearch() throws Exception {
		luceneSearcher.search();
	}
	
	@Test
	public void testPath() throws Exception {
		String indexPath = "D:/luceneTestFiles/index";
		Path path = Paths.get(indexPath,"");
		LOGGER.info("---" + path.toString());
	}
}