package com.example.swagger.demo.repository;


import com.example.swagger.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:25 PM 1/15/2019
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
