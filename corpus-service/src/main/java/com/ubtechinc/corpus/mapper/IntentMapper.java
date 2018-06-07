package com.ubtechinc.corpus.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ubtechinc.corpus.entity.Intent;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hugui
 * @since 2018-04-02
 */

public interface IntentMapper extends BaseMapper<Intent> {

	Map<String,Object> selectByName(@Param("name") String name);
	
	Long selectIdByName(@Param("name") String name);
}
