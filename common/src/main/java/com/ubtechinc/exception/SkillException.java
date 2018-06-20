package com.ubtechinc.exception;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: SkillException.java
 * @Prject: corpus
 * @Package: com.ubtechinc.corpus.exception
 * @Description: TODO
 * @author: HuGui
 * @date: 2018年4月16日 下午4:21:38
 * @version: V1.0
 */

@SuppressWarnings("serial")
public class SkillException extends RuntimeException {

	public SkillException() {
		super();
	}

	public SkillException(String code) {
		super(code);
	}

	public SkillException(String code, Throwable cause) {
		super(code, cause);
	}
}
