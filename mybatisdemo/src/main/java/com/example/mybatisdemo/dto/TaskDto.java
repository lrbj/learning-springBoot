package com.example.mybatisdemo.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:18 PM 5/16/2019
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
    // 中断类型
    private Integer interruptType;
    // 审批状态
    private Integer statusId;
    // 审批人意见
    private String comment;
    // 开始时间
    private Timestamp startTime;
    // 结束时间
    private Timestamp endTime;
    // 附件列表
    private List<String> attachments;
}
