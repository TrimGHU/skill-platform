package com.ubtechinc.corpus.generate;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbType;

/**
 * 
 * Copyright © 2018 Ubtech. All rights reserved.
 * 
 * @Title: AutoGeneratorHelper.java 
 * @Prject: corpus
 * @Package: com.ubtechinc.corpus.generate 
 * @Description: TODO
 * @author: HuGui   
 * @date: 2018年4月3日 下午5:10:05 
 * @version: V1.0
 */

public class AutoGeneratorHelper {

	/**
	 * <p>
	 * 测试 run 执行
	 * </p>
	 * <p>
	 * 更多使用查看 http://mp.baomidou.com
	 * </p>
	 */
	public static void main(String[] args) {
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir("e:/");
		gc.setFileOverride(true);
		gc.setActiveRecord(true);// 开启 activeRecord 模式
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor("hugui");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert());
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("1234");
		dsc.setUrl("jdbc:mysql://localhost:3306/ubxdb?characterEncoding=utf8");
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// 字段名生成策略
		// strategy.setDbColumnUnderline(true).setNaming(NamingStrategy.underline_to_camel);
		// strategy.setFieldNaming(NamingStrategy.underline_to_camel);
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setModuleName("");
		pc.setParent("com.ubtechinc.corpus");// 自定义包路径
		pc.setController("controller");// 这里是控制器包名，默认 web
		mpg.setPackageInfo(pc);
		// 执行生成
		mpg.execute();
	}

}
