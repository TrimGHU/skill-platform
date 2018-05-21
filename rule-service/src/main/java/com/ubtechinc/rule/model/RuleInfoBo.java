package com.ubtechinc.rule.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ubtechinc.rule.model.RuleBo.Modify;
import com.ubtechinc.rule.model.RuleBo.New;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: RuleInfoBo.java
 * @Prject: rule
 * @Package: com.ubtechinc.rule.model
 * @Description: TODO
 * @author: HuGui
 * @date: 2018年5月16日 下午6:08:49
 * @version: V1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfoBo implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(groups = { New.class, Modify.class }, message = "3003")
	private String domainName;

	@NotBlank(groups = { New.class, Modify.class }, message = "3004")
	private String intentName;

	@NotNull(groups = { New.class, Modify.class }, message = "3005")
	private Integer round;

	private List<ResourceBo> resources;
}