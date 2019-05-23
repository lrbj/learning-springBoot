package com.example.mybatisdemo.dto;

import lombok.Data;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:30 PM 5/22/2019
 */
@Data
public class AuditTotalData {

    private Integer total; //总数

    private Integer unapplied; //待申请数

    private Integer unapproved; //驳回数

    private Integer approved;//通过

    private Integer returned;//退件

    private Integer approving;//待审核
}
