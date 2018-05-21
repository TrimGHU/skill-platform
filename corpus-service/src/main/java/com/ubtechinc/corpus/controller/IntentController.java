package com.ubtechinc.corpus.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ubtechinc.corpus.api.IIntentService;
import com.ubtechinc.corpus.entity.Intent;
import com.ubtechinc.corpus.exception.SkillException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hugui
 * @since 2018-04-02
 */
@RestController
@RequestMapping("/intent")
public class IntentController {

	@Autowired
	private IIntentService intentService;

	/**
	 * @title: query
	 * @description: 根据领域查询所属意图
	 * @param response
	 * @param domainId
	 * @return
	 */
	@GetMapping(value = "/query")
	public List<Intent> query(HttpServletResponse response, String domainId) {
		if (StringUtils.isEmpty(domainId)) {
			throw new SkillException("1010");
		}

		return intentService
				.selectList(new EntityWrapper<Intent>(Intent.builder().domainId(Long.valueOf(domainId)).build()));
	}

	/**
	 * 不允许当前修改意图名称
	 * @title: modify
	 * @description: 修改意图名称
	 * @param response
	 * @param intentBo
	@PutMapping("/modify")
	public void modify(HttpServletResponse response, @RequestBody @Validated IntentBo intentBo) {
		intentService.updateById(Intent.builder().id(intentBo.getIntentId()).name(intentBo.getName()).build());
		response.setStatus(HttpServletResponse.SC_OK);
	}
	 */

}
