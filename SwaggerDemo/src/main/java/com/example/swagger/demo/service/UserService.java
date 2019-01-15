package com.example.swagger.demo.service;

import com.example.swagger.demo.entity.User;
import com.example.swagger.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:29 PM 1/15/2019
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public int  saveListUser (List<User> userList){
        int nRet = -1;
        for(User user: userList ){
            save(user);
        }
        nRet = 0;

        return  nRet;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public  List<User> findAll(){
       return userRepository.findAll();
    }
}
