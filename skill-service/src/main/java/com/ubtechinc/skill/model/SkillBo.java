package com.ubtechinc.skill.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: SkillBo.java 
 * @Prject: skill
 * @Package: com.ubtechinc.skill.model 
 * @Description: skill请求业务实体类s
 * @author: HuGui   
 * @date: 2018年6月1日 下午2:02:38 
 * @version: V1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillBo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message="4001")
	private String domainName;
	
	@NotBlank(message="4002")
	private String intentName;
	
	private Long ruleId;

}
