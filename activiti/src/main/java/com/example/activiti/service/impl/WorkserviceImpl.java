package com.example.activiti.service.impl;

import com.example.activiti.service.Workservice;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:20 PM 11/19/2018
 */
@Service
public class WorkserviceImpl implements Workservice {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;
    @Override
    public void deploy() {

        //部署流程
        Deployment deployment = repositoryService.createDeployment() //创建一个部署的构造器
                .addClasspathResource("processes/first.bpmn") //从类路径中添加资源
                .name("工单流程") //设置部署的名称
                .category("办公类别") //设置部署的类别
                .deploy();
        System.out.println("部署ID:"+deployment.getId());
        System.out.println("部署名称"+deployment.getName());
    }

    @Override
    public void runProcess() {
        String processDefiKey = "myProcess_1";

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
    public void complieTask(String taskId) {
        taskService.complete(taskId);
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
                System.out.println("该实例id："+processInstance.getProcessInstanceId());
                System.out.println("该实例名字："+processInstance.getName());
                System.out.println("该实例对象id:"+processInstance.getId());
            }
        }
        else {
            System.out.println("当前无流程实例");
        }
    }
}
