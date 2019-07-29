package com.example.mybatisdemo.controller;

import com.example.mybatisdemo.dto.TaskDto;
import com.example.mybatisdemo.model.Task;
import com.example.mybatisdemo.service.TaskService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:12 PM 5/16/2019
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/add")
    @ApiOperation(value = "addTask", notes = "添加数据")
    public void addTask(@RequestBody Task task) {
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);

        task.setStartTime(timestamp);
        task.setEndTime(timestamp);
        taskService.insert(task);
    }

    @GetMapping("")
    @ApiOperation(value = "find", notes = "查找全部数据")
    public List<TaskDto> find(@RequestParam("processInstanceId") String processInstanceId) {

        return taskService.queryTasksByProcessInstanceId(processInstanceId);
    }
}
