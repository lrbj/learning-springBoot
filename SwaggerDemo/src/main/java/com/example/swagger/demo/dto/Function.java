package com.example.swagger.demo.dto;

import lombok.Data;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:53 PM 1/10/2020
 */
@Data
public class Function {

    private  boolean checkauth;

    private String displayname; //查询巡检计划

    private String  method;// "GET",

     private String  name;// "findAll",

    private String url;//
}
