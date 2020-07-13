package com.example.common.schedule;

import com.example.common.mq.notify.NotiftyTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@EnableScheduling
//@EnableAsync
public class TaskSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedule.class);


    @Autowired
    NotiftyTrigger notiftyTrigger;

   // @Scheduled(cron = "0 0/1 * * * ? ")
   @Scheduled(cron = "0 0 15 * * ? ")  //每天下午三 发布异常提醒前一天处理过的任务
    public void ExceptionNoctice() throws IOException, InterruptedException {
       LOGGER.info("do schedule time");
    }
}
