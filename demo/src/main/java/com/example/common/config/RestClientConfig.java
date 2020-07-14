package com.example.common.config;

import com.example.common.utils.EnvHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * restemplate 请求拦截器
 */

@Configuration
@Slf4j
public class RestClientConfig {

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(3000);
    requestFactory.setReadTimeout(3000);
    restTemplate.setRequestFactory(requestFactory);

    // 通用请求拦截器
    restTemplate.setInterceptors(Arrays.asList((request, body, execution) -> {
      request.getHeaders().add("authorization", EnvHolder.getHolder().getJwtToken());

      if (EnvHolder.getHolder().getEssSystem() != null) {
        request.getHeaders().add("ess-system", EnvHolder.getHolder().getEssSystem().toString());
      }
      request.getHeaders().add("content-type", MediaType.APPLICATION_JSON_VALUE);
      request.getHeaders().add("userid", EnvHolder.getHolder().getUserid());
      request.getHeaders().add("tenantids", "-1");

//      log.info("restTemplate request header: {}", JSON.toJSON(request.getHeaders().toSingleValueMap()));
      return execution.execute(request, body);
    }));

    return restTemplate;
  }
}
