package com.ubtechinc.rule.api;

import com.ubtechinc.rule.entity.Rule;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hugui
 * @since 2018-05-14
 */
public interface IRuleService extends IService<Rule> {

	List<Long> selectSubRuleIdsByMainRuleId(Long mainRuleId);
	
}
