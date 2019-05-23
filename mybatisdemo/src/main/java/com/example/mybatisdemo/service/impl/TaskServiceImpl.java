package com.example.mybatisdemo.service.impl;

import com.example.mybatisdemo.dto.TaskDto;
import com.example.mybatisdemo.mapper.TaskMapper;
import com.example.mybatisdemo.model.Task;
import com.example.mybatisdemo.service.TaskService;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:23 PM 5/16/2019
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    TaskMapper taskMapper;

    @Override
    public Task selectByTaskId(String taskId) {
        Preconditions.checkNotNull(taskId, "taskId must not be null.");
        return taskMapper.selectByTaskId(taskId);
    }

    @Override
    public int insert(Task task) {
        Preconditions.checkNotNull(task, "insert task must not be null.");
        return taskMapper.insert(task);
    }

    @Override
    public int update(Task task) {
        Preconditions.checkNotNull(task, "insert task must not be null.");
        return taskMapper.update(task);
    }

    @Override
    public List<TaskDto> queryTasksByStatusIdAndAssigneeId(String assigneeId, Integer statusId) {
        Preconditions.checkNotNull(assigneeId, "assigneeId must not be null.");
        Preconditions.checkNotNull(statusId, "statusId must not be null.");
        return taskMapper.queryTasksByStatusIdAndAssigneeId(assigneeId, statusId);
    }

    @Override
    public List<TaskDto> queryTasksByProcessInstanceId(String processInstanceId) {
        Preconditions.checkNotNull(processInstanceId, "processInstanceId must not be null.");
        return taskMapper.queryTasksByProcessInstanceId(processInstanceId);
    }
}
