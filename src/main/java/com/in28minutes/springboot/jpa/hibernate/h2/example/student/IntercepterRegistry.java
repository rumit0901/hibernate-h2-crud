package com.in28minutes.springboot.jpa.hibernate.h2.example.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class IntercepterRegistry implements WebMvcConfigurer  {

	@Autowired
	private LoggerInterceptor loggerInterceptor;
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggerInterceptor);
	}
}
