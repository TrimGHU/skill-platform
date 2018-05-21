package com.ubtechinc.corpus.api;

import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.ubtechinc.corpus.entity.Intent;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hugui
 * @since 2018-04-02
 */


public interface IIntentService extends IService<Intent> {

	LinkedHashMap<String,Object> selectByName(@Param("name") String name);
	
}
