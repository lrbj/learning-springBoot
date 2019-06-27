package com.example.activiti.service;

import com.example.activiti.vo.TaskVo;

import java.io.InputStream;
import java.util.Map;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:18 PM 11/19/2018
 */
public interface Workservice {
    //1、部署流程
    void deploy();

    //2、运行流程
    void runProcess();

    //3、查询代理人的任务情况
    void queryTask(String assignee);

    //4、完成任务
    void complieTask(String taskId, TaskVo data);

    //5、查看流程的具体定义
    void queryProcessDefination(String processDefikey);

    //6、查看流程实例的状态
    void queryProcessInstanceState();

    //7、删除流程定义
    void deleteProcessDefi(String deploymentId);

    //8、查看历史流程实例信息
    void queryHistoryProcInst();

    //9、查询流程实例历史执行信息
    void queryHistoryTask(String processInstanceId);

    void deployProcessDefinition(InputStream inputStream, String trim, String trim1);
}
