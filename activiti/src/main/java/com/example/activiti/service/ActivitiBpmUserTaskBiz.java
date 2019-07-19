package com.example.activiti.service;
;
import com.example.activiti.vo.BpmUserTask;
import com.example.activiti.vo.UserTasksWithInterruptType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.NonNull;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:07 PM 7/19/2019
 */
@Service
public class ActivitiBpmUserTaskBiz {
    private static Logger logger = LoggerFactory.getLogger(ActivitiBpmUserTaskBiz.class);

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private FormService formService;

    /**
     * 查询用户任务列表
     *
     * @param userId   用户id
     * @param tenantId 租户id
     * @return 用户任务列表
     */

    public List<BpmUserTask> queryUserTask(String userId, String tenantId) throws Exception {
        try {
            logger.info("queryUserTask, userId: {}, tenantId: {}.", userId, tenantId);
            List<Task> tasks = taskService.createTaskQuery().taskTenantId(tenantId).taskAssignee(userId).orderByTaskCreateTime().desc().includeTaskLocalVariables().includeProcessVariables().list();
            if (Objects.nonNull(tasks)) {
                return tasks.stream().map(this::convertToBpmUserTask).collect(Collectors.toList());
            } else {
                return Lists.newArrayList();
            }
        } catch (Exception e) {
            logger.error("queryUserTask, userId: {}.", userId, e);
            throw new Exception();
        }
    }

    /**
     * 完成用户任务
     *
     * @param taskId   任务id
     * @param contexts 任务相关变量
     */

    @Transactional
    public void completeUserTask(String taskId, Map<String, Object> contexts) throws Exception {
        try {
            logger.debug("completeUserTask, taskId: {}, contexts: {}.", taskId, contexts);
            Map<String, Object> variables;
            if (contexts.isEmpty()) {
                variables = Maps.newHashMap();
            } else {
                variables = contexts.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> (Object) entry.getValue()));
            }
            taskService.complete(taskId, variables);
        } catch (Exception e) {
            logger.error("completeUserTask failed, taskId: {}, contexts: {}.", taskId, contexts, e);
            throw new Exception();
        }
    }


    @Transactional
    public void saveDraftOfUserTask(String taskId, Map<String, String> contexts) throws Exception {
        try {
            logger.debug("saveDraftOfUserTask, taskId: {}, contexts: {}.", taskId, contexts);
            formService.saveFormData(taskId, contexts.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> (String) entry.getValue())));
        } catch (Exception e) {
            logger.error("saveDraftOfUserTask failed, taskId: {}, contexts: {}.", taskId, contexts, e);
            throw new Exception();
        }
    }


    public Object getTaskVariables(String taskId) throws Exception {
        try {
            logger.debug("getTaskFormData, taskId: {}.", taskId);
            Map<String, Object> vaviables = taskService.getVariables(taskId);
            return vaviables;
        } catch (Exception e) {
            logger.error("getTaskFormData failed, taskId: {}.", taskId, e);
            throw new Exception();
        }
    }


    public List<BpmUserTask> queryUserTaskOfProcessInstance(String processInstanceId) throws Exception {
        try {
            logger.debug("queryUserTaskOfProcessInstance, processInstanceId: {}.", processInstanceId);
            List<Task> tasks = taskService.createTaskQuery().includeProcessVariables().includeTaskLocalVariables().processInstanceId(processInstanceId).list();
            return tasks.stream().map(this::convertToBpmUserTask).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("queryUserTaskOfProcessInstance failed, processInstanceId: {}.", processInstanceId, e);
            throw new Exception();
        }
    }




    private BpmUserTask convertToBpmUserTask(Task task) {
        BpmUserTask bpmUserTask = new BpmUserTask();
        bpmUserTask.setAssignee(task.getAssignee());
        bpmUserTask.setContext(task.getTaskLocalVariables().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> (String) entry.getValue())));
        bpmUserTask.setCreateTime(task.getCreateTime().getTime());
        //  bpmUserTask.setDescription(task.getDescription());
        bpmUserTask.setFormKey(task.getFormKey());
        bpmUserTask.setId(task.getId());
        bpmUserTask.setTenantId(task.getTenantId());
        logger.info("task id: {}.", task.getId());
        bpmUserTask.setName(task.getName());
        // bpmUserTask.setPriority(task.getPriority());
        logger.info("task variables: {}.", task.getProcessVariables());
        bpmUserTask.setProcessContext(task.getProcessVariables().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue())));
        bpmUserTask.setProcessDefinitionId(task.getProcessDefinitionId());
        bpmUserTask.setIsSuspended(task.isSuspended());
        return bpmUserTask;
    }


}
