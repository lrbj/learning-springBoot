package com.example.mybatisdemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProcessVo {
    // 申请编码
    private String applicationId;
    // +流程实例Id
    private String processInstanceId;
    // 消审申请类型
    private Integer auditType;
    // 创建时间
    private Long createTime;
    // 最后更新时间
    private Long lastUpdateTime;
    // 流程状态id
    private Integer operatingStatusId;
    // 审批状态id
    private Integer auditStatusId;
    // 最后提交时间
    private Long lastCommitTime;
    // 最后驳回时间
    private Long lastRejectTime;
    // 最后退件时间
    private Long lastReturnApplicationTime;
    // 最后批准时间
    private Long lastApproveTime;
    // 最后审批时间
    private Long lastAuditTime;
    // 任务列表
    private List<TaskDto> tasks;
}
