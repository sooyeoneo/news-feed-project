//package com.example.newsfeedproject.config;
//
//import com.example.newsfeedproject.filter.LoginFilter;
//import jakarta.servlet.Filter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WebConfig {
//    @Bean
//    public FilterRegistrationBean loginFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//
//        filterRegistrationBean.setFilter(new LoginFilter());
//
//        filterRegistrationBean.setOrder(2);
//
//        filterRegistrationBean.addUrlPatterns("/*");
//
//        return filterRegistrationBean;
//    }
//}
