package com.example.common.config;

import com.example.common.interceptor.EnvInterceptor;
import com.example.common.interceptor.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

  @Bean
  PageInterceptor pageInterceptor() {
    return new PageInterceptor();
  }

  @Bean
  EnvInterceptor envInterceptor() {
    return new EnvInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(pageInterceptor()).addPathPatterns("/**").order(1);
    registry.addInterceptor(envInterceptor()).addPathPatterns("/**").order(2);
  }
}
