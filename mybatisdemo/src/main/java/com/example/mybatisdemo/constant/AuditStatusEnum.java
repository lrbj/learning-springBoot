package com.example.mybatisdemo.constant;

public enum AuditStatusEnum {
    None(0, "异常状态"),
    Approved(1, "已批准"), // 已批准
    Deleted(2, "退件"), // 退件
    Applying(3, "待提交"), // 待提交
    Approving(4, "审核中"), // 审核中
    Rejected(5, "驳回"); // 驳回
    private int id;
    private String displayName;

    AuditStatusEnum(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

}
