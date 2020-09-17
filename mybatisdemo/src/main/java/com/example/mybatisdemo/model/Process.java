/*
 * Copyright (C) 2019 example, Inc. All Rights Reserved.
 */
package com.example.mybatisdemo.model;

import java.sql.Timestamp;

public class Process {
    /**
     * 记录id
     */
    private Integer id;

    /**
     * 申请编号
     */
    private String applicationId;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 子流程(中断)类型
     */
    private Integer interruptType;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 最后更新时间
     */
    private Timestamp lastUpdateTime;

    /**
     * 流程状态
     */
    private Integer operatingStatus;

    /**
     * 审批状态
     */
    private Integer auditStatus;

    /**
     * 最后提交时间
     */
    private Timestamp lastCommitTime;

    /**
     * 最后驳回时间
     */
    private Timestamp lastRejectTime;

    /**
     * 最后退件时间
     */
    private Timestamp lastReturnApplicationTime;

    /**
     * 最后批准时间
     */
    private Timestamp lastApproveTime;

    /**
     * 最后审批时间
     */
    private Timestamp lastAuditTime;

    public Integer getId() {
        return id;
    }

    public Process setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Process setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public Process setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public Integer getInterruptType() {
        return interruptType;
    }

    public Process setInterruptType(Integer interruptType) {
        this.interruptType = interruptType;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Process setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Process setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public Integer getOperatingStatus() {
        return operatingStatus;
    }

    public Process setOperatingStatus(Integer operatingStatus) {
        this.operatingStatus = operatingStatus;
        return this;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public Process setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
        return this;
    }

    public Timestamp getLastCommitTime() {
        return lastCommitTime;
    }

    public Process setLastCommitTime(Timestamp lastCommitTime) {
        this.lastCommitTime = lastCommitTime;
        return this;
    }

    public Timestamp getLastRejectTime() {
        return lastRejectTime;
    }

    public Process setLastRejectTime(Timestamp lastRejectTime) {
        this.lastRejectTime = lastRejectTime;
        return this;
    }

    public Timestamp getLastReturnApplicationTime() {
        return lastReturnApplicationTime;
    }

    public Process setLastReturnApplicationTime(Timestamp lastReturnApplicationTime) {
        this.lastReturnApplicationTime = lastReturnApplicationTime;
        return this;
    }

    public Timestamp getLastApproveTime() {
        return lastApproveTime;
    }

    public Process setLastApproveTime(Timestamp lastApproveTime) {
        this.lastApproveTime = lastApproveTime;
        return this;
    }

    public Timestamp getLastAuditTime() {
        return lastAuditTime;
    }

    public Process setLastAuditTime(Timestamp lastAuditTime) {
        this.lastAuditTime = lastAuditTime;
        return this;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", applicationId='" + applicationId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", interruptType=" + interruptType +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", operatingStatus=" + operatingStatus +
                ", auditStatus=" + auditStatus +
                ", lastCommitTime=" + lastCommitTime +
                ", lastRejectTime=" + lastRejectTime +
                ", lastReturnApplicationTime=" + lastReturnApplicationTime +
                ", lastApproveTime=" + lastApproveTime +
                ", lastAuditTime=" + lastAuditTime +
                '}';
    }
}