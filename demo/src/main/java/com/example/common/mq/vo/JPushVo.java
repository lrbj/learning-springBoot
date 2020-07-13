package com.example.common.mq.vo;

import lombok.Data;

@Data
public class JPushVo {
    private String appkey;

    private String secret;

    private String title; //消息

    private String[] alias; //用户EID

    private String extra;//其他信息

    private String alert;


}
