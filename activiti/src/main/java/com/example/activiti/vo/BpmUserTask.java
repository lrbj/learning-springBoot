package com.example.activiti.vo;

import lombok.Data;

import java.util.Map;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:12 PM 7/19/2019
 */
@Data
public class BpmUserTask {
    private String id;
    private String name;
    //private String description;
    //private Integer priority;
    private String assignee;
    private String tenantId;
    private String formKey;
    private Boolean isSuspended;
    private String processDefinitionId;
    private Long createTime;
    private Map<String, Object> context;
    private Map<String, Object> processContext;

}
