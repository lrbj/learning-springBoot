package com.example.mybatisdemo.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description:前端查询相关的工作内容
 */
@Data
public class TaskDto {
    // 申请编码
    private String applicationId;
    // 任务id
    private String taskId;
    // 任务名称
    private String name;
    // 执行人id
    private String assigneeUserId;

    // 消审类型
    private Integer auditType;

    // 审批状态
    private Integer statusId;
    // 审批人意见
    private String comment;
    // 开始时间
    private Timestamp startTime;
    // 结束时间
    private Timestamp endTime;

}
