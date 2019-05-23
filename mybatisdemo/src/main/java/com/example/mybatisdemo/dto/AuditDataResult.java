package com.example.mybatisdemo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:37 PM 5/22/2019
 */
@Data
public class AuditDataResult {

    private AuditTotalData inTotal;

    private List<AuditGroupData> byGroups;
}
