package com.ubtechinc.corpus.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ubtechinc.corpus.api.ICorpusService;
import com.ubtechinc.corpus.api.IDomainService;
import com.ubtechinc.corpus.api.IIntentService;
import com.ubtechinc.corpus.entity.Corpus;
import com.ubtechinc.corpus.entity.Corpus.CorpusBuilder;
import com.ubtechinc.corpus.entity.Domain;
import com.ubtechinc.corpus.entity.Intent;
import com.ubtechinc.exception.SkillException;
import com.ubtechinc.corpus.model.CorpusBo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hugui
 * @since 2018-04-02
 */

@RestController
@RequestMapping("/corpus")
@Slf4j
public class CorpusController extends BaseController {

	@Autowired
	private CustomLogger logger;

	@Autowired
	private IDomainService domainService;

	@Autowired
	private IIntentService intentService;

	@Autowired
	private ICorpusService corpusService;

	// TODO 语料批量新增
	// TODO 语料批量导出

	/**
	 * @title: add
	 * @description: 语料新增
	 * @param response
	 * @param corpusBo
	 */
	@Transactional
	@PostMapping(value = "/add")
	public void add(HttpServletResponse response, @RequestBody @Validated(CorpusBo.New.class) CorpusBo corpusBo) {
		// 根据意图查询信息（是否已存在，所属领域）
		Map<String, Object> intentMap = intentService.selectByName(corpusBo.getIntentName());

		// 根据领域名称查询领域信息
		Domain searchDomain = Domain.builder().name(corpusBo.getDomainName()).build();
		searchDomain = domainService.selectOne(new EntityWrapper<Domain>(searchDomain));

		// 查询意图
		// 1. 意图不存在：
		// 1.1 领域存在
		// 1.2 领域不存在
		// 2. 意图存在
		// 2.1 参数领域和意图不匹配
		// 2.2 参数领域和意图匹配
		if (intentMap == null) {

			if (searchDomain == null) {
				searchDomain = new Domain(corpusBo.getDomainName(), ownerId);
				domainService.insert(searchDomain);
			}

			Intent intent = new Intent(corpusBo.getIntentName(), searchDomain.getId());
			intentService.insert(intent);

			corpusService.insert(new Corpus(corpusBo.getContent(), searchDomain.getId(), intent.getId(),
					corpusBo.getSlots(), ownerId));
		} else {
			// 意图已存在，但是与参数领域不匹配
			if (searchDomain == null || !MapUtils.getLong(intentMap, "domainId").equals(searchDomain.getId())) {
				throw new SkillException("1007");
			}

			corpusService.insert(new Corpus(corpusBo.getContent(), MapUtils.getLong(intentMap, "domainId"),
					MapUtils.getLong(intentMap, "intentId"), corpusBo.getSlots(), ownerId));
		}

		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * 
	 * @title: list
	 * @description: 语料展示
	 * @param response
	 * @param domainId 领域id
	 * @param intentId 意图id
	 * @param size 分页参数条数
	 * @param num 分页参数页数
	 * @return
	 */
	@GetMapping(value = "/list")
	public List<Corpus> list(HttpServletResponse response, @RequestParam(required = false) Long domainId,
			@RequestParam(required = false) Long intentId, @RequestParam(required = false) Integer size,
			@RequestParam(required = false) Integer num) {

		logger.info(String.format("Into %s %s ", this.getClass().getName(), "list"));
		log.info(String.format("Into %s %s ", this.getClass().getName(), "list"));

		CorpusBuilder corpusBuilder = Corpus.builder().ownerId(ownerId);

		if (domainId != null) {
			corpusBuilder.domainId(domainId);
		}
		if (intentId != null) {
			corpusBuilder.intentId(intentId);
		}

		size = size != null ? size : 0;
		num = num != null ? num : 0;

		return corpusService.selectPage(getPage(size, num), new EntityWrapper<>(corpusBuilder.build())).getRecords();
	}

	/**
	 * @title: remove
	 * @description: 语料删除
	 * @param domainId 领域Id
	 * @param intentId 意图Id
	 * @param corpusIds 语料Id数组
	 */
	@DeleteMapping(value = "/remove")
	@Transactional
	public void remove(HttpServletResponse response,
			@RequestBody @Validated @NotNull(message = "1008") CorpusBo corpusBo) {
		// 按语料删除
		if (corpusBo.getCorpusIds() != null && corpusBo.getCorpusIds().length > 0) {
			corpusService.deleteBatchIds(Arrays.asList(corpusBo.getCorpusIds()));
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		// 按意图删除
		if (corpusBo.getIntentId() != null) {
			intentService.deleteById(corpusBo.getIntentId());
			corpusService.delete(new EntityWrapper<Corpus>(Corpus.builder().intentId(corpusBo.getIntentId()).build()));
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		// 按领域删除
		if (corpusBo.getDomainId() != null) {
			domainService.deleteById(corpusBo.getDomainId());
			intentService.delete(new EntityWrapper<Intent>(Intent.builder().domainId(corpusBo.getDomainId()).build()));
			corpusService.delete(new EntityWrapper<Corpus>(Corpus.builder().domainId(corpusBo.getDomainId()).build()));
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}
	}

	/**
	 * @title: modify
	 * @description: 语料更新
	 * @param response
	 * @param corpusBo
	 */
	@PutMapping("/modify")
	public void modify(HttpServletResponse response, @RequestBody @Validated(CorpusBo.Modify.class) CorpusBo corpusBo) {
		corpusService.updateById(Corpus.builder().id(corpusBo.getCorpusId()).content(corpusBo.getContent()).build());
		response.setStatus(HttpServletResponse.SC_OK);
	}

	// TODO 语料服务上报语料数据
	// TODO 语料服务触发训练数据

}
