package com.himalaya.elasticsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himalaya.elasticsearch.domain.NewsDO;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月28日 下午2:29:31
* Description
*/
@Service
public class ESService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ESService.class);

	@Autowired
	private TransportClient client;
	
	public Map<String, Object> get(String id){
		
		LOGGER.info(id);
		
		try {
			GetResponse response = this.client.prepareGet("news", "finance", id).get();
			return response.getSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String index(NewsDO news) throws IOException {
		
		try {
			XContentBuilder context = XContentFactory.jsonBuilder()
				.startObject()
				.field("id", news.getId())
				.field("title", news.getTitle())
				.field("content", news.getContent())
				.field("author", news.getAuthor())
				.field("type", news.getType())
				.field("grade", news.getGrade())
				.field("createTime", news.getCreateTime().getTime())
				.field("publishTime", news.getPublishTime().getTime())
				.field("updateTime", news.getUpdateTime().getTime())
				.endObject();
			IndexResponse response = client.prepareIndex("news", "finance").setSource(context).get();
			return response.getId();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String delete(String id) throws IOException {
		
		DeleteResponse response = client.prepareDelete("news", "finance", id).get();
		return response.getResult().toString();
	}
	
	public String update(NewsDO news) throws Exception {
		
		try {
			UpdateRequest update = new UpdateRequest("news", "finance", news.getId());
			
			XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject();
			if(news.getTitle() != null){
				builder.field("title", news.getTitle());
			}
			if(news.getContent() != null){
				builder.field("content", news.getContent());
			}
			if(news.getAuthor() != null){
				builder.field("author", news.getAuthor());
			}
			if(news.getGrade() > 0){
				builder.field("grade", news.getGrade());
			}
			if(news.getType() > 0){
				builder.field("type", news.getType());
			}
			
			if(news.getUpdateTime() != null){
				builder.field("updateTime", news.getUpdateTime().getTime());
			}
			builder.endObject();
			update.doc(builder);
			UpdateResponse response = client.update(update).get();
			return response.getResult().toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Map<String, Object>> query(NewsDO news){
		
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(news.getTitle()!=null){
			queryBuilder.must(QueryBuilders.matchQuery("title", news.getTitle()));
		}
		if(news.getAuthor()!=null){
			queryBuilder.must(QueryBuilders.matchQuery("author", news.getAuthor()));
		}
		
		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("type").from(0);
		rangeQuery.to(news.getType());
		
		queryBuilder.filter(rangeQuery);
		
		SearchRequestBuilder requestBuilder = this.client.prepareSearch("news")
				.setTypes("finance")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(queryBuilder)
				.setFrom(0)
				.setSize(10);
		
		System.out.println(requestBuilder);
		
		SearchResponse response = requestBuilder.get();
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		for (SearchHit hit : response.getHits()) {
			resultList.add(hit.getSourceAsMap());
		}
		
		return resultList;
	}
}
