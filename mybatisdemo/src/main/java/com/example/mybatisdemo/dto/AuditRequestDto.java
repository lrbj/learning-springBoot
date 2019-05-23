package com.example.mybatisdemo.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 表单详情
 */
@Data
public class AuditRequestDto {
    // 记录id
    private Integer id;
    // 申请编码
    private String applicationId;
    // 申请人用户id
    private String applyUserId;
    // 申请表单
    private String applyForm;
    // 申请创建时间
    private Timestamp createTime;
    // 申请最后更新时间
    private Timestamp lastUpdateTime;
    // 最后活动的申请的类型id
    private Integer lastActivityTypeId;
    // 最后活动的申请的审批状态id
    private Integer lastActivityStatusId;
    // 最后活动的申请的审批意见
    private String lastActivityComment;
    // 是否已提交
    private Boolean applied;
    // 是否可验收, true-可验收, false-不可验收
    private Boolean acceptanceEnabled;

    /**
     * 申请单位(园区)
     */
    private String unitPark;
    /**
     * 申请单位(事业群)
     */
    private String unitBusinessGroup;

    /**
     * 申请单位(事业处)
     */
    private String unitBusinessDepartment;

}
