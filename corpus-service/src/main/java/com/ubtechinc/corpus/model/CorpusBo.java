package com.ubtechinc.corpus.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: CorpusBo.java 
 * @Prject: corpus
 * @Package: com.ubtechinc.corpus.model 
 * @Description: TODO
 * @author: HuGui   
 * @date: 2018年4月20日 下午5:48:50 
 * @version: V1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorpusBo implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotNull(groups = Modify.class, message = "1009")
	@Null(groups = New.class)
	private Long corpusId;

	@NotBlank(groups = { Modify.class, New.class }, message = "1001")
	private String content;

	@NotBlank(groups = New.class, message = "1011")
	@Size(groups = New.class, max = 100, min = 1, message = "1002")
	@Pattern(groups = New.class, regexp = "^[a-zA-Z0-9]+$", message = "1003")
	private String domainName;

	@Pattern(groups = New.class, regexp = "^[a-zA-Z0-9]+$", message = "1005")
	@Size(groups = New.class, max = 255, min = 1, message = "1004")
	@NotBlank(groups = New.class, message = "1013")
	private String intentName;

	@NotBlank(groups = New.class, message = "1006")
	private String slots;
	
	private Long domainId;
	private Long intentId;
	private Integer size;
	private Integer num;
	private Long[] corpusIds;

	public interface Modify {
	}

	public interface New {
	}
}
