package com.example.mybatisdemo.controller;

import com.example.mybatisdemo.service.ProcessService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import com.example.mybatisdemo.model.Process;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 10:25 AM 5/17/2019
 */
@RestController
@RequestMapping("/process")
public class ProcessController {
    @Autowired
    ProcessService processService;

    @PostMapping("/add")
    @ApiOperation(value="addProcess", notes="添加数据")
    public void  addProcess(@RequestBody Process process){
        Long time = System.currentTimeMillis();

        Timestamp   now = new Timestamp(time);
        process.setCreateTime(now);
        process.setLastApproveTime(now);
        process.setLastAuditTime(now);
        process.setLastRejectTime(now);
        process.setLastCommitTime(now);
        process.setLastUpdateTime(now);
        process.setLastReturnApplicationTime(now);

        processService.insert(process);
    }

    @GetMapping
    @ApiOperation(value="find", notes="查找全部数据")
    public Process find(@RequestParam("processInstanceId") String processInstanceId){

        return processService.selectByProcessInstanceId(processInstanceId);
    }
}