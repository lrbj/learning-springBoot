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

    String authorization = request.getHeader("authorization");
    String essSystem = request.getHeader("ess-system");
    String userid = request.getHeader("userid");
    String tenantIdsStr = request.getHeader("tenantids");
    if (authorization == null) {
      response.setContentType("application/json;charset=utf-8");
      ResponseObject resObj = new ResponseObject<>(ErrorEnum.MISS_HEADER.getCode(), "请求头缺失：authorization", null);
      response.getWriter().write(JSON.toJSONString(resObj));
      return false;
    }
    if (essSystem == null) {
      response.setContentType("application/json;charset=utf-8");
      ResponseObject resObj = new ResponseObject<>(ErrorEnum.MISS_HEADER.getCode(), "请求头缺失：ess-system", null);
      response.getWriter().write(JSON.toJSONString(resObj));
      return false;
    }
    if (tenantIdsStr == null) {
      response.setContentType("application/json;charset=utf-8");
      ResponseObject resObj = new ResponseObject<>(ErrorEnum.MISS_HEADER.getCode(), "请求头缺失：tenantids", null);
      response.getWriter().write(JSON.toJSONString(resObj));
      return false;
    }

    env.setJwtToken(request.getHeader("authorization"));
    env.setEssSystem(Integer.parseInt(request.getHeader("ess-system")));
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
