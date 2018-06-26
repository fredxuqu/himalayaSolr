package com.himalaya.solr.index.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himalaya.solr.domain.NewsDO;
import com.himalaya.solr.index.IndexService;
import com.himalaya.solr.util.SolrClientUtil;

/**
 * @author: xuqu
 * @E-mail: fredxuqu@163.com
 * @version 2018年6月25日 上午11:06:39 Description
 */
@Service
public class IndexServiceImpl implements IndexService {

	@Autowired
	SolrClientUtil solrClientUtil;

	@Override
	public int index(NewsDO newsDO) {

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", newsDO.getId());
		doc.addField("title", newsDO.getTitle());
		doc.addField("content", newsDO.getContent());
		doc.addField("author", newsDO.getAuthor());
		doc.addField("type", newsDO.getType());
		doc.addField("grade", newsDO.getGrade());
		doc.addField("publishTime", newsDO.getPublishTime());
		doc.addField("createTime", newsDO.getCreateTime());
		doc.addField("updateTime", newsDO.getUpdateTime());

		try {
			SolrClient client = solrClientUtil.getSolrClient("news");
			UpdateResponse rsp = client.add(doc);
			client.commit();
			System.out.println("commit doc to index" + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public int index(List<NewsDO> list) {
		return 0;
	}
}