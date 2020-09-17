package com.example.activiti.listener;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:23 PM 5/15/2019
 */
@Component(value = "approveListener")
public class ApproveTaskListener implements TaskListener {
    private static Logger logger = LoggerFactory.getLogger(ApproveTaskListener.class);

//    @Resource
//    private BpmNotifyTrigger bpmNotifyTrigger;

    @Resource
    private RuntimeService runtimeService;

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.error("call ApproveTaskListener");
        //1. 获取审批结果：0-拒绝：直接删除运行时实例; 1-回退; 2-同意
        Integer result = (Integer) delegateTask.getVariable("result");
        //2. 记录审批人 审批结果 及comment -->保存到历史中 用于审批记录查询
//         updateTaskComments(delegateTask);
        //3.根据审批结果做处理
        if (ApproveResultEnum.REFUSE.getId().equals(result)) { //退件-->直接结束流程：只删除运行中的实例，历史实例保存记录
            //this.runtimeService.deleteProcessInstance( delegateTask.getProcessInstanceId(), (String)delegateTask.getVariable("comment"));
//            this.runtimeService.suspendProcessInstanceById(delegateTask.getProcessInstanceId());
//            logger.info(" notify(DelegateTask delegateTask): refuse");
//            int rejectedCnt = (int) delegateTask.getVariable("rejected");
//
//            delegateTask.setVariable("rejected", --rejectedCnt);
//            logger.info(" notify(DelegateTask delegateTask): rejectedCnt:{}", rejectedCnt);

        } else if (ApproveResultEnum.RETURN.getId().equals(result)) { //回退
            int rejectedCnt = (int) delegateTask.getVariable("rejected");
            delegateTask.setVariable("rejected", ++rejectedCnt);
            logger.info(" notify(DelegateTask delegateTask): rejectedCnt:{}", rejectedCnt);

        } else if (ApproveResultEnum.JUMP.getId().equals(result)) {
            //跳转
            int rejectedCnt = (int) delegateTask.getVariable("rejected");
            delegateTask.setVariable("rejected", --rejectedCnt);
        } else if (ApproveResultEnum.AGREE.getId().equals(result)) {
            //同意
            int rejectedCnt = (int) delegateTask.getVariable("rejected");
            // 如果上一次是跳转则置为0
            if (rejectedCnt < 0) {
                delegateTask.setVariable("rejected", 0);
            }
        }
        logger.info("notify agree: rejectedCnt:{}", (int) delegateTask.getVariable("rejected"));
        //  发送消息
//        try {
//            logger.debug("bpmNotifyTrigger,  taskId: {}.", delegateTask.getId());
//            bpmNotifyTrigger.taskChanged(new BpmTaskChangedNotify().setTaskId(delegateTask.getId()));
//        } catch (JsonProcessingException e) {
//            logger.error("bpmNotifyTrigger failed.", e);
//
//        } catch (GetNextUuidException e) {
//            logger.error("bpmNotifyTrigger failed.", e);
//        }


    }


    private void updateTaskComments(DelegateTask delegateTask) {
        Map<String, TaskComment> taskComments = (Map<String, TaskComment>) delegateTask.getVariable("taskComments");
        Integer result = (Integer) delegateTask.getVariable("result");

        logger.info("updateTaskComments: delegateTask:{}", delegateTask);
        logger.info("updateTaskComments: result:{}", result);
        //添加每个task的审批历史
        TaskComment taskComment = new TaskComment();
        taskComment.setAssignee(delegateTask.getAssignee());
        taskComment.setResult(ApproveResultEnum.idOf(result).getCode());
        taskComment.setComment((String) delegateTask.getVariable("comment"));
        //add it to map
        taskComments.put(delegateTask.getId(), taskComment);
        //update taskComments
        delegateTask.setVariable("taskComments", taskComments);
    }
}
