package com.ubtechinc.rule.controller;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: BaseController.java
 * @Prject: resource
 * @Package: com.ubtechinc.resource.controller
 * @Description: TODO
 * @author: HuGui
 * @date: 2018年4月16日 下午3:11:01
 * @version: V1.0
 */

public class BaseController {

	/**
	 *  TODO 后续集成用户ID
	 */
	Long ownerId = 10000L;
	
	private final int DEFAULT_SIZE = 10;

	public <T> Page<T> getPage(int size, int num) {
		if (size <= 0) {
			size = DEFAULT_SIZE;
		}
		if( num <= 0){
			num = 1;
		}
		return new Page<T>(num , size);
	}

}
