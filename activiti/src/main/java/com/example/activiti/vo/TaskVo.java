package com.example.activiti.vo;

import lombok.Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 10:32 AM 6/27/2019
 */
@Data
public class TaskVo {
  public List<String> approvers1;

  public String   comment; //审批意见. 如果不同意，审批意见必填.
  public Integer  result ; //审批结果：0-退件; 1-回退; 2-同意. 必填
}
