package com.example.common.account;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("user.api")
@Data
@Component
public class UserApiProperties {

  private String getDetail;
  private String getProfile;
}
