package com.example.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProcessInstanceListener implements ExecutionListener {
    private static Logger logger = LoggerFactory.getLogger(ProcessInstanceListener.class);

    @Override
    public void notify(DelegateExecution delegateExecution) {

        logger.info("call ProcessInstanceListener");
        String processInstanceId = delegateExecution.getProcessInstanceId();
        String eventName = delegateExecution.getEventName();
        Set<String> variableNames = delegateExecution.getVariableNames();
        Map<String, Object> variables = delegateExecution.getVariables();
        String processDefinitionId = delegateExecution.getProcessDefinitionId();
        System.out.println("自定义的监听器执行了，监听器到事件：" + eventName);
        //初始化(重置)rejected
        delegateExecution.setVariable("rejected", 0);

        Map<String, TaskComment> taskComments = new HashMap<String, TaskComment>();
        System.out.println("taskComments：" + taskComments);
        delegateExecution.setVariable("taskComments", taskComments);


    }
}
