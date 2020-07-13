package com.example.common.exception;

import com.example.common.constant.ErrorEnum;

/**
 * 所有业务请求异常父类
 */
public class BusinessException extends RuntimeException {
    public int code;
    public String msg;


    public BusinessException(ErrorEnum e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
