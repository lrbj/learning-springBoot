package com.example.common.account;

import lombok.Data;

import java.util.Map;

@Data
public class User {
  private String eid;
  private String userName;
  private String fullName;
  private String email;
  private String cellPhone;
  private String creator;
  private String description;

  // 角色信息，key: roleId, value: roleName
  private Map<String, String> roleInfo;

  public boolean isAdmin() {
    if (this.roleInfo != null) {
      for (Map.Entry<String, String> role : roleInfo.entrySet()) {
        if ("SuperAdmin".equals(role.getValue()) || role.getValue().contains("超级")) {
          return true;
        }
      }
    }

    return false;
  }


}
