package com.example.fileopera.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Kayla, Ye
 * @Description: 1、创建拦截器
 * @Date:Created in 2:52 PM 3/1/2019
 */
public class PageInterceptor implements HandlerInterceptor {

    //在控制器执行前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("POST".equals(request.getMethod())){
            System.out.println("控制器拦截器");
        }
        return true;
    }


    //控制器执行完成后调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    //整个请求结束后调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
