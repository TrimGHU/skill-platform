package com.ubtechinc.rule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ubtechinc.rule.entity.RuleResource;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hugui
 * @since 2018-05-14
 */
public interface RuleResourceMapper extends BaseMapper<RuleResource> {

	/**
	 * 根据规则id删除资源文件配置
	 * @title: deleteByRuleId 
	 * @param ruleIds
	 */
	void deleteByRuleId(@Param("ruleIds") List<Long> ruleIds);
	
}
