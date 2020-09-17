/*
 * Copyright (C) 2019 example, Inc. All Rights Reserved.
 */
package com.example.mybatisdemo.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @program: foxconn-interruptrequest
 * @description: 任务列表实体
 * @author: Daniel.Zhang
 * @create: 2019-03-25 09:35
 */
@Data
public class Task {
    /**
     * 记录id
     */
    private Integer id;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 执行人id
     */
    private String assigneeId;

    /**
     * 开始时间
     */
    private Timestamp startTime;

    /**
     * 结束时间
     */
    private Timestamp endTime;

    /**
     * 审批状态
     */
    private Integer auditStatus;

    /**
     * 审批人意见
     */
    private String comment;


}