package com.example.common.mq.vo;

import lombok.Data;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 11:16 AM 3/26/2020
 */
@Data
public class AttachFile {
    private String filename;

    private byte[] payload;
}
