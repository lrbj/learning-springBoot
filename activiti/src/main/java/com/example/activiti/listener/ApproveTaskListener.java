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
@Component(value="approveListener")
public class ApproveTaskListener implements TaskListener,ExecutionListener {
    private static Logger logger = LoggerFactory.getLogger(ApproveTaskListener.class);

//    @Resource
//    private BpmNotifyTrigger bpmNotifyTrigger;

    @Resource
    private RuntimeService runtimeService;

    @Override
    public void notify(DelegateTask delegateTask) {
        //1. 获取审批结果：0-拒绝：直接删除运行时实例; 1-回退; 2-同意
        Integer result = (Integer) delegateTask.getVariable("result");
        //2. 记录审批人 审批结果 及comment -->保存到历史中 用于审批记录查询
      //  updateTaskComments(delegateTask);
        //3.根据审批结果做处理
        if(ApproveResultEnum.REFUSE.getId().equals(result)){ //退件-->直接结束流程：只删除运行中的实例，历史实例保存记录
            this.runtimeService.deleteProcessInstance( delegateTask.getProcessInstanceId(), (String)delegateTask.getVariable("comment"));

        }else if(ApproveResultEnum.RETURN.getId().equals(result)){ //回退
            int rejectedCnt = (int)delegateTask.getVariable("rejected");
            delegateTask.setVariable("rejected", ++rejectedCnt);
        }
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

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        String eventName = delegateExecution.getEventName();
        Set<String> variableNames = delegateExecution.getVariableNames();
        Map<String, Object> variables = delegateExecution.getVariables();
        String processDefinitionId = delegateExecution.getProcessDefinitionId();
        System.out.println("自定义的监听器执行了，监听器到事件：" + eventName);
        //初始化(重置)rejected
        delegateExecution.setVariable("rejected",0);

        Map<String, TaskComment> taskComments = new HashMap<String, TaskComment>();
        System.out.println("taskComments：" + taskComments);
        delegateExecution.setVariable("taskComments", taskComments);


    }

    private void updateTaskComments(DelegateTask delegateTask){
        Map<String, TaskComment> taskComments = (Map<String, TaskComment>) delegateTask.getVariable("taskComments");
        Integer result = (Integer) delegateTask.getVariable("result");

        //添加每个task的审批历史
        TaskComment taskComment = new TaskComment();
        taskComment.setAssignee(delegateTask.getAssignee());
        taskComment.setResult(ApproveResultEnum.idOf(result).getCode());
        taskComment.setComment((String)delegateTask.getVariable("comment"));
        //add it to map
        taskComments.put( delegateTask.getId(), taskComment);
        //update taskComments
        delegateTask.setVariable("taskComments", taskComments);
    }
}
