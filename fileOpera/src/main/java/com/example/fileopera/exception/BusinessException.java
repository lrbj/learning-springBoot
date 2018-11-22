package com.example.fileopera.exception;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:27 PM 11/22/2018
 */
public class BusinessException extends RuntimeException {
     private int code;
    private String msg;
    public BusinessException(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
