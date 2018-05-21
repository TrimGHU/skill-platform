package com.ubtechinc.rule.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: MybatisPlusConfig.java 
 * @Prject: rule
 * @Package: com.ubtechinc.rule.configuration 
 * @Description: TODO
 * @author: HuGui   
 * @date: 2018年4月17日 上午10:58:03 
 * @version: V1.0
 */

@Configuration
@MapperScan({"com.ubtechinc.rule.mapper*",})
public class MybatisPlusConfig {

    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
    
    

}
