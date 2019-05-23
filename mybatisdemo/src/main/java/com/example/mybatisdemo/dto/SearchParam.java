
package com.example.mybatisdemo.dto;

import lombok.Data;

/**
 * @description: 窗口查询参数
 **/
@Data
public class SearchParam {
    //  消审申请编号
    private String applicationId;
    // 申请时间区间, 开始时间点, 闭区间
    private Long applyStartTime;
    // 申请时间区间, 结束时间点, 开区间
    private Long applyEndTime;
    // 申请状态id(-// 申请状态id, 1 - 消审审核中, 2 - 消审已批准, 3 - 消审驳回, 4 - 消审退件,  5 - 验收审核中, 6 - 验收已批准, 7 - 验收驳回)
    private Integer statusId;
    // 是否已提交
    private Boolean applied;
    // 申请人用户id
    private String applyUserId;

    // 申请单位-园区id
    private String unitParkId;
    // 申请单位-事业群id
    private String unitBusinessGroupId;
    // 申请单位-事业处id
    private String unitBusinessDepartmentId;


}
