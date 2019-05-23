package com.example.mybatisdemo.dto;

import lombok.Data;


@Data
public class AuditGroupData {

    private String groupName;//事业群名

    private Integer total; //总数

    private Integer unapplied; //待申请数

    private Integer unapproved; //驳回数

    private Integer approved;//通过

    private Integer returned;//退件

    private Integer approving;//待审核

}
