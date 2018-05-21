package com.ubtechinc.rule;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubtechinc.rule.entity.Rule;
import com.ubtechinc.rule.mapper.RuleMapper;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: RuleServiceApplicationTests.java
 * @Prject: rule
 * @Package: com.ubtechinc.rule
 * @Description: TODO
 * @author: HuGui
 * @date: 2018年5月16日 下午6:08:12
 * @version: V1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleServiceApplicationTest {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private RuleMapper ruleMapper;

	private static final String ADD_URL = "/rule/add";
	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void init() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Transactional(rollbackFor = Exception.class)
	@Rollback(true)
	public void addSuccess() throws Exception {
		Map map1 = new HashMap(10);
		map1.put("domainName", "domain");
		map1.put("intentName", "intent");
		map1.put("round", "1");

		List resources = new ArrayList<>();
		Map map2 = new HashMap(10);
		map2.put("resourceIds", "1,2,3,4");
		map2.put("returnType", "ALL");
		resources.add(map2);
		Map map3 = new HashMap(10);
		map3.put("resourceIds", "9,10,11");
		map3.put("returnType", "RANDOM");
		resources.add(map3);

		map1.put("resources", resources);

		// =====================================
		Map map4 = new HashMap(10);
		map4.put("domainName", "domain1");
		map4.put("intentName", "intent1");
		map4.put("round", "2");

		List resources1 = new ArrayList<>();
		Map map5 = new HashMap(10);
		map5.put("resourceIds", "1,2,3,4");
		map5.put("returnType", "ALL");
		resources1.add(map5);
		Map map6 = new HashMap(10);
		map6.put("resourceIds", "9,10,11");
		map6.put("returnType", "RANDOM");
		resources1.add(map6);

		map4.put("resources", resources1);
		// =====================================

		Map map7 = new HashMap(10);
		map7.put("domainName", "domain2");
		map7.put("intentName", "intent2");
		map7.put("round", "2");

		List resources2 = new ArrayList<>();
		Map map8 = new HashMap(10);
		map8.put("resourceIds", "1,2,3,4");
		map8.put("returnType", "ALL");
		resources2.add(map8);
		Map map9 = new HashMap(10);
		map9.put("resourceIds", "9,10,11");
		map9.put("returnType", "RANDOM");
		resources2.add(map9);

		map7.put("resources", resources2);

		Map map = new HashMap(10);
		map.put("isMultiple", 1);
		List ruleInfos = new ArrayList<>();
		ruleInfos.add(map1);
		ruleInfos.add(map4);
		ruleInfos.add(map7);
		map.put("ruleInfos", ruleInfos);

		RequestBuilder req = post(ADD_URL).content(mapper.writeValueAsString(map))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(status().isOk());

		Rule rule = ruleMapper.selectOne(Rule.builder().domainName("domain").build());
		assertTrue(rule != null && "intent".equals(rule.getIntentName()));
	}
}
