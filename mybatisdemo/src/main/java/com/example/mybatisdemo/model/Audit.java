package com.example.mybatisdemo.model;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Audit {

    /**
     * id号
     */
    private Integer id;

    /**
     * 申请编号
     */
    private String  applicationId;


    /**
     * 申请人id
     */
    private String applyUserId;

    /**
     *  创建时间
     */
    private Timestamp createTime;

    /**
     * 最后更新时间
     */
    private Timestamp lastUpdateTime;

    /**
     * 最后活动的流程( 消审)类型
     */
    private Integer lastActivityType;

    /**
     * 最后活动的流程( 消审)状态
     */
    private Integer lastActivityStatus;

    /**
     * 最后活动的申请的审批意见
     */
    private String lastActivityComment;

    /**
     * 是否已提交
     */
    private Boolean applied;

    /**
     * 是否可验收
     */
    private Boolean acceptanceEnabled;

    /**
     * 申请表单
     */
    private String applyForm;


    /**
     * 申请单位(园区)
     */
    private String unitPark;

    /**
     * 申请单位(事业群)
     */
    private String unitBusinessGroup;


    /**
     * 申请事业单位群名
     */
    private String unitBusinessGroupName;
    /**
     * 申请单位(事业处)
     */
    private String unitBusinessDepartment;

    public Audit(String applicationId, String applyUserId, Timestamp createTime, Timestamp lastUpdateTime,
                 Integer lastActivityType, Integer lastActivityStatus, String lastActivityComment, Boolean applied,
                 Boolean acceptanceEnabled, String applyForm, String unitPark, String unitBusinessGroup, String unitBusinessGroupName,
                 String unitBusinessDepartment, Timestamp planStartTime, Timestamp planEndTime) {
        this.applicationId = applicationId;
        this.applyUserId = applyUserId;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.lastActivityType = lastActivityType;
        this.lastActivityStatus = lastActivityStatus;
        this.lastActivityComment = lastActivityComment;
        this.applied = applied;
        this.acceptanceEnabled = acceptanceEnabled;
        this.applyForm = applyForm;
        this.unitPark = unitPark;
        this.unitBusinessGroup = unitBusinessGroup;
        this.unitBusinessGroupName = unitBusinessGroupName;
        this.unitBusinessDepartment = unitBusinessDepartment;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
    }

    public Audit() {
    }

    /**

     *  计划开工时间
     */
    private  Timestamp planStartTime;


    /**
     * 计划竣工时间
     */
    private  Timestamp planEndTime;





}
