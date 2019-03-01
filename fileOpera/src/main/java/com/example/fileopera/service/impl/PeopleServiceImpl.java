package com.example.fileopera.service.impl;

import com.example.fileopera.entity.People;
import com.example.fileopera.repository.PeopleRepository;
import com.example.fileopera.service.PeopleService;
import com.example.fileopera.util.PageUtil;
import com.example.fileopera.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:10 PM 3/1/2019
 */
@Service
public class PeopleServiceImpl implements PeopleService {
    @Autowired
    PeopleRepository peopleRepository;

    @Override
    public void save(People people) {
        peopleRepository.save(people);
    }

    @Override
    public People findById(Long id) {

        return peopleRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {

        peopleRepository.deleteById(id);
    }

    @Override
    public Pagination<People> findPage(Specification<People> specification) {
        Page<People> page =  peopleRepository.findAll(specification, PageUtil.getPageRequest());
        return new Pagination<People>((int)page.getTotalElements(), page.getContent());
    }
}
