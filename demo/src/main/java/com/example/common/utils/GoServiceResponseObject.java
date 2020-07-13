package com.example.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class GoServiceResponseObject {
  private Integer code;
  private String msg;

  public static GoServiceResponseObject build(JSONObject jsonObject) {
    GoServiceResponseObject dto = new GoServiceResponseObject();
    if (jsonObject.getInteger("code") == null) {
      dto.setCode(-1);
    } else {
      dto.setCode( jsonObject.getInteger("code"));
    }

    if (jsonObject.getString("msg") != null) {
      dto.setMsg(jsonObject.getString("msg"));
    }
    return dto;
  }
}
