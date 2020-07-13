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
    public Queue queueFault() {
        return new Queue(rabbitmqProps.getFaultQueue());
    }

    @Bean
    public FanoutExchange exchangeFire() {
        FanoutExchange fanoutExchange = new FanoutExchange(rabbitmqProps.getFireExchange());
        return new FanoutExchange(rabbitmqProps.getFireExchange());
    }

    @Bean
    Binding bindingFaultQueue(@Qualifier("queueFault") Queue queueFault, @Qualifier("exchangeFire") FanoutExchange exchange) {
        return BindingBuilder.bind(queueFault).to(exchange);
    }

    @Bean
    public Queue queueLocation() {
        return new Queue(rabbitmqProps.getLocationQueue());
    }

    @Bean
    public FanoutExchange exchangeLocation() {
        return new FanoutExchange(rabbitmqProps.getLocationExchange());
    }


    @Bean
    public Queue queueTest() {
        return new Queue("test");
    }

    @Bean
    public FanoutExchange exchangeTest() {
        FanoutExchange fanoutExchange = new FanoutExchange("test");
        return new FanoutExchange("testExchange");
    }
    @Bean
    Binding bindingTestQueue(@Qualifier("queueTest") Queue queueFault, @Qualifier("exchangeTest") FanoutExchange exchange) {
        return BindingBuilder.bind(queueFault).to(exchange);
    }
}
