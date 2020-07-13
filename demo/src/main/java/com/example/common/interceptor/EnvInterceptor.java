package com.example.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.common.constant.ErrorEnum;
import com.example.common.entity.Env;
import com.example.common.utils.EnvHolder;
import com.example.common.utils.ResponseObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * 环境信息拦截
 */
public class EnvInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Env env = EnvHolder.getHolder();
      String authorization = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBsaWNhdGlvbnMiOlswLDEsMiwzXSwiY2xpZW50SVAiOiIiLCJkZXZpY2VUeXBlIjowLCJkb21haW4iOiIiLCJleHAiOjE1OTQwMjMyODYsImdyb3VwSURzIjoiIiwiaWF0IjoxNTk0MDE2MDg2LCJpZCI6IjM5M0U3QzNELTBBMzEtNDBDRC1BNTY3LUVFNURGRThBQjQ5MSIsImxvY2F0aW9uSURzIjoiIiwibW9iaWxlQWNjZXNzIjpbMCwxLDIsM10sInJlc291cmNlIjozLCJ0ZW5hbnRJRHMiOiItMSIsInVzZXJOYW1lIjoiYWRtaW4ifQ.m21jkg96-tKo_BS7niJxXUstKBe-so-BLImUdDF_W2o";
      String essSystem = "3";
      String userid = "zlt1";
      String tenantIdsStr = "1";
//    env.setJwtToken(request.getHeader("authorization"));
//    env.setEssSystem(Integer.parseInt(request.getHeader("ess-system")));
//    env.setUserid(userid);
    env.setJwtToken(authorization);
    env.setEssSystem(Integer.parseInt(essSystem));
    env.setUserid(userid);
    String tenantId = "";
    Set<String> tenantIds = new HashSet<>();
    if (tenantIdsStr.contains("-1")) {
      tenantId = "-1";
      tenantIds.add("-1");
    } else {
      String[] tenantIdArray = tenantIdsStr.split(",");

      tenantId = tenantIdArray[0];
      tenantIds = new HashSet<>(Arrays.asList(tenantIdArray[0]));
    }
    env.setTenantId(tenantId);
    env.setTenantIds(tenantIds);
    EnvHolder.setHolder(env);
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    EnvHolder.remove();
  }
}
