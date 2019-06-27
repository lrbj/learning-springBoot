package com.example.activiti.listener;

import java.io.Serializable;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:24 PM 5/15/2019
 */
public class TaskComment implements Serializable {
    private String assignee;
    private String result;
    private String comment;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "TaskComment{" +
                "assignee='" + assignee + '\'' +
                ", result='" + result + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
