package com.example.fileopera.util;


import com.example.fileopera.constant.ErrorEnum;
import lombok.Data;

@Data
public class ResponseObject<T> {
    private int code;
    private String msg;

    private T data;

    public static <T> ResponseObject<T> success(T data) {
        ResponseObject<T> respObject = new ResponseObject<>();
        respObject.setCode(200);
        respObject.setMsg("处理成功");
        respObject.setData(data);
        return respObject;
    }

    public static <T> ResponseObject<T> fail(ErrorEnum errorCode) {
        ResponseObject<T> responseObject = new ResponseObject<>();
        responseObject.setCode(errorCode.getCode());
        responseObject.setMsg(errorCode.getMsg());
        return responseObject;
    }


}
