package com.ubtechinc.skill.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ubtechinc.skill.response.JsonResponse;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: GlobalExceptionController.java
 * @Prject: rule
 * @Package: com.ubtechinc.skill.controller
 * @Description: 全局错误映射类
 * @author: HuGui
 * @date: 2018年4月20日 上午9:57:02
 * @version: V1.0
 */

@ControllerAdvice
@PropertySource(value = { "classpath:error.properties" }, encoding = "UTF-8")
public class GlobalExceptionHandler {

	@Autowired
	private Environment env;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public JsonResponse handleBadRequestException(MethodArgumentNotValidException exception, HttpServletRequest request,
			HttpServletResponse response) {

		String errorCode = exception.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse(exception.getMessage());

		return JsonResponse.builder().code(errorCode).message(env.getProperty(errorCode)).build();
	}
	

	@ExceptionHandler(SkillException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public JsonResponse handleSKillException(SkillException exception, HttpServletRequest request,
			HttpServletResponse response) {
		return JsonResponse.builder().code(exception.getMessage()).message(env.getProperty(exception.getMessage()))
				.build();
	}
}
