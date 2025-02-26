package com.ezotex.comm.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Value("${file_img_slash}")
	String file_img_slash;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**") // 해당 경로의 요청이 올 때
				.addResourceLocations("file://" + file_img_slash) // classpath 기준으로 'm' 디렉토리 밑에서 제공
				.setCachePeriod(20); // 캐싱 지정
	}
}
