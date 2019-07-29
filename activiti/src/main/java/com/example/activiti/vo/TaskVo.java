package com.example.activiti.vo;

import com.example.activiti.listener.TaskComment;
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
    public List<String> approvers2;
    public List<String> approvers3;
    public List<String> approvers4;
    public List<String> approvers5;
    public List<String> approvers6;
    public List<String> approvers7;
    public List<String> approvers8;
    public List<String> approvers9;

    public String comment; //审批意见. 如果不同意，审批意见必填.
    public Integer result; //审批结果：0-退件; 1-回退; 2-同意. 必填

    public TaskComment taskComments;
}
