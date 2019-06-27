package com.example.activiti.service.impl;

import com.example.activiti.controller.testController;
import com.example.activiti.service.Workservice;
import com.example.activiti.vo.TaskVo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:20 PM 11/19/2018
 */
@Service
public class WorkserviceImpl implements Workservice {

    private static Logger logger = LoggerFactory.getLogger(testController.class);

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Override
    public void deploy() {

        //部署流程
        Deployment deployment = repositoryService.createDeployment() //创建一个部署的构造器
                .addClasspathResource("processes/ResumeApplyProcess.bpmn") //从类路径中添加资源
                .name("工单流程11") //设置部署的名称
                .category("办公类别11") //设置部署的类别
                .deploy();
        System.out.println("部署ID:"+deployment.getId());
        System.out.println("部署名称"+deployment.getName());
    }

    @Override
    public void runProcess() {
        String processDefiKey = "ResumeApplyProcess";

        //获取流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefiKey);
        System.out.println("流程执行对象的id:"+processInstance.getId());
        System.out.println("流程实例id:"+processInstance.getProcessInstanceId());//流程实例id
        System.out.println("流程定义id:"+processInstance.getProcessDefinitionId());//输出流程定义的id
    }

    @Override
    public void queryTask(String assignee) {
        //创建任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        //查看办理人的任务列表
        List<Task> taskList = taskQuery.taskAssignee(assignee).list();
        if(!taskList.isEmpty()){
             for(Task task:taskList){
                 System.out.println("任务办理人："+task.getAssignee());
                 System.out.println("任务id:" + task.getId());
                 System.out.println("任务名称："+ task.getName());
             }
        }
    }

    @Override
    public void complieTask(String taskId,TaskVo data) {

        Map<String,Object> var = new HashMap<>();
        var.put("approvers1",data.getApprovers1());
        taskService.complete(taskId,var);
        System.out.println("当前任务已执行完");
    }

    @Override
    public void queryProcessDefination(String processDefikey) {

        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefikey) //流程定义的key
                .latestVersion()
                .orderByProcessDefinitionVersion().desc() //按照版本降序排序
                .list();
        if(!processDefinitionList.isEmpty()){
            for(ProcessDefinition processDefinition: processDefinitionList){
                System.out.println("流程定义的id:"+ processDefinition.getId());
                System.out.println("流程定义的Key:"+ processDefinition.getKey());
                System.out.println("流程定义的版本："+ processDefinition.getVersion());
                System.out.println("流程定义部署的id:"+ processDefinition.getDeploymentId());
                System.out.println("流程定义的名称:"+processDefinition.getName());
            }
        }

    }

    @Override
    public void queryProcessInstanceState() {
        //获取所有流程实例
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery()
                .list();
        if(!processInstanceList.isEmpty()){
            for(ProcessInstance processInstance: processInstanceList){
                System.out.println("当前正在运行的流程实例");
                System.out.println("该实例id："+processInstance.getProcessInstanceId());
                System.out.println("该实例名字："+processInstance.getName());
                System.out.println("该实例对象id:"+processInstance.getId());
            }
        }
        else {
            System.out.println("当前无流程实例");
        }
    }

    @Override
    public void deleteProcessDefi(String deploymentId) {
       repositoryService.deleteDeployment(deploymentId);
    }

    @Override
    public void queryHistoryProcInst() {
        List<HistoricProcessInstance> list = historyService
                .createHistoricProcessInstanceQuery()
                .list();
        if(!list.isEmpty()){
            for(HistoricProcessInstance temp:list){
                System.out.println("历史流程实例id:" + temp.getId());
                System.out.println("历史流程定义的id:"+ temp.getProcessDefinitionId());
                System.out.println("历史流程实例开始时间--结束时间:"+ temp.getStartTime() + "-->"+ temp.getEndTime());
            }
        }

    }

    @Override
    public void queryHistoryTask(String processInstanceId) {
        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if(!taskInstanceList.isEmpty()){
            for(HistoricTaskInstance historicTaskInstance:taskInstanceList){
                System.out.println("历史流程实例任务id:"+ historicTaskInstance.getId());
                System.out.println("历史流程定义的id:"+ historicTaskInstance.getProcessDefinitionId());
                System.out.println("历史流程实例任务名称：" +historicTaskInstance.getName());
                System.out.println("历史流程实例任务处理人："+historicTaskInstance.getAssignee());
            }
        }
    }

    @Override
    public void deployProcessDefinition(InputStream zipBpmFileInputStream, String deploymentName, String tenantId) {
        try {
            logger.debug("deployProcessDefine, deploymentName: {}, tenantId: {}.", deploymentName, tenantId);
            // 发布新版本
            Deployment deployment = repositoryService.createDeployment()
                    .addZipInputStream(new ZipInputStream(zipBpmFileInputStream)) //
                    .name(deploymentName)
                    .tenantId(tenantId)
                    .deploy();
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId());
            System.out.println(processDefinitionQuery.list().size());
        } catch (Exception e) {
            logger.error("deployProcessDefine failed.", e);
        }
    }


}
