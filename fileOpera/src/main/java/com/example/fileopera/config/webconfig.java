package com.example.fileopera.config;


import com.example.fileopera.interceptor.PageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: Kayla, Ye
 * @Description: 2、添加拦截器
 * @Date:Created in 2:51 PM 3/1/2019
 */
@Configuration
public class webconfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里可以添加多个拦截器
        registry.addInterceptor(new PageInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
