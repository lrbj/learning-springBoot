package com.example.common.utils;


import com.example.common.constant.ErrorEnum;
import lombok.Data;

@Data
public class ResponseObject<T> {
  private int code;
  private String msg;

  private T data;

  public ResponseObject() {
  }

  public ResponseObject(int code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static <T> ResponseObject<T> fail(ErrorEnum errorCode) {
    ResponseObject<T> responseObject = new ResponseObject<>();
    responseObject.setCode(errorCode.getCode());
    responseObject.setMsg(errorCode.getMsg());
    return responseObject;
  }

  public static <T> ResponseObject<T> success(T data) {
    ResponseObject<T> respObject = new ResponseObject<>();
    respObject.setCode(200);
    respObject.setMsg("处理成功");
    respObject.setData(data);
    return respObject;
  }

  @Override
  public String toString() {
    return "ResponseObject{" +
        "code=" + code +
        ", msg='" + msg + '\'' +
        ", data=" + data +
        '}';
  }

}
