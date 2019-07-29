package com.example.mybatisdemo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuditRequestVo {
    // 记录id

    private Integer id;
    // 申请编码

    private String applicationId;
    // 申请人用户id

    private String applyUserId;

    // 申请创建时间

    private Timestamp createTime;
    // 申请最后更新时间
    //  private Timestamp lastUpdateTime;
    // 最后活动的申请的类型id

    private Integer lastActivityTypeId;
    // 最后活动的申请的审批状态id

    private Integer lastActivityStatusId;
    // 最后活动的申请的审批意见

    private String lastActivityComment;
    // 是否已提交
    //  private Boolean applied;
    // 是否可验收, true-可验收, false-不可验收

    private Boolean acceptanceEnabled;
}
