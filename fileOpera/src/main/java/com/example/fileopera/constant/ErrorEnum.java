package com.example.fileopera.constant;

import lombok.Data;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:09 PM 11/22/2018
 */

public enum ErrorEnum {
    PARAM_ERROR(1000, "参数错误"),
    FILEFORMAT_ERROR(1001,"文件格式错误"),;

    private int code;
    private String msg;
    ErrorEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return  msg;
    }
}
