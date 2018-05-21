package com.ubtechinc.resource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ubtechinc.resource.api.IResourceTypeService;
import com.ubtechinc.resource.entity.ResourceType;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hugui
 * @since 2018-05-02
 */
@RestController
@RequestMapping("/resourcetype")
public class ResourceTypeController {

	@Autowired
	private IResourceTypeService resourceTypeService;

	@GetMapping("/list")
	public List<ResourceType> list() {
		return resourceTypeService.selectList(new EntityWrapper<ResourceType>(ResourceType.builder().build()));
	}

}
