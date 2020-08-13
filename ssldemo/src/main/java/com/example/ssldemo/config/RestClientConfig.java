package com.example.ssldemo.config;

import com.example.ssldemo.common.DataInitRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author: zhenzhong.wang@honeywell.com
 * @date: 2020/1/8 11:00 AM
 */
@Configuration
@Slf4j
public class RestClientConfig {
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    SimpleClientHttpRequestFactory requestFactory = new HttpsClientRequestFactory();
    requestFactory.setConnectTimeout(3000);
    requestFactory.setReadTimeout(3000);
    restTemplate.setRequestFactory(requestFactory);

    // 通用请求拦截器
    restTemplate.setInterceptors(Arrays.asList((request, body, execution) -> {
      request.getHeaders().add("auth_token",DataInitRunner.jwtToken);
      System.out.println("local jwtoken"+DataInitRunner.jwtToken);
//      log.info("restTemplate request header: {}", JSON.toJSON(request.getHeaders().toSingleValueMap()));
      return execution.execute(request, body);
    }));

    return restTemplate;
  }
}