package com.example.fileopera.interceptor;

import com.example.fileopera.annotation.JpaPage;
import com.example.fileopera.constant.PageOperator;
import com.example.fileopera.util.PageHolder;
import com.example.fileopera.util.PageSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @Author: Kayla, Ye
 * @Description: 1、创建拦截器
 * @Date:Created in 2:52 PM 3/1/2019
 */
public class PageInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
    //在控制器执行前调用: 只拦截GET 以及 含有注解@JpaPage
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!"GET".equals(request.getMethod())){
           return true;
        }

        HandlerMethod method;
        //此处专门捕获异常，以防止程序在启动过程产生异常而停止
        try {
            method = (HandlerMethod) handler;
        } catch (Exception e) {
            logger.error("Page拦截出现异常");
            return true;
        }


        if(null == method.getMethodAnnotation(JpaPage.class)){
            return true;
        }

        //拦截提取请求参数
        PageSearch pageSearch = new PageSearch();
        //当参数无时，默认为分页查询
        pageSearch.setOpsType(request.getParameter("opsType")== null ? PageOperator.LIST:PageOperator.valueOf(request.getParameter("opsType")));

        // 分页操作
        if (PageOperator.LIST.equals(pageSearch.getOpsType())) {

            if (request.getParameter("ps") != null) {
                pageSearch.setPs(Integer.valueOf(request.getParameter("ps")));
            }
            if (request.getParameter("pi") != null) {
                Integer pi = Integer.valueOf(request.getParameter("pi")) - 1;
                pageSearch.setPi(pi < 0 ? 0 : pi);
            }
        }

        HashMap<String, Object> pageParameter = new HashMap<>();
        HashMap<String, Object> exportParameter = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            // 封装分页参数
            if (StringUtils.isNotEmpty(parameterName) && parameterName.startsWith("page_")) {
                pageParameter.put(parameterName.substring(5), request.getParameter(parameterName));
            }

            // 封装导出参数
            if (StringUtils.isNotEmpty(parameterName) && parameterName.startsWith("expo_")) {
                exportParameter.put(parameterName.substring(5), request.getParameter(parameterName));
            }
        }
        pageSearch.setPageParameter(pageParameter);
        pageSearch.setExportParameter(exportParameter);
        PageHolder.setHolder(pageSearch);
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
