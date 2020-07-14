package com.example.common.mq.vo;

import lombok.Data;

@Data
public class AttachFile {
    private String filename;

    private byte[] payload;
}
