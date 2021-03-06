package com.ubtechinc.corpus.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: IntentBo.java
 * @Prject: corpus
 * @Package: com.ubtechinc.corpus.model
 * @Description: TODO
 * @author: HuGui
 * @date: 2018年4月20日 下午6:00:02
 * @version: V1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntentBo implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "1012")
	private Long intentId;

	@NotBlank(message = "1013")
	private String name;

}
