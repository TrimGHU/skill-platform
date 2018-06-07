package com.ubtechinc.rule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubtechinc.rule.api.IRuleService;
import com.ubtechinc.rule.entity.Rule;
import com.ubtechinc.rule.mapper.RuleMapper;

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
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements IRuleService {

	@Autowired
	private RuleMapper mapper;

	@Override
	public List<Long> selectSubRuleIdsByMainRuleId(Long mainRuleId) {
		return mapper.selectSubRuleIdsByMainRuleId(mainRuleId);
	}

}
