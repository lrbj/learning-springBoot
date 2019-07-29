package com.example.swagger.demo.controller;

import com.sun.javafx.collections.MappingChange;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:19 PM 1/10/2019
 */
@RestController
@RequestMapping("/say")
@Api(value = "SayController|一个用来测试swagger注解的控制器")
public class SayController {


    @GetMapping("/getUserName")
    @ApiOperation(value = "getUserName", notes = "根据用户编号获取用户姓名test: 仅1和2有正确返回")
    @ApiImplicitParam(paramType = "query", name = "userNumber",
            value = "用户编号", required = true,
            dataType = "Integer", example = "1")
    public String getUserName(@RequestParam(required = true) Integer userNumber) {
        if (userNumber == 1) {
            return "张三丰";
        } else if (userNumber == 2) {
            return "慕容复";
        } else {
            return "未知";
        }
    }


    @PostMapping("/updatePassword")
    @ApiOperation(value = "updatePassword", notes = "修改用户密码,根据用户id修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "Integer", example = "111"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "旧密码", required = true, dataType = "String", example = "11111"),
            @ApiImplicitParam(paramType = "query", name = "newPassword", value = "新密码", required = true, dataType = "String",
                    examples = @Example({@ExampleProperty(value = "88888")}))
    })
    public String updatePassword(@RequestParam(value = "userId", required = true) Integer userId, @RequestParam(value = "password", required = true) String password,
                                 @RequestParam(value = "newPassword", required = true) String newPassword) {
        if (userId <= 0 || userId > 2) {
            return "未知的用户";
        }
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword)) {
            return "密码不能为空";
        }
        if (password.equals(newPassword)) {
            return "新旧密码不能相同";
        }
        return "密码修改成功!";
    }


    @PostMapping("/findPassword")
    @ApiOperation(value = "findPassword")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",
            name = "allParams",
            value = "",
            dataTypeClass = Map.class,
            required = true, examples = @Example({@ExampleProperty(value = "{'user:'id''}")}))})
    public void findPassword(@RequestParam(required = true) Map<String, String> allParams) {

    }

}
