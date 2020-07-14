package com.example.common.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 环境信息
 */
@Data
public class Env {

  private String jwtToken;

  /**
   * 系统编号
   */
  private Integer essSystem;

  /**
   * 从api-gateway传来的userid, 需放在请求mic-gateway的header中
   */
  private String userid;

  /**
   * 从api-gateway传来的tenantid, 需放在请求mic-gateway的header中
   * 该值为tenantId[0]
   */
  private String tenantId;

  /**
   * 租户ID，所有的租户ID
   */
  private Set<String> tenantIds;


}
