package com.example.ssldemo.config;

import com.example.ssldemo.common.DataInitRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
    @Autowired
    HttpsClientRequestFactory requestFactory;
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    requestFactory.setConnectTimeout(3000);
    requestFactory.setReadTimeout(3000);
    restTemplate.setRequestFactory(requestFactory);

    // 通用请求拦截器
    restTemplate.setInterceptors(Arrays.asList((request, body, execution) -> {
        request.getHeaders().add("authorization","wqqqeqwe");

         request.getHeaders().add("ess-system", "2");

        request.getHeaders().add("content-type", MediaType.APPLICATION_JSON_VALUE);
        request.getHeaders().add("userid", "H222");
        request.getHeaders().add("tenantids", "-1");
      request.getHeaders().add("auth-token",DataInitRunner.jwtToken);
      request.getHeaders().add("tenantids", "-1");
      System.out.println("local jwtoken"+DataInitRunner.jwtToken);
//      log.info("restTemplate request header: {}", JSON.toJSON(request.getHeaders().toSingleValueMap()));
      return execution.execute(request, body);
    }));

    return restTemplate;
  }
}
