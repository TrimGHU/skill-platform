package com.ubtechinc.rule.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: RuleBo.java
 * @Prject: rule
 * @Package: com.ubtechinc.rule.model
 * @Description: TODO
 * @author: HuGui
 * @date: 2018年5月15日 下午3:01:41
 * @version: V1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleBo implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(groups = { New.class, Modify.class, Remove.class }, message = "3001")
	private Integer isMultiple;

	@NotNull(groups = { New.class, Modify.class }, message = "3002")
	private List<RuleInfoBo> ruleInfos;

	@NotNull(groups = { Modify.class, Remove.class }, message = "3010")
	private Long mainRuleId;

	public interface New {
	}

	public interface Modify {
	}

	public interface Remove {
	}
}
