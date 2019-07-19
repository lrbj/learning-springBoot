package com.example.activiti.vo;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:14 PM 7/19/2019
 */
public class UserTasksWithInterruptType {
    private String interruptType;

    private List<BpmUserTask> userTasks;

    public UserTasksWithInterruptType() {
    }

    public UserTasksWithInterruptType(String interruptType, List<BpmUserTask> userTasks) {
        this.interruptType = interruptType;
        this.userTasks = userTasks;
    }

    public String getInterruptType() {
        return interruptType;
    }

    public void setInterruptType(String interruptType) {
        this.interruptType = interruptType;
    }

    public List<BpmUserTask> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(List<BpmUserTask> userTasks) {
        this.userTasks = userTasks;
    }

    @Override
    public String toString() {
        return "UserTasksWithInterruptType{" +
                "interruptType='" + interruptType + '\'' +
                ", userTasks=" + userTasks +
                '}';
    }
}
