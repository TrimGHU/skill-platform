package com.ubtechinc.resource.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
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
 * @Title: ResourceBo.java
 * @Prject: resource
 * @Package: com.ubtechinc.resource.model
 * @Description: TODO
 * @author: HuGui
 * @date: 2018年5月2日 下午3:57:37
 * @version: V1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceBo implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(groups = Modify.class, message = "2001")
	private Long resourceId;

	@NotBlank(groups = { Modify.class, New.class }, message = "2002")
	private String typeCode;

	@NotBlank(groups = { Modify.class, New.class }, message = "2003")
	@Pattern(groups = { Modify.class, New.class }, regexp = "^[a-zA-Z0-9]+$", message = "2006")
	@Size(groups = { Modify.class, New.class }, max = 100, min = 1, message = "2007")
	private String name;

	@NotBlank(groups = { Modify.class, New.class }, message = "2004")
	private String content;

	@NotNull(groups = Remove.class, message = "2005")
	private Long[] resourceIds;

	public interface Modify {

	}

	public interface New {

	}

	public interface Remove {

	}
}
