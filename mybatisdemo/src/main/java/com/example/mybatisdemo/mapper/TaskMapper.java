
package com.example.mybatisdemo.mapper;

import com.example.mybatisdemo.dto.TaskDto;
import com.example.mybatisdemo.model.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {

    /**
     * 根据任务id，查询任务数据
     *
     * @param taskId 任务id
     * @return 任务数据
     */
    Task selectByTaskId(@Param("taskId") String taskId);

    /**
     * 将任务数据，插入数据库
     *
     * @param task 任务数据
     * @return 插入成功条数
     */
    int insert(Task task);

    /**
     * 更新任务数据
     *
     * @param task 任务数据
     * @return 更新成功条数
     */
    int update(Task task);

    /**
     * 根据审批人id及审批状态id，查询相应用户任务列表
     *
     * @param assigneeId 审批人id
     * @param statusId   审批状态id
     * @return 用户任务列表
     */
    List<TaskDto> queryTasksByStatusIdAndAssigneeId(@Param("assigneeId") String assigneeId,
                                                    @Param("statusId") Integer statusId);

    /**
     * 根据流程id，查询相应的任务列表
     *
     * @param processInstanceId 流程id
     * @return 任务列表
     */
    List<TaskDto> queryTasksByProcessInstanceId(@Param("processInstanceId") String processInstanceId);
}