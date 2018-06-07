package com.ubtechinc.corpus;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubtechinc.corpus.api.ICorpusService;
import com.ubtechinc.corpus.api.IDomainService;
import com.ubtechinc.corpus.api.IIntentService;
import com.ubtechinc.corpus.entity.Corpus;
import com.ubtechinc.corpus.entity.Domain;
import com.ubtechinc.corpus.entity.Intent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CorpusControllerTests {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private IDomainService domainService;

	@Autowired
	private IIntentService intentService;

	@Autowired
	private ICorpusService corpusService;

	private ObjectMapper mapper = new ObjectMapper();
	private final String ADD_URL = "/corpus/add";
	private final String LIST_URL = "/corpus/list";
	private final String MODIFY_URL = "/corpus/modify";
	private final String REMOVE_URL = "/corpus/remove";

	@Before
	public void init() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	private LinkedHashMap<String, String> getCorpusParam() {
		LinkedHashMap<String, String> cboMap = new LinkedHashMap<>();
		cboMap.put("content", "唱一首歌吧");
		cboMap.put("domainName", "music");
		cboMap.put("intentName", "sing");
		cboMap.put("slots", "唱,歌");
		return cboMap;
	}

	Domain domain1 = Domain.builder().ownerId(10000L).name("music1").createTime(new Date()).build();
	Domain domain2 = Domain.builder().ownerId(10000L).name("music2").createTime(new Date()).build();
	Intent intent1 = Intent.builder().name("sing1").createTime(new Date()).build();
	Intent intent2 = Intent.builder().name("sing2").createTime(new Date()).build();
	Corpus corpus1 = Corpus.builder().content("content1").createTime(new Date()).ownerId(10000L).slots("slots1")
			.build();
	Corpus corpus2 = Corpus.builder().content("content2").createTime(new Date()).ownerId(10000L).slots("slots2")
			.build();
	Corpus corpus3 = Corpus.builder().content("content3").createTime(new Date()).ownerId(10000L).slots("slots3")
			.build();

	private void addDateBeforeDDL() {
		domainService.insert(domain1);
		domainService.insert(domain2);

		intent1.setDomainId(domain1.getId());
		intentService.insert(intent1);
		intent2.setDomainId(domain2.getId());
		intentService.insert(intent2);

		corpus1.setDomainId(domain1.getId());
		corpus1.setIntentId(intent1.getId());
		corpusService.insert(corpus1);

		corpus2.setDomainId(domain2.getId());
		corpus2.setIntentId(intent2.getId());
		corpusService.insert(corpus2);

		corpus3.setDomainId(domain2.getId());
		corpus3.setIntentId(intent2.getId());
		corpusService.insert(corpus3);
	}

	// 1. 访问路径
	// 2. 设置支持访问数据格式
	// 3. 获取返回HTTP status
	// 4. 内容是否正确
	@Test
	@Transactional
	@Rollback(true)
	public void addSuccess() throws Exception {
		RequestBuilder req = post(ADD_URL).content(mapper.writeValueAsString(getCorpusParam()))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(status().isOk());
	}

	/**
	 * 
	 * 1001 = 语料文本不能为空
	 * 1002 = 领域长度不在范围内
	 * 1003 = 领域名称含有非法字符
	 * 
	 * 1004 = 意图长度不在范围内
	 * 1005 = 意图名称含有非法字符
	 * 
	 * 1006 = 关键字不能为空
	 * 
	 * 1007 = 意图已存在
	 * 1008 = 缺少要删除的语料范围参数
	 * 
	 * 1009 = 语料id不能为空
	 * 1010 = 领域id不能为空
	 * 1011 = 领域名称不能为空
	 * 1012 = 意图id不能为空
	 * 1013 = 意图名称不能为空
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void listByPageSuccess() throws Exception {
		addDateBeforeDDL();
		for (int i = 0; i < 10; i++) {
			corpusService.insert(corpus2);
		}

		RequestBuilder req = get(LIST_URL).param("size", "5").contentType(MediaType.APPLICATION_JSON);
		String response = mvc.perform(req).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		List<HashMap<String, String>> responseList = (List<HashMap<String, String>>) mapper.readValue(response,
				List.class);
		assertTrue(responseList.size() == 5);

		req = get(LIST_URL).param("size", "5").param("num", "3").contentType(MediaType.APPLICATION_JSON);
		response = mvc.perform(req).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		List<HashMap<String, String>> response1List = (List<HashMap<String, String>>) mapper.readValue(response,
				List.class);
		assertTrue(response1List.size() == 3);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void listByOwnerIdAndIntentSuccess() throws Exception {
		addDateBeforeDDL();

		RequestBuilder req = get(LIST_URL).param("ownerId", corpus2.getOwnerId().toString())
				.param("intentId", corpus2.getIntentId().toString()).contentType(MediaType.APPLICATION_JSON);

		String response = mvc.perform(req).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		List<HashMap<String, String>> responseList = (List<HashMap<String, String>>) mapper.readValue(response,
				List.class);
		assertTrue(responseList.size() == 2);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void listByOwnerIdAndDomainIdAndIntentSuccess() throws Exception {
		addDateBeforeDDL();

		RequestBuilder req = get(LIST_URL).param("domainId", corpus2.getDomainId().toString())
				.param("intentId", corpus2.getIntentId().toString()).contentType(MediaType.APPLICATION_JSON);

		String response = mvc.perform(req).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		List<HashMap<String, String>> responseList = (List<HashMap<String, String>>) mapper.readValue(response,
				List.class);

		System.out.println("===========" + mapper.writeValueAsString(responseList));

		assertTrue(responseList.size() == 2);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void listByOwnerIdAndDomainIdSuccess() throws Exception {
		addDateBeforeDDL();

		RequestBuilder req = get(LIST_URL).param("ownerId", corpus1.getOwnerId().toString())
				.param("domainId", corpus1.getDomainId().toString()).contentType(MediaType.APPLICATION_JSON);

		String response = mvc.perform(req).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		List<HashMap<String, String>> responseList = (List<HashMap<String, String>>) mapper.readValue(response,
				List.class);
		assertTrue(responseList.size() == 1);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void listByOwnerIdSuccess() throws Exception {
		addDateBeforeDDL();

		RequestBuilder req = get(LIST_URL).contentType(MediaType.APPLICATION_JSON);

		String response = mvc.perform(req).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		List<HashMap<String, String>> responseList = (List<HashMap<String, String>>) mapper.readValue(response,
				List.class);
		assertTrue(responseList.size() == 3);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void modifyWithInvalidContent() throws Exception {
		addDateBeforeDDL();

		LinkedHashMap<String, String> corpusParam = new LinkedHashMap<>();
		corpusParam.put("corpusId", corpus1.getId().toString());
		corpusParam.put("content", "");
		RequestBuilder req = put(MODIFY_URL).content(mapper.writeValueAsString(corpusParam))
				.contentType(MediaType.APPLICATION_JSON);

		String response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> responseMap = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(responseMap.get("code").equals("1001"));

		corpusParam.remove("content");
		req = put(MODIFY_URL).content(mapper.writeValueAsString(corpusParam)).contentType(MediaType.APPLICATION_JSON);

		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response1Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response1Map.get("code").equals("1001"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void modifyWithInvalidCorpusId() throws Exception {
		addDateBeforeDDL();

		LinkedHashMap<String, String> corpusParam = new LinkedHashMap<>();
		corpusParam.put("content", corpus1.getContent() + "-extend");
		RequestBuilder req = put(MODIFY_URL).content(mapper.writeValueAsString(corpusParam))
				.contentType(MediaType.APPLICATION_JSON);

		String response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> responseMap = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(responseMap.get("code").equals("1009"));

		corpusParam.put("corpusId", "");
		req = put(MODIFY_URL).content(mapper.writeValueAsString(corpusParam)).contentType(MediaType.APPLICATION_JSON);

		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response1Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response1Map.get("code").equals("1009"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void modifySuccess() throws Exception {
		addDateBeforeDDL();

		LinkedHashMap<String, String> corpusParam = new LinkedHashMap<>();
		corpusParam.put("corpusId", corpus1.getId().toString());
		String content = corpus1.getContent();
		corpusParam.put("content", content + "-extend");
		RequestBuilder req = put(MODIFY_URL).content(mapper.writeValueAsString(corpusParam))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(status().isOk());

		assertTrue(corpusService.selectCount(new EntityWrapper<Corpus>(
				Corpus.builder().id(corpus1.getId()).content(content + "-extend").build())) == 1);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void removeByCorpusIdSuccess() throws Exception {
		addDateBeforeDDL();

		LinkedHashMap<String, Object> corpusParam = new LinkedHashMap<>();
		corpusParam.put("corpusIds", new String[] { corpus1.getId().toString(), corpus2.getId().toString() });
		RequestBuilder req = delete(REMOVE_URL).content(mapper.writeValueAsString(corpusParam))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(status().isOk());

		assertTrue(domainService.selectCount(new EntityWrapper<Domain>(Domain.builder().name("music1").build())) == 1);
		assertTrue(intentService.selectCount(new EntityWrapper<Intent>(Intent.builder().name("sing1").build())) == 1);
		assertTrue(corpusService.selectCount(new EntityWrapper<Corpus>(
				Corpus.builder().domainId(domain1.getId()).intentId(intent1.getId()).build())) == 0);
		assertTrue(corpusService.selectCount(new EntityWrapper<Corpus>(
				Corpus.builder().domainId(domain2.getId()).intentId(intent2.getId()).build())) == 1);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void removeByIntentIdSuccess() throws Exception {
		addDateBeforeDDL();

		LinkedHashMap<String, String> corpusParam = new LinkedHashMap<>();
		corpusParam.put("intentId", intent1.getId().toString());
		RequestBuilder req = delete(REMOVE_URL).content(mapper.writeValueAsString(corpusParam))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(status().isOk());

		assertTrue(domainService.selectCount(new EntityWrapper<Domain>(Domain.builder().name("music1").build())) == 1);
		assertTrue(intentService.selectCount(new EntityWrapper<Intent>(Intent.builder().name("sing1").build())) == 0);
		assertTrue(corpusService.selectCount(new EntityWrapper<Corpus>(
				Corpus.builder().domainId(domain1.getId()).intentId(intent1.getId()).build())) == 0);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void removeByDomainIdSuccess() throws Exception {
		addDateBeforeDDL();

		LinkedHashMap<String, String> corpusParam = new LinkedHashMap<>();
		corpusParam.put("domainId", domain1.getId().toString());
		RequestBuilder req = delete(REMOVE_URL).content(mapper.writeValueAsString(corpusParam))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(status().isOk());

		assertTrue(domainService.selectCount(new EntityWrapper<Domain>(Domain.builder().name("music1").build())) == 0);
		assertTrue(intentService.selectCount(new EntityWrapper<Intent>(Intent.builder().name("sing1").build())) == 0);
		assertTrue(corpusService.selectCount(new EntityWrapper<Corpus>(
				Corpus.builder().domainId(domain1.getId()).intentId(intent1.getId()).build())) == 0);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void addWithExistsIntent() throws Exception {
		LinkedHashMap<String, String> cboMap = getCorpusParam();
		RequestBuilder req = post(ADD_URL).content(mapper.writeValueAsString(cboMap))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req);

		cboMap.put("domainName", "chat");
		req = post(ADD_URL).content(mapper.writeValueAsString(cboMap)).contentType(MediaType.APPLICATION_JSON);
		String response = mvc.perform(req).andExpect(status().isInternalServerError()).andReturn().getResponse()
				.getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> responseMap = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(responseMap.get("code").equals("1007"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void addWithInvalidSlots() throws Exception {
		LinkedHashMap<String, String> cboMap = getCorpusParam();
		cboMap.remove("slots");

		RequestBuilder req = post(ADD_URL).content(mapper.writeValueAsString(cboMap))
				.contentType(MediaType.APPLICATION_JSON);
		String response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> responseMap = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(responseMap.get("code").equals("1006"));

		cboMap.put("slots", "");
		req = post(ADD_URL).content(mapper.writeValueAsString(cboMap)).contentType(MediaType.APPLICATION_JSON);
		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response1Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response1Map.get("code").equals("1006"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void addWithInvalidIntentName() throws Exception {
		LinkedHashMap<String, String> cboMap = getCorpusParam();
		cboMap.remove("intentName");

		RequestBuilder req = post(ADD_URL).content(mapper.writeValueAsString(cboMap))
				.contentType(MediaType.APPLICATION_JSON);
		String response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> responseMap = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(responseMap.get("code").equals("1013"));

		cboMap.put("intentName",
				"11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
						+ "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
						+ "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		req = post(ADD_URL).content(mapper.writeValueAsString(cboMap)).contentType(MediaType.APPLICATION_JSON);
		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response2Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response2Map.get("code").equals("1004"));

		cboMap.put("intentName", "故事");
		req = post(ADD_URL).content(mapper.writeValueAsString(cboMap)).contentType(MediaType.APPLICATION_JSON);
		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response3Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response3Map.get("code").equals("1005"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void addWithInvalidDomainName() throws Exception {
		LinkedHashMap<String, String> cboMap = getCorpusParam();
		cboMap.remove("domainName");

		RequestBuilder req = post(ADD_URL).content(mapper.writeValueAsString(cboMap))
				.contentType(MediaType.APPLICATION_JSON);
		String response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> responseMap = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(responseMap.get("code").equals("1011"));

		cboMap.put("domainName",
				"11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		req = post(ADD_URL).content(mapper.writeValueAsString(cboMap)).contentType(MediaType.APPLICATION_JSON);
		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response2Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response2Map.get("code").equals("1002"));

		cboMap.put("domainName", "故事");
		req = post(ADD_URL).content(mapper.writeValueAsString(cboMap)).contentType(MediaType.APPLICATION_JSON);
		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response3Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response3Map.get("code").equals("1003"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void addWithInvalidContent() throws Exception {
		LinkedHashMap<String, String> cboMap = getCorpusParam();
		cboMap.remove("content");
		RequestBuilder req = post(ADD_URL).content(mapper.writeValueAsString(cboMap))
				.contentType(MediaType.APPLICATION_JSON);
		String response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> responseMap = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(responseMap.get("code").equals("1001"));

		cboMap.put("content", "");
		req = post(ADD_URL).content(mapper.writeValueAsString(cboMap)).contentType(MediaType.APPLICATION_JSON);
		response = mvc.perform(req).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		@SuppressWarnings("unchecked")
		HashMap<String, String> response1Map = (HashMap<String, String>) mapper.readValue(response, Map.class);
		assertTrue(response1Map.get("code").equals("1001"));
	}

}
