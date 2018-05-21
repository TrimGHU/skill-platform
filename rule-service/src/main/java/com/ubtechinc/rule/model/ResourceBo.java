package com.ubtechinc.rule.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: ResourceBo.java 
 * @Prject: rule
 * @Package: com.ubtechinc.rule.model 
 * @Description: TODO
 * @author: HuGui   
 * @date: 2018年5月16日 下午6:08:42 
 * @version: V1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String resourceIds;
	private String returnType;

}
