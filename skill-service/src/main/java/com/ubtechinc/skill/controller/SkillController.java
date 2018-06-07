package com.ubtechinc.skill.controller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ubtechinc.resource.api.IResourceService;
import com.ubtechinc.resource.entity.Resource;
import com.ubtechinc.rule.api.IRuleResourceService;
import com.ubtechinc.rule.api.IRuleService;
import com.ubtechinc.rule.constant.RuleConstants;
import com.ubtechinc.rule.entity.Rule;
import com.ubtechinc.rule.entity.RuleResource;
import com.ubtechinc.skill.model.SkillBo;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: SkillController.java
 * @Prject: skill
 * @Package: com.ubtechinc.skill.controller
 * @Description: 技能控制器类
 * @author: HuGui
 * @date: 2018年5月30日 下午6:02:07
 * @version: V1.0
 */

@RestController
@RequestMapping("/skill")
public class SkillController {

	@Reference(version = "1.0.0", application = "${dubbo.application.id}", registry = "${dubbo.registry.id}")
	private IRuleService ruleService;

	@Reference(version = "1.0.0", application = "${dubbo.application.id}", registry = "${dubbo.registry.id}")
	private IResourceService resourceService;

	@Reference(version = "1.0.0", application = "${dubbo.application.id}", registry = "${dubbo.registry.id}")
	private IRuleResourceService ruleResourceService;

	@PostMapping("/hit")
	public Object hit(HttpServletResponse response, @RequestBody @Valid SkillBo skillBo) {
		Map<String, Object> returnMap = new LinkedHashMap<>();

		/**
		 * TODO 校验用户权限
		 */
		Long ownerId = 10000L;

		Long ruleId = skillBo.getRuleId();
		Rule searchRule;

		// 首次命中技能逻辑
		if (ruleId == null) {
			// 根据用户，领域，意图查询定义的技能逻辑
			searchRule = Rule.builder().ownerId(ownerId).domainName(skillBo.getDomainName())
					.intentName(skillBo.getIntentName()).build();
		}
		// 参数携带ruleId表示多轮交互, 只命中与此相关的多轮交互逻辑规则
		else {
			searchRule = Rule.builder().ownerId(ownerId).domainName(skillBo.getDomainName())
					.intentName(skillBo.getIntentName()).mainRuleId(ruleId).build();
		}

		Rule rule = ruleService.selectOne(new EntityWrapper<Rule>(searchRule));
		if (rule == null) {
			return null;
		}

		List<RuleResource> ruleResourceList = ruleResourceService
				.selectList(new EntityWrapper<RuleResource>(RuleResource.builder().ruleId(rule.getId()).build()));

		// 逻辑规则资源为空，直接返回当前规则Id，用于可能会存在的下轮交互
		if (ruleResourceList == null || ruleResourceList.isEmpty()) {
			returnMap.put("ruleId", rule.getId());
			return returnMap;
		}

		returnMap.put("resources", getResources(ruleResourceList, ownerId));
		returnMap.put("ruleId", rule.getId());
		return returnMap;
	}

	/**
	 * 根据逻辑规则获取当前规则下需要返回的资源信息
	 * 
	 * @title: getResources
	 * @param ruleResourceList
	 * @return
	 */
	public List<Map<String, Object>> getResources(List<RuleResource> ruleResourceList, Long ownerId) {
		List<Map<String, Object>> resourceList = new LinkedList<>();

		for (Iterator<RuleResource> rrIterator = ruleResourceList.iterator(); rrIterator.hasNext();) {
			RuleResource ruleResource = rrIterator.next();

			// 先随机出需要返回的资源
			if (RuleConstants.RULE_RESOURCE_TYPE_DANDOM.equals(ruleResource.getReturnType())) {
				Map<String, Object> randomResource = getRandomResource(ruleResource, ownerId);

				if (randomResource != null && !randomResource.isEmpty()) {
					resourceList.add(randomResource);
				}
			}

			// 若是全部返回则查出全部资源链接或文本
			if (RuleConstants.RULE_RESOURCE_TYPE_ALL.equals(ruleResource.getReturnType())) {
				Map<String, Object> allResource = getAllResources(ruleResource, ownerId);

				if (allResource != null && !allResource.isEmpty()) {
					resourceList.add(allResource);
				}
			}
		}

		return resourceList;
	}

	/**
	 * 获取随机资源
	 * 
	 * @title: getRandomResource
	 * @param ruleResource
	 * @param ownerId
	 * @return
	 */
	public Map<String, Object> getRandomResource(RuleResource ruleResource, Long ownerId) {
		Map<String, Object> resourceMap = new LinkedHashMap<>();

		String[] resourceIds = ruleResource.getResourceIds().split(",");
		String resouceId = resourceIds[new Random().nextInt(resourceIds.length)];
		Resource resource = resourceService.selectById(resouceId);

		// 防止返回非所属用户资源
		if (resource != null && resource.getOwnerId().equals(ownerId)) {
			resourceMap.put("type", resource.getTypeCode());
			resourceMap.put("content", resource.getContent());
		}

		return resourceMap;
	}

	/**
	 * 获取全部资源
	 * 
	 * @title: getAllResources
	 * @param ruleResource
	 * @param ownerId
	 * @return
	 */
	public Map<String, Object> getAllResources(RuleResource ruleResource, Long ownerId) {
		Map<String, Object> resourceMap = new LinkedHashMap<>();

		String[] resourceIds = ruleResource.getResourceIds().split(",");
		List<Resource> resources = resourceService.selectBatchIds(Arrays.asList(resourceIds));

		if (resources == null || resources.isEmpty()) {
			return null;
		}

		StringBuilder contents = new StringBuilder();
		for (Iterator<Resource> iterator = resources.iterator(); iterator.hasNext();) {
			Resource resource = iterator.next();

			if (resource.getOwnerId().equals(ownerId)) {
				contents.append(resource.getContent() + ";");
			}
		}

		if (contents.length() == 0) {
			return null;
		}

		resourceMap.put("type", resources.get(0).getTypeCode());
		resourceMap.put("content", contents);
		return resourceMap;
	}
}
