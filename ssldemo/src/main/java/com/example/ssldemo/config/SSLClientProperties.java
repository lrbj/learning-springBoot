package com.example.ssldemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:01 PM 8/28/2020
 */
@ConfigurationProperties("client.ssl")
@Data
@Component
public class SSLClientProperties {
    private String keystore;
    private String keystoreType;
    private String keystorePassword;
}
