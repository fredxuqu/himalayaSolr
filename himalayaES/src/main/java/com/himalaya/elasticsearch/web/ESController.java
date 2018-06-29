package com.himalaya.elasticsearch.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.himalaya.elasticsearch.domain.NewsDO;
import com.himalaya.elasticsearch.service.ESService;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月28日 上午11:28:08
* Description
*/
@RestController
public class ESController {

	@Autowired
	private ESService service;
	
	@GetMapping("/")
	public String index(){
		return "Elastic Search Server is running";
	}
	
	@PostMapping("/test")
	@ResponseBody
	public ResponseEntity<String> testConnection(@RequestBody String title){
		return new ResponseEntity<String>("Elastic Search Server is running " + title, HttpStatus.OK);
	}
	
	@PostMapping("/testRequestBody")
	@ResponseBody
	public ResponseEntity<String> testRequestBody(@RequestBody NewsDO news){
		
		String request = JSON.toJSONString(news);
		return new ResponseEntity<String>(request, HttpStatus.OK);
	}
	
	@GetMapping("/get/news/finance")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> get(@RequestParam(name="id", defaultValue="") String id){
		
		if(id.isEmpty()){
			return new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND);
		}
		
		Map<String, Object> resultMap = service.get(id);
		
		if(resultMap==null || resultMap.isEmpty()){
			return new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	@PostMapping("/add/news/finance")
	@ResponseBody
	public ResponseEntity<String> add(
			@RequestParam(name="id") String id,
			@RequestParam(name="title") String title,
			@RequestParam(name="content") String content,
			@RequestParam(name="author") String author,
			@RequestParam(name="type") int type,
			@RequestParam(name="grade") int grade,
			@RequestParam(name="createTime")
				@DateTimeFormat(pattern="yyyy-MM-dd") Date createTime,
			@RequestParam(name="publishTime")
				@DateTimeFormat(pattern="yyyy-MM-dd") Date publishTime,	
			@RequestParam(name="updateTime")
				@DateTimeFormat(pattern="yyyy-MM-dd") Date updateTime
			){
		try {
			
			System.out.println("add method called....." + title);
			NewsDO news = new NewsDO();
			news.setId(id);
			news.setTitle(title);
			news.setAuthor(author);
			news.setContent(content);
			news.setType(type);
			news.setGrade(grade);
			news.setCreateTime(createTime);
			news.setPublishTime(publishTime);
			news.setUpdateTime(updateTime);
			String result = service.index(news);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/news/finance")
	@ResponseBody
	public ResponseEntity<String> delete(
			@RequestParam(name="id", defaultValue="") String id
			){
		try {
			String result = service.delete(id);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/news/finance")
	@ResponseBody
	public ResponseEntity<String> update(
			@RequestParam(name="id") String id,
			@RequestParam(name="title", required=false) String title,
			@RequestParam(name="content", required=false) String content,
			@RequestParam(name="author", required=false) String author,
			@RequestParam(name="type", required=false) int type,
			@RequestParam(name="grade", required=false) int grade,
			@RequestParam(name="updateTime", required=false)
				@DateTimeFormat(pattern="yyyy-MM-dd") Date updateTime
			){
		try {
			NewsDO news = new NewsDO();
			news.setId(id);
			news.setTitle(title);
			news.setAuthor(author);
			news.setContent(content);
			news.setType(type);
			news.setGrade(grade);
			news.setUpdateTime(updateTime);
			String result = service.update(news);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("query/news/finance")
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> query(
			@RequestParam(name="title", required=false) String title,
			@RequestParam(name="author", required=false) String author,
			@RequestParam(name="type", required=false) int type,
			@RequestParam(name="grade", required=false) int grade){
		
		NewsDO news = new NewsDO();
		news.setTitle(title);
		news.setAuthor(author);
		news.setType(type);
		news.setGrade(grade);
		List<Map<String, Object>> response = service.query(news);
		return new ResponseEntity<List<Map<String, Object>>>(response, HttpStatus.OK);
	}
}