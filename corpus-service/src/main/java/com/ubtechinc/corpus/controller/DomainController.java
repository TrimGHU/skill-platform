package com.ubtechinc.corpus.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ubtechinc.corpus.api.IDomainService;
import com.ubtechinc.corpus.entity.Domain;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hugui
 * @since 2018-04-02
 */
@RestController
@RequestMapping("/domain")
public class DomainController extends BaseController {

	@Autowired
	private IDomainService domainService;

	/**
	 * 
	 * @title: query
	 * @description:查询领域信息
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/query")
	public List<Domain> query(HttpServletResponse response) {
		return domainService.selectList(new EntityWrapper<Domain>(Domain.builder().ownerId(ownerId).build()));
	}

	/**
	 * 不允许当前修改领域名称
	 * @title: modify
	 * @description: 更新领域名称
	 * @param response
	 * @param domainBo
	@PutMapping("/modify")
	public void modify(HttpServletResponse response, @RequestBody @Validated DomainBo domainBo) {
		domainService.updateById(Domain.builder().id(domainBo.getDomainId()).name(domainBo.getName()).build());
		response.setStatus(HttpServletResponse.SC_OK);
	}
	 */
}
