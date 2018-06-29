package com.himalaya.elasticsearch.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.himalaya.elasticsearch.BaseTestCase;
import com.himalaya.elasticsearch.domain.NewsDO;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月28日 下午2:39:16
* Description
*/
public class ESServiceTest extends BaseTestCase {

	@Autowired
	ESService service;
	
	@Test 
	public void testGet(){
		Map<String, Object> result = service.get("XQbXRWQBTsb0O5kfh3nF");
		
		System.out.println(result);
		Assert.assertNotNull(result);
	}
	
	@Test 
	public void testGetNull(){
		Map<String, Object> result = service.get("0");
		System.out.println(result);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testIndex() throws IOException {
		NewsDO news = new NewsDO();
		news.setTitle("深圳市举办大学生运动会");
		news.setAuthor("张三");
		news.setContent("几十年来，你以党员标准要求自己，把为人民创作作为人生追求，坚持社会效益至上，塑造了许多富有生命力、感染力的艺术形象，受到人民群众高度评价和充分肯定。希望你发挥好党员先锋模范作用，继续在从艺做人上作表率，带动更多文艺工作者做有信仰、有情怀、有担当的人，为繁荣发展社会主义文艺贡献力量。");
		news.setType(1);
		news.setGrade(1);
		news.setCreateTime(new Date());
		news.setPublishTime(new Date());
		news.setUpdateTime(new Date());
		service.index(news);
		
		NewsDO news1 = new NewsDO();
		news1.setTitle("深圳市园林管理股份有限公司");
		news1.setAuthor("李四");
		news1.setContent("近日，83岁高龄的电影表演艺术家牛犇入党一事，引起媒体和社会广泛关注。牛犇是上海电影制片厂演员，11岁起从事表演工作，参演过《龙须沟》《红色娘子军》《天云山传奇》《牧马人》等一批脍炙人口的影片。因其对中国电影的贡献，2017年获得金鸡奖终身成就奖。牛犇经历过旧社会的苦难，受老一辈电影人的影响，青年时期就立志加入中国共产党，几十年从未放弃追求进步。近年来，他又多次向组织表达入党意愿。今年5月31日，中共上海电影（集团）有限公司演员剧团支部委员会同意吸收牛犇为中共预备党员");
		news1.setType(1);
		news1.setGrade(1);
		news1.setCreateTime(new Date());
		news1.setPublishTime(new Date());
		news1.setUpdateTime(new Date());
		service.index(news1);
		
		NewsDO news2 = new NewsDO();
		news2.setTitle("深圳园林管理股份有限公司");
		news2.setAuthor("张三");
		news2.setContent("你把党当作母亲，把入党当成神圣的事情，60多年矢志不渝追求进步，决心一辈子跟党走，这份执着的坚守令人感动");
		news2.setType(1);
		news2.setGrade(1);
		news2.setCreateTime(new Date());
		news2.setPublishTime(new Date());
		news2.setUpdateTime(new Date());
		service.index(news2);
		
		NewsDO news3 = new NewsDO();
		news3.setTitle("园林管理有限公司");
		news3.setAuthor("张三");
		news3.setContent("园林管理有限公司决定于2018年递交IPO申请，此次预计向社会募集资金100亿人民币");
		news3.setType(1);
		news3.setGrade(1);
		news3.setCreateTime(new Date());
		news3.setPublishTime(new Date());
		news3.setUpdateTime(new Date());
		String result = service.index(news3);
		
		Assert.assertNotNull(result);
	}
	
	@Test 
	public void testDelete() throws Exception {
		String result = service.delete("XQbXRWQBTsb0O5kfh3nF");
		System.out.println(result);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testUpdate() throws Exception {
		
		NewsDO news3 = new NewsDO();
		news3.setId("XgbXRWQBTsb0O5kfj3lK");
		news3.setTitle("园林管理有限公司");
		news3.setAuthor("张三");
		news3.setContent("园林管理有限公司决定于2018年向港交所递交IPO申请，此次预计向社会募集资金100亿港币");
		news3.setType(1);
		news3.setGrade(2);
		news3.setCreateTime(new Date());
		news3.setPublishTime(new Date());
		news3.setUpdateTime(new Date());
		String result = service.update(news3);
		System.out.println(result);
	}
	
	@Test
	public void testQuery() throws Exception {
		
		NewsDO news3 = new NewsDO();
		news3.setTitle("园林");
		news3.setAuthor("张三");
		news3.setType(1);
		news3.setGrade(2);
		List<Map<String, Object>> result = service.query(news3);
		for (Map<String, Object> map : result) {
			System.out.println(map);
		}		
	}
}

