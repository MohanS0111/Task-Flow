package com.todoapplication.todo.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.todoapplication.todo.filter.JWTFilter;

@Configuration
public class FilterRegistration {
	
	@Bean
	public FilterRegistrationBean jwtFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.addUrlPatterns("/api/v3/*");
		filterRegistrationBean.setFilter(new JWTFilter());
	    return filterRegistrationBean;
	}

}
