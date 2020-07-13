package com.example.common.mq.vo;

import lombok.Data;

import java.util.List;

@Data
public class MailVo {
    private String subject; //邮件主题

    private String content;//邮件内容

    private List<String> toAddress; //收件人

    private List<String> ccAddress; //抄送者

    private List<AttachFile> files;//附件

}
