package com.example.common.mq.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageProducer {
    private static Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private RabbitTemplate template;

    public void   sendMessage(String exchange, String routeKey,byte[] data) {
        logger.info("call sendMessage");
        template.send(exchange, routeKey,new Message(data, new MessageProperties()));
    }

}
