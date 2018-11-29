package com.example.fileopera.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:19 PM 11/22/2018
 */
@Entity
@Data
@Table(name="people")
public class People  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;//姓名

    private String phone;//电话

    private String address;//地址

    public  People(){

    }

    public People(String name, String phone, String address){
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

}
