package com.ebeauty;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/main").setViewName("user_main");
		registry.addViewController("/expertmain").setViewName("expert_main");
		registry.addViewController("/ebeauty-admin.ca").setViewName("adminmain");
	}
	
}
