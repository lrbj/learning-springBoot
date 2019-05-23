package com.example.mybatisdemo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 5:38 PM 5/19/2019
 */
@Data
public class AuditRequestResult {
    // 分页大小
    private Integer pageSize;
    // 页码, 0为第一页
    private Integer pageNo;
    // 符合条件的记录总数
    private Integer totalCount;
    // 消审请求列表
    private List<AuditRequestVo> auditRequests;
}
