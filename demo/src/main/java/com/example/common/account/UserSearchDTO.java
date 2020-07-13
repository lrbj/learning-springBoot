package com.example.common.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSearchDTO {
  private String eid;
  private String cellPhone;
  private String email;
  private String description;
  private String fullName;
}
