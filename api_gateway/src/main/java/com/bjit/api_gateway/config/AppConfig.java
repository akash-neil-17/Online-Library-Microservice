package com.bjit.api_gateway.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new JwtFilter());
        filter.addUrlPatterns("/book-service/create");
        filter.addUrlPatterns("/book-service/update");
        filter.addUrlPatterns("/book-service/delete");
        filter.addUrlPatterns("/book-service/test");
        filter.addUrlPatterns("/book-service/book/all");
        filter.addUrlPatterns("/book-service/book/*");
        filter.addUrlPatterns("/book-service/buy");
        return filter;
    }
}