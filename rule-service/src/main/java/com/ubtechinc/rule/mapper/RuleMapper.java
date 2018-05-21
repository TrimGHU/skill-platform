package com.ubtechinc.rule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ubtechinc.rule.entity.Rule;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hugui
 * @since 2018-05-14
 */
public interface RuleMapper extends BaseMapper<Rule> {

	/**
	 * 根据主规则查找子规则id
	 * @title: selectSubRuleIdsByMainRuleId 
	 * @param mainRuleId
	 * @return
	 */
	List<Long> selectSubRuleIdsByMainRuleId(@Param("mainRuleId") Long mainRuleId);

}
