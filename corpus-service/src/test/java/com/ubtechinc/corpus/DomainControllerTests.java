package com.ubtechinc.corpus;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.ubtechinc.corpus.api.IDomainService;
import com.ubtechinc.corpus.entity.Domain;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainControllerTests {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private IDomainService domainService;

	private ObjectMapper mapper = new ObjectMapper();
	private final String QUERY_URL = "/domain/query";
	//private final String MODIFY_URL = "/domain/modify";

	@Before
	public void init() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	Domain domain1 = Domain.builder().ownerId(10000L).name("music1").createTime(new Date()).build();
	Domain domain2 = Domain.builder().ownerId(10000L).name("music2").createTime(new Date()).build();

	private void AddDateBeforeDDL() {
		domainService.insert(domain1);
		domainService.insert(domain2);
	}

	/**
	 * 1010 = 领域id不能为空
	 * 1011 = 领域名称不能为空
	 */
	/**
	@Test
	@Transactional
	@Rollback(true)
	public void modifyWithInvalidName() throws Exception {
		AddDateBeforeDDL();

		LinkedHashMap<String, String> domainParam = new LinkedHashMap<>();
		domainParam.put("domainId", domain1.getId().toString());
		domainParam.put("name", "");
		RequestBuilder req = put(MODIFY_URL).content(mapper.writeValueAsString(domainParam))
				.contentType(MediaType.APPLICATION_JSON);

		String response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> responseMap = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(responseMap.get("code").equals("1011"));

		domainParam.remove("name");
		req = put(MODIFY_URL).content(mapper.writeValueAsString(domainParam)).contentType(MediaType.APPLICATION_JSON);

		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response1Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response1Map.get("code").equals("1011"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void modifyWithInvalidId() throws Exception {
		AddDateBeforeDDL();

		LinkedHashMap<String, String> domainParam = new LinkedHashMap<>();
		domainParam.put("domainId", "");
		domainParam.put("name", "music1-extend");
		RequestBuilder req = put(MODIFY_URL).content(mapper.writeValueAsString(domainParam))
				.contentType(MediaType.APPLICATION_JSON);

		String response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> responseMap = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(responseMap.get("code").equals("1010"));

		domainParam.remove("domainId");
		req = put(MODIFY_URL).content(mapper.writeValueAsString(domainParam)).contentType(MediaType.APPLICATION_JSON);

		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response1Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response1Map.get("code").equals("1010"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void modifySuccess() throws Exception {
		AddDateBeforeDDL();

		LinkedHashMap<String, String> domainParam = new LinkedHashMap<>();
		domainParam.put("domainId", domain1.getId().toString());
		domainParam.put("name", "music1-extend");
		RequestBuilder req = put(MODIFY_URL).content(mapper.writeValueAsString(domainParam))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(status().isOk());

		assertTrue(domainService.selectCount(
				new EntityWrapper<Domain>(Domain.builder().id(domain1.getId()).name("music1-extend").build())) == 1);
	}
	**/
	
	@Test
	@Transactional
	@Rollback(true)
	public void querySuccess() throws Exception {
		AddDateBeforeDDL();

		RequestBuilder req = get(QUERY_URL).contentType(MediaType.APPLICATION_JSON);

		String response = mvc.perform(req).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		List<HashMap<String, String>> responseList = (List<HashMap<String, String>>) mapper.readValue(response,
				List.class);
		assertTrue(responseList.size() == 2);
	}
}
