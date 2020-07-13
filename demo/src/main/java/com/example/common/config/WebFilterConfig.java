package com.example.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 过滤器配置类
 * spring boot配置过滤器方式有多种，通过此种配置方便管理filter并配置顺序
 */
//@Configuration
public class WebFilterConfig {
  Logger logger = LoggerFactory.getLogger(WebFilterConfig.class);

//    @Bean
//    public FilterRegistrationBean testFilterRegistration() {
//        FilterRegistrationBean regBean = new FilterRegistrationBean();
//        regBean.setFilter(new SSOFilter());
//        regBean.addUrlPatterns("/*");
//        regBean.setName("SSOFilter");
//        regBean.setOrder(1);
//        return regBean;
//    }
}
