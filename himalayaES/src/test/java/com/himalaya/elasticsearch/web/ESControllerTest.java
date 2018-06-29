package com.himalaya.elasticsearch.web;

import java.util.Date;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.himalaya.elasticsearch.ESApplication;
import com.himalaya.elasticsearch.domain.NewsDO;

/**
 * @author: xuqu
 * @E-mail: fredxuqu@163.com
 * @version 2018年6月29日 下午2:59:20 Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
@WebAppConfiguration
public class ESControllerTest {

	private MockMvc mockMvc;

	@Autowired
	ESController esController;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		//项目拦截器有效
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

	@Test
	public void testConnection() throws  Exception {
	    //调用接口，传入添加的用户参数
	    RequestBuilder request = MockMvcRequestBuilders.get("/")
	            .contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(JSON.toJSONString(null)); 

	     MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
	     int status = mvcResult.getResponse().getStatus();  
	     String content = mvcResult.getResponse().getContentAsString();
	     Assert.assertTrue("正确", status == 200);  
	     Assert.assertFalse("错误", status != 200);  
	     System.out.println("返回结果："+status);
	     System.out.println(content);
	}
	
	@Test
	public void testConn() throws  Exception {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", "fred");
		System.out.println(jsonObject.toString());
	    //调用接口，传入添加的用户参数
	    RequestBuilder request = MockMvcRequestBuilders.post("/test")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("fred");

	     MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
	     int status = mvcResult.getResponse().getStatus();  
	     String content = mvcResult.getResponse().getContentAsString();
	     Assert.assertTrue("正确", status == 200);  
	     Assert.assertFalse("错误", status != 200);  
	     System.out.println("返回结果："+status);
	     System.out.println(content);
	}
	
	@Test
	public void testConn1() throws  Exception {
		
		//调用接口，传入添加的用户参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", "MockMVC Testing");
		jsonObject.put("author", "fred");
		jsonObject.put("content", "dsfadsfdasfafasdfasfasdf");
		jsonObject.put("type", 1);
		jsonObject.put("grade", 1);
		jsonObject.put("createTime", "2018-01-01");
		jsonObject.put("publishTime", "2018-02-01");
		jsonObject.put("updateTime", "2018-03-01");
		System.out.println(jsonObject.toString());
	    //调用接口，传入添加的用户参数
	    RequestBuilder request = MockMvcRequestBuilders.post("/testRequestBody")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonObject.toString());

	     MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
	     int status = mvcResult.getResponse().getStatus();  
	     String content = mvcResult.getResponse().getContentAsString();
	     Assert.assertTrue("正确", status == 200);  
	     Assert.assertFalse("错误", status != 200);  
	     System.out.println("返回结果："+status);
	     System.out.println(content);
	}
	
	@Test
	public void testGet() throws  Exception {
	    //调用接口，传入添加的用户参数
	    RequestBuilder request = MockMvcRequestBuilders.get("/get/news/finance?id=XgbXRWQBTsb0O5kfj3lK")
	            .contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(JSON.toJSONString(null)); 

	     MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
	     int status = mvcResult.getResponse().getStatus();  
	     String content = mvcResult.getResponse().getContentAsString();
	     Assert.assertTrue("正确", status == 200);  
	     Assert.assertFalse("错误", status != 200);  
	     System.out.println("返回结果："+status);
	     System.out.println(content);
	}
	
	@Test
	public void testDelete() throws  Exception {
	    		
	    RequestBuilder request = MockMvcRequestBuilders.delete("/delete/news/finance?id=XgbXRWQBTsb0O5kfj3l1")
	            .contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(JSON.toJSONString(null)); 

	     MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
	     int status = mvcResult.getResponse().getStatus();  
	     String content = mvcResult.getResponse().getContentAsString();
	     Assert.assertTrue("正确", status == 200);  
	     Assert.assertFalse("错误", status != 200);  
	     System.out.println("返回结果："+status);
	     System.out.println(content);
	}
	
	@Test
	public void testUpdate() throws  Exception {
		//调用接口，传入添加的用户参数
		NewsDO news = new NewsDO();
		news.setId("XgbXRWQBTsb0O5kfj3lK");
		news.setTitle("MockMVC Testing");
		news.setAuthor("fred");
		news.setContent("dsfasdfasdfasfsadfsdfas");
		news.setType(1);
		news.setGrade(1);
		
	    RequestBuilder request = MockMvcRequestBuilders.put("/update/news/finance")
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	    		.content(JSON.toJSONString(null));

	     MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
	     int status = mvcResult.getResponse().getStatus();  
	     String content = mvcResult.getResponse().getContentAsString();
	     Assert.assertTrue("正确", status == 200);  
	     Assert.assertFalse("错误", status != 200);  
	     System.out.println("返回结果："+status);
	     System.out.println(content);
	}
	
	@Test
	public void testIndex() throws  Exception {
	    
		//调用接口，传入添加的用户参数
		NewsDO news = new NewsDO();
		news.setTitle("MockMVC Testing");
		news.setAuthor("fred");
		news.setContent("XgbXRWQBTsb0O5kfj3lK");
		news.setType(1);
		news.setGrade(1);
		news.setCreateTime(new Date());
		news.setUpdateTime(new Date());
		news.setPublishTime(new Date());
		
		System.out.println(JSON.toJSONString(news));
	    RequestBuilder request = MockMvcRequestBuilders.post("/add/news/finance")
	            .contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(JSON.toJSONString(news)); 

	     MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
	     int status = mvcResult.getResponse().getStatus();  
	     String content = mvcResult.getResponse().getContentAsString();
	     Assert.assertTrue("正确", status == 200);  
	     Assert.assertFalse("错误", status != 200);  
	     System.out.println("返回结果："+status);
	     System.out.println(content);
	}
	
	@Test
	public void testIndex1() throws  Exception {
	    
		//调用接口，传入添加的用户参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", "MockMVC Testing");
		jsonObject.put("author", "fred");
		jsonObject.put("content", "dsfadsfdasfafasdfasfasdf");
		jsonObject.put("type", 1);
		jsonObject.put("grade", 1);
		jsonObject.put("createTime", "2018-01-01");
		jsonObject.put("publishTime", "2018-02-01");
		jsonObject.put("updateTime", "2018-03-01");
		
		System.out.println(JSON.toJSONString(jsonObject.toString()));
	    RequestBuilder request = MockMvcRequestBuilders.post("/add/news/finance")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonObject.toString()); 
	    
	    //String responseString = mockMvc.perform( post("/softs").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
        //        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(); 

	     MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
	     int status = mvcResult.getResponse().getStatus();  
	     String content = mvcResult.getResponse().getContentAsString();
	     Assert.assertTrue("正确", status == 200);  
	     Assert.assertFalse("错误", status != 200);  
	     System.out.println("返回结果："+status);
	     System.out.println(content);
	}
}
