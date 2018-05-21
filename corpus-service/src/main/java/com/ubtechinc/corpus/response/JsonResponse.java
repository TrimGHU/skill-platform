package com.ubtechinc.corpus.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: JsonResponse.java
 * @Prject: corpus
 * @Package: com.ubtechinc.corpus.response
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
