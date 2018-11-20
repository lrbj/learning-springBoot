package com.example.activiti.service;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:18 PM 11/19/2018
 */
public interface Workservice {
    //部署流程
    void deploy();
    //运行流程
    void runProcess();
    //查询代理人的任务情况
    void queryTask(String assignee);
    //完成任务
    void complieTask(String taskId);
    //查看流程的具体定义
    void queryProcessDefination(String processDefikey);

    void queryProcessInstanceState();
}
