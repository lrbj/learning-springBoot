package com.example.ssldemo.controller;

import com.example.ssldemo.dto.JwtTokenDto;
import com.example.ssldemo.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 10:00 AM 8/13/2020
 */

@RestController
@RequestMapping("/api/test")
@Api(tags = "test")
public class TestController {
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/jwtToken")
    @ApiOperation("jwtToken")
    public Object test(@RequestBody JwtTokenDto jwtTokenDto) throws Exception {
        String subject = "test";
        Map<String, Object> map = BeanMap.create(jwtTokenDto);
        String id = UUID.randomUUID().toString();
        long time = 60 * 60 * 1000;
        String issuer = "kayla";
        String jwt = JwtTokenUtil.createJWT(id, issuer,subject,time,map);
        System.out.println("jwt: "+ jwt);
        System.out.println("解密");
        Claims c = JwtTokenUtil.parseJWT(jwt);
        System.out.println(c.getExpiration());
        System.out.println("exp"+c.getExpiration());
        System.out.println("cuurent"+ System.currentTimeMillis());
       return  "Hello World!";
    }

    @GetMapping("/template")
    @ApiOperation("template")
    public Object testTemplate() {
        ResponseEntity responseEntity = restTemplate.exchange(
              "https://localhost:7080/polling/api/test/test1",
                HttpMethod.GET,
                null,String.class);
        return responseEntity.getBody();
    }
}
