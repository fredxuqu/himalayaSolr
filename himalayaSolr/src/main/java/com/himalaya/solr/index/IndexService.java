package com.himalaya.solr.index;

import java.util.List;

import com.himalaya.solr.domain.NewsDO;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月25日 上午11:06:10
* Description
*/
public interface IndexService {

	public int index(NewsDO newsDO);
	
	public int index(List<NewsDO> list);
}
