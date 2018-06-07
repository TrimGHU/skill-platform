package com.ubtechinc.rule.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ubtechinc.rule.api.IRuleResourceService;
import com.ubtechinc.rule.api.IRuleService;
import com.ubtechinc.rule.entity.Rule;
import com.ubtechinc.rule.entity.Rule.RuleBuilder;
import com.ubtechinc.rule.entity.RuleResource;
import com.ubtechinc.rule.exception.SkillException;
import com.ubtechinc.rule.model.ResourceBo;
import com.ubtechinc.rule.model.RuleBo;
import com.ubtechinc.rule.model.RuleInfoBo;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hugui
 * @since 2018-05-14
 */
@RestController
@RequestMapping("/rule")
public class RuleController extends BaseController {

	@Autowired
	private IRuleService ruleService;

	@Autowired
	private IRuleResourceService ruleResourceService;

	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/add")
	public void add(HttpServletResponse response, @RequestBody RuleBo ruleBo) {

		// 检查当前领域和意图下是否存在逻辑规则
		RuleInfoBo ruleInfoBo = ruleBo.getRuleInfos().get(0);
		String domainName = ruleInfoBo.getDomainName();
		String intentName = ruleInfoBo.getIntentName();

		int count = ruleService.selectCount(new EntityWrapper<Rule>(
				Rule.builder().ownerId(ownerId).domainName(domainName).intentName(intentName).build()));
		if (count > 0) {
			throw new SkillException("3009");
		}

		Long mainRuleId = null;
		List<RuleInfoBo> ruleInfos = ruleBo.getRuleInfos();
		for (RuleInfoBo ruleInfo : ruleInfos) {
			Rule rule = Rule.builder().domainName(ruleInfo.getDomainName()).intentName(ruleInfo.getIntentName())
					.isMultiple(ruleBo.getIsMultiple()).round(ruleInfo.getRound()).ownerId(ownerId)
					.createTime(new Date()).build();
			if (ruleInfo.getRound() > 1 && mainRuleId != null) {
				rule.setMainRuleId(mainRuleId);
			}

			ruleService.insert(rule);

			if (ruleInfo.getRound() == 1) {
				mainRuleId = rule.getId();
			}

			List<RuleResource> ruleResources = new LinkedList<>();
			List<ResourceBo> resources = ruleInfo.getResources();
			if (resources == null || resources.isEmpty()) {
				continue;
			}
			resources.parallelStream()
					.forEach(resource -> ruleResources
							.add(RuleResource.builder().createTime(new Date()).resourceIds(resource.getResourceIds())
									.returnType(resource.getReturnType()).ruleId(rule.getId()).build()));
			ruleResourceService.insertBatch(ruleResources);
		}

		response.setStatus(HttpServletResponse.SC_OK);
	}

	@GetMapping(value = "/list")
	public Page<Rule> list(HttpServletResponse response, @RequestParam(required = false) Integer size,
			@RequestParam(required = false) Integer num) {
		// 只查询当前第一轮的交互逻辑规则，后续轮数通过详情来展示
		RuleBuilder builder = Rule.builder().ownerId(ownerId).mainRuleId(null);

		Page<Rule> page = getPage(size, num);

		return ruleService.selectPage(page, new EntityWrapper<>(builder.build()));
	}

	@GetMapping(value = "/query/{ruleId}")
	public List<Object> query(HttpServletResponse response,
			@Validated @NotNull(message = "3006") @PathVariable(value = "ruleId") Long ruleId) {
		ArrayList<Object> returnList = new ArrayList<>(10);

		Rule rule = ruleService.selectById(ruleId);
		if (rule == null) {
			throw new SkillException("3007");
		}
		if (!ownerId.equals(rule.getOwnerId())) {
			throw new SkillException("3008");
		}

		// 查询多轮技能逻辑
		List<Rule> ruleList = Arrays.asList(rule);
		if (rule.getIsMultiple() == 1) {
			ruleList.addAll(
					ruleService.selectList(new EntityWrapper<Rule>(Rule.builder().mainRuleId(rule.getId()).build())
							.orderAsc(Arrays.asList("round"))));

			// 查询多轮技能逻辑对应的资源文件及返回类型
			ruleList.parallelStream().forEach(e -> {
				Map<String, Object> ruleMap = new LinkedHashMap<>();

				List<RuleResource> resourceRuleList = ruleResourceService
						.selectList(new EntityWrapper<RuleResource>(RuleResource.builder().ruleId(e.getId()).build()));

				ruleMap.put("rule", e);
				ruleMap.put("ruleResources", resourceRuleList);

				returnList.add(ruleMap);
			});
		}
		// 单轮交互的技能逻辑
		else {
			Map<String, Object> ruleMap = new LinkedHashMap<>();
			List<RuleResource> resourceRuleList = ruleResourceService
					.selectList(new EntityWrapper<RuleResource>(RuleResource.builder().ruleId(rule.getId()).build()));

			ruleMap.put("rule", rule);
			ruleMap.put("ruleResources", resourceRuleList);

			returnList.add(ruleMap);
		}

		return returnList;
	}

	@Transactional(rollbackFor = Exception.class)
	@PutMapping("/modify")
	public void modify(HttpServletResponse response, @RequestBody RuleBo ruleBo) {
		// 编辑场景太过复杂，此更新接口处理逻辑为删除原来的关联数据，重新插入新的数据
		// 当然在此场景中，很多小范围的修改，会给出很多相应的接口

		// 删除相关数据
		delete(response, ruleBo);

		// 调用增加的方法
		add(response, ruleBo);

		response.setStatus(HttpServletResponse.SC_OK);
	}

	
	@Transactional(rollbackFor = Exception.class)
	@DeleteMapping("/delete")
	public void delete(HttpServletResponse response, @RequestBody RuleBo ruleBo) {
		int isMultiple = ruleBo.getIsMultiple();

		// 单轮交互
		if (isMultiple == 0) {
			ruleService.deleteById(ruleBo.getMainRuleId());
			ruleResourceService.deleteByRuleId(Arrays.asList(ruleBo.getMainRuleId()));
		}
		// 多轮交互
		else {
			List<Long> ruleIds = ruleService.selectSubRuleIdsByMainRuleId(ruleBo.getMainRuleId());
			if (ruleIds == null) {
				ruleIds = new ArrayList<>();
			}
			ruleIds.add(ruleBo.getMainRuleId());

			ruleService.deleteBatchIds(ruleIds);
			ruleResourceService.deleteByRuleId(ruleIds);
		}
	}
}
