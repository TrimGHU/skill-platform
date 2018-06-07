package com.ubtechinc.skill.exception;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: SkillException.java
 * @Prject: rule
 * @Package: com.ubtechinc.skill.exception
 * @Description: 通用Skill异常类
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
