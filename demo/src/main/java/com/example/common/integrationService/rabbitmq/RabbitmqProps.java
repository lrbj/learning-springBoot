package com.example.common.integrationService.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "rabbitmq")
@Component
public class RabbitmqProps {
    private String fireExchange;
    private String faultQueue;
    private String locationQueue;
    private String LocationExchange;
    private String mailExchange;
    private String mailRouteKey;
}
