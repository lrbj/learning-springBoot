package com.example.activiti.controller;

import com.example.activiti.service.Workservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:43 PM 11/19/2018
 */
@RequestMapping("/api/activiti/test")
@RestController
@Api(tags = "activiti")
public class testController {

    private static Logger logger = LoggerFactory.getLogger(testController.class);
    @Autowired
    Workservice workservice;


    @PostMapping(value = "/deployments", consumes = "multipart/form-data", produces = "application/json;charset=utf-8")
    public void deployProcessDefinition(@RequestParam("file") MultipartFile zipBpmFile,
                                        @RequestParam("deploymentName") String deploymentName,
                                        @RequestParam("tenantId") String tenantId, HttpServletResponse httpServletResponse){
        try {
            logger.debug("deployProcessDefinition, deploymentName: {}, tenantId: {}.", deploymentName, tenantId);
            workservice.deployProcessDefinition(zipBpmFile.getInputStream(), deploymentName.trim(), tenantId.trim());
        } catch (Exception e) {
            logger.error("deployProcessDefinition failed.", e);
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
    @GetMapping("/deploy")
    @ApiOperation(value = "流程部署")
    void deploy() {
        workservice.deploy();
    }
    @PostMapping("/run")
    @ApiOperation(value = "启动流程")
    void runProcess() {
        workservice.runProcess();
    }

    @GetMapping("/query")
    @ApiOperation(value = "查询任务")
    void queryTask(@RequestParam("assginName")String assginName) {
        workservice.queryTask(assginName);
    }

    @PostMapping("/complete")
    @ApiOperation(value = "执行任务")
    void completeTask(@RequestParam("taskId")String taskId) {
        workservice.complieTask(taskId);
    }

    @GetMapping("/queryProcess")
    @ApiOperation(value = "查询流程定义")
    void queryProcessDefination(@RequestParam("processDefikey")String  processDefikey){
        workservice.queryProcessDefination(processDefikey);
        workservice.queryProcessInstanceState();

    }

    @DeleteMapping("/processDefi")
    @ApiOperation(value = "删除流程")
    void deleteProcessDefi(@RequestParam("deploymentId")String deploymentId){
        workservice.deleteProcessDefi(deploymentId);
    }

}
