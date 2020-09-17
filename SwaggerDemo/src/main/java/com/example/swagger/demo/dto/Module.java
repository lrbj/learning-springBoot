package com.example.swagger.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:49 PM 1/10/2020
 */
@Data
public class Module {
    private String name;
    private List<String> functions;
    private boolean show =true;
}
