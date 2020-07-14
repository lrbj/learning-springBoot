package com.example.common.integrationService.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {

    @Autowired
    private RabbitmqProps rabbitmqProps;

    @Bean
    public Queue queueLocation() {
        return new Queue(rabbitmqProps.getLocationQueue());
    }

    @Bean
    public FanoutExchange exchangeLocation() {
        return new FanoutExchange(rabbitmqProps.getLocationExchange());
    }

}
