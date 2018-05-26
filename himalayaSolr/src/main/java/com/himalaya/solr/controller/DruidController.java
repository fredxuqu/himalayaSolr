package com.himalaya.solr.controller;

import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.himalaya.solr.domain.NewsDO;
import com.himalaya.solr.repository.HimalayaNewsDao;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年1月16日 下午4:02:33
* Description
*/

@RestController
public class DruidController {

	private final Logger logger = LoggerFactory.getLogger(DruidController.class);

	@Autowired
	private HimalayaNewsDao himalayaNewsDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String index() {
		return "Spring boot & druid demo.";
	}

	@RequestMapping(value = "/news/listall", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String searchNews() {

		logger.info("List All Records!");

		List<NewsDO> news = null;
		
		JSONArray resultJSON = null;

		try {
			news = himalayaNewsDao.getAll();
			logger.info("Get " + news.size() + " records");
			resultJSON = new JSONArray(news);
			logger.debug("news : {} ", resultJSON.toString());
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error("Searching tags error, msg:", e.getMessage());
		}

		return resultJSON.toString();
	}
}
