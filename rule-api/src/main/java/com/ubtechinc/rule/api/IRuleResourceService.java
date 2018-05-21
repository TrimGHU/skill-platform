package com.ubtechinc.rule.api;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.ubtechinc.rule.entity.RuleResource;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hugui
 * @since 2018-05-14
 */
public interface IRuleResourceService extends IService<RuleResource> {

	void deleteByRuleId(List<Long> ruleIds);
	
}
