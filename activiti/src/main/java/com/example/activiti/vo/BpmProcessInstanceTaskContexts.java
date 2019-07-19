package com.example.activiti.vo;

import java.util.Map;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:04 PM 7/19/2019
 */
public class BpmProcessInstanceTaskContexts {
    private String taskId;
    private Map<String, Object> bpmContexts;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId.trim();
    }

    public Map<String, Object> getBpmContexts() {
        return bpmContexts;
    }

    public void setBpmContexts(Map<String, Object> bpmContexts) {
        this.bpmContexts = bpmContexts;
    }

    @Override
    public String toString() {
        return "BpmProcessInstanceTaskContexts{" +
                "taskId='" + taskId + '\'' +
                ", bpmContexts=" + bpmContexts +
                '}';
    }
}
