package com.ubtechinc.resource.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ubtechinc.resource.api.IResourceService;
import com.ubtechinc.resource.entity.Resource;
import com.ubtechinc.resource.exception.SkillException;
import com.ubtechinc.resource.model.ResourceBo;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hugui
 * @since 2018-05-02
 */
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {

	@Autowired
	private IResourceService resourceService;

	// 后台管理添加资源
	@PostMapping(value = "/add")
	public void add(HttpServletResponse response, @RequestBody @Validated(ResourceBo.New.class) ResourceBo resourceBo) {
		int count = resourceService.selectCount(
				new EntityWrapper<Resource>(Resource.builder().name(resourceBo.getName()).ownerId(ownerId).build()));
		if (count > 0) {
			throw new SkillException("2008");
		}

		resourceService.insert(Resource.builder().ownerId(ownerId).typeCode(resourceBo.getTypeCode())
				.name(resourceBo.getName()).content(resourceBo.getContent()).createTime(new Date()).build());

		response.setStatus(HttpServletResponse.SC_OK);
	}

	// 后台管理查看资源
	@GetMapping(value = "/list")
	public List<Resource> list(HttpServletResponse response, @RequestParam(required = false) String resourceType,
			@RequestParam(required = false) Integer size, @RequestParam(required = false) Integer num) {
		return resourceService
				.selectPage(getPage(size, num), new EntityWrapper<>(Resource.builder().typeCode(resourceType).build()))
				.getRecords();
	}

	@GetMapping(value = "/query")
	public List<Resource> query(HttpServletResponse response,
			@RequestParam @Validated @NotNull(message = "2005") Long[] resourceIds) {
		return resourceService.selectBatchIds(Arrays.asList(resourceIds));
	}

	// 后台管理修改资源
	@PutMapping(value = "/modify")
	public void modify(HttpServletResponse response,
			@RequestBody @Validated(ResourceBo.Modify.class) ResourceBo resourceBo) {
		int count = resourceService.selectCount(
				new EntityWrapper<Resource>(Resource.builder().name(resourceBo.getName()).ownerId(ownerId).build()));
		if (count > 0) {
			throw new SkillException("2008");
		}

		resourceService.updateById(Resource.builder().id(resourceBo.getResourceId()).content(resourceBo.getContent())
				.name(resourceBo.getName()).typeCode(resourceBo.getTypeCode()).build());
		response.setStatus(HttpServletResponse.SC_OK);

	}

	// 后台管理删除资源
	@DeleteMapping(value = "/delete")
	public void delete(HttpServletResponse response,
			@RequestBody @Validated(ResourceBo.Remove.class) ResourceBo resourceBo) {
		resourceService.deleteBatchIds(Arrays.asList(resourceBo.getResourceIds()));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
