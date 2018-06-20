package com.ubtechinc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ubtechinc.annotation.AuthorizationInterceptor;

@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthorizationInterceptor authInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor);
	}

}
