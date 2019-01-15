package com.example.swagger.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:19 PM 1/15/2019
 */
@Data
@Entity
@Table(name = "Test_user")
public class User extends BaseEntity<User>{

    @Column(nullable = true, length = 5)
    private  String name;

    @Column(nullable = true)
    private  Integer age;
}
