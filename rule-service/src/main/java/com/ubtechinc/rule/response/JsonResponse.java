package com.ubtechinc.rule.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: JsonResponse.java
 * @Prject: rule
 * @Package: com.ubtechinc.rule.response
 * @Description: TODO
 * @author: HuGui
 * @date: 2018年4月16日 下午1:53:20
 * @version: V1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse {
	private String code;
	private String message;
}
