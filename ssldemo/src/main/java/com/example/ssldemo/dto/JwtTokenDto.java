package com.example.ssldemo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 5:41 PM 8/12/2020
 */
@Data
public class JwtTokenDto {
    public JwtTokenDto(String type, List<String> scopes, String metadata, long exp, String sub) {
        this.type = type;
        this.scopes = scopes;
        this.metadata = metadata;
        this.exp = exp;
        this.sub = sub;
    }
    public JwtTokenDto(){}

    private  String type;
    private  List<String> scopes;
    private  String metadata;
    private  long exp;
    private  String sub;


}
