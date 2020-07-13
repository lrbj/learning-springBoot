package com.example.common.mq;

import com.alibaba.fastjson.JSON;
import com.example.common.integrationService.rabbitmq.RabbitmqProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventListener {

  public static final String EVENT_FAULT_CODE = "80";   // 故障

  @Autowired
  RabbitmqProps rabbitmqProps;

  @RabbitListener(queues = "event-data")
  public void EventListener(Message message) {
     System.out.println("listener");
  }
}
