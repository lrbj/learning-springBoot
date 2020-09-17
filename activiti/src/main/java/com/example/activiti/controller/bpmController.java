package com.example.activiti.controller;

import com.example.activiti.service.ActivitiBpmUserTaskBiz;
import com.example.activiti.vo.BpmProcessInstanceTaskContexts;
import com.example.activiti.vo.BpmTaskDraftContexts;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:43 PM 7/19/2019
 */
@RequestMapping("/api/bpm/test")
@RestController
@Api(tags = "bpm")
public class bpmController {

    private static Logger logger = LoggerFactory.getLogger(bpmController.class);
    @Resource
    private ActivitiBpmUserTaskBiz bpmUserTaskBiz;

    @PostMapping(value = "/tasks/drafts")
    public void saveDraftOfUserTask(@RequestBody BpmTaskDraftContexts context,
                                    HttpServletResponse httpServletResponse) {
        try {

            logger.debug("saveDraftOfUserTask, context: {}.", context);
            bpmUserTaskBiz.saveDraftOfUserTask(context.getTaskId(), context.getVariables());


        } catch (Exception e) {
            logger.error("saveDraftOfUserTask failed, context: {}.", context, e);
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @PostMapping(value = "/tasks")
    public void completeUserTask(@RequestBody BpmProcessInstanceTaskContexts context,
                                 HttpServletResponse httpServletResponse) {
        try {

            logger.debug("processTask, context: {}.", context);
            bpmUserTaskBiz.completeUserTask(context.getTaskId(), context.getBpmContexts());
        } catch (Exception e) {
            logger.error("processTask failed, context: {}.", context, e);
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
