package com.example.swagger.demo.controller;

import com.example.swagger.demo.entity.User;
import com.example.swagger.demo.repository.UserRepository;
import com.example.swagger.demo.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:32 PM 1/15/2019
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    @ApiOperation(value="addUser", notes="添加数据")
    public int  addUser(@RequestBody List<User> userList){
        int nRet= -2;
        nRet = userService.saveListUser(userList);
        System.out.println("nRet = "+ nRet);
        return  nRet;
    }

    @PostMapping("/findAll")
    @ApiOperation(value="findAll", notes="查找全部数据")
    public List<User> findAll(){
       return userService.findAll();
    }
}
