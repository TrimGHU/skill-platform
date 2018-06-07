package com.ubtechinc.rule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubtechinc.rule.api.IRuleResourceService;
import com.ubtechinc.rule.entity.RuleResource;
import com.ubtechinc.rule.mapper.RuleResourceMapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hugui
 * @since 2018-05-14
 */

@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class RuleResourceServiceImpl extends ServiceImpl<RuleResourceMapper, RuleResource> implements IRuleResourceService {

	@Autowired
	private RuleResourceMapper mapper;
	
	@Override
	public void deleteByRuleId(List<Long> ruleIds) {
		mapper.deleteByRuleId(ruleIds);
	}

}
