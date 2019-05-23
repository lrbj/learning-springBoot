package com.example.mybatisdemo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 9:25 PM 5/19/2019
 */
@Data
public class AuditRequestProcessDetail extends AuditRequestDto {
    // 子流程列表
    private List<ProcessVo> processes;
}
