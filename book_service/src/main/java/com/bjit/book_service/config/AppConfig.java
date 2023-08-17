package com.bjit.book_service.config;

import com.bjit.book_service.model.BookResponseModel;
import com.bjit.book_service.model.BuyBookModel;
import com.bjit.book_service.model.InventoryModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public InventoryModel inventoryModel() {
        return new InventoryModel();
    }

    @Bean
    public BookResponseModel bookResponseModel() {
        return new BookResponseModel();
    }

    @Bean
    public HttpHeaders httpHeaders() {
        return new HttpHeaders();
    }

    @Bean
    public BuyBookModel buyBookModel() {
        return new BuyBookModel();
    }

//    @Bean
//    public FilterRegistrationBean<JwtFilter> jwtFilter() {
//        FilterRegistrationBean<JwtFilter> filter = new FilterRegistrationBean<>();
//        filter.setFilter(new JwtFilter());
//
//        // Register the JwtFilter for the "/api/v1/blog/restrictedUser" URL pattern
//        filter.addUrlPatterns("/api/v1/blog/restrictedUser");
//
//        // Register the JwtFilter for the "/api/v1/blog/restrictedAdmin" URL pattern
//        filter.addUrlPatterns("/api/v1/blog/restrictedAdmin");
//        filter.addUrlPatterns("/book-service/get-it");
//
//        return filter;
//    }
}
