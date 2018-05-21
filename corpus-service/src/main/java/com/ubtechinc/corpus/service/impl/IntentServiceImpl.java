package com.ubtechinc.corpus.service.impl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
//import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubtechinc.corpus.api.IIntentService;
import com.ubtechinc.corpus.entity.Intent;
import com.ubtechinc.corpus.mapper.IntentMapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hugui
 * @since 2018-04-02
 */

@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class IntentServiceImpl extends ServiceImpl<IntentMapper, Intent> implements IIntentService {

	@Autowired
	private IntentMapper mapper;
	
	@Override
	public LinkedHashMap<String,Object> selectByName(String name) {
		return mapper.selectByName(name);
	}
	
}

