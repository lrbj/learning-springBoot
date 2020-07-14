package com.example.common.controller;

import com.example.common.utils.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
@Api(tags = "test")
public class DemoController {

    @PostMapping
    @ApiOperation(value = "test", httpMethod = "POST")
    public ResponseObject add() {
        return ResponseObject.success("OK");
    }
}