package com.example.fileopera.service;

import com.example.fileopera.entity.People;
import com.example.fileopera.util.Pagination;
import org.springframework.data.jpa.domain.Specification;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:08 PM 3/1/2019
 */
public interface PeopleService {

    void save(People people);

    People findById(Long Id);

    void delete(Long Id);

    Pagination<People> findPage(Specification<People> specification);
}
