package com.example.common.redis;

import com.example.common.config.BeanProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Redis key常量
 */
@Component
@Slf4j
public class RedisKeyConstant {

  private static String DEFAULT_PREFIX;

  private static Environment env;

  public static String HASH_allDeviceTypeMap = build("allDeviceTypeMap");

  public static String HASH_allDeviceMapById = build("allDeviceMapById");
  public static String HASH_allDeviceMapByNo = build("allDeviceMapByNo");
  public static String HASH_allDeviceGroupBySystemId = build("allDeviceGroupBySystemId");
  public static String HASH_allDeviceGroupByDeviceTypeId = build("allDeviceGroupByDeviceTypeId");

  private static String build(String value) {
    if (env == null) {
      try {
        env = BeanProvider.getBean(Environment.class);
        DEFAULT_PREFIX = env.getProperty("redis.key.defaultPrefix");
        log.info("redis default prefix: {}", DEFAULT_PREFIX);

      } catch (Exception e) {
        // TODO 暂时规避BeanProvider造成的空指针异常
        DEFAULT_PREFIX = "ess-polling:dev";
      }
    }

    StringBuilder builder = new StringBuilder();
    return builder.append(DEFAULT_PREFIX).append(":").append(value).toString();
  }

}

