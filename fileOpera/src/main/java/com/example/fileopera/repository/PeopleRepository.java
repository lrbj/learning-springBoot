package com.example.fileopera.repository;

import com.example.fileopera.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:24 PM 11/22/2018
 */
public interface PeopleRepository extends JpaRepository<People, Long> {
}
