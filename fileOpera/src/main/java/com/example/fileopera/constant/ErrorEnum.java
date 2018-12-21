package com.example.fileopera.constant;

import lombok.Data;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:09 PM 11/22/2018
 */

public enum ErrorEnum {
    PARAM_ERROR(1000, "参数错误"),
    FILEFORMAT_ERROR(1001,"文件格式错误"),
    FILEEMPTY_ERROR(1002,"文件为空"),
    IMPORT_DATA_REPEAT(1003, "导入数据存在重复"),
    IMPORT_DATA_CLUMMUN_ERR(1004, "导入数据列失败"),
    IMPORT_DATA_FAIL(1005, "导入数据失败"),
    ;

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
