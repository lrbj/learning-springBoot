package com.example.fileopera.controller;

import com.example.fileopera.annotation.JpaPage;
import com.example.fileopera.entity.People;
import com.example.fileopera.service.PeopleService;
import com.example.fileopera.util.PageUtil;
import com.example.fileopera.util.Pagination;
import com.example.fileopera.util.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:15 PM 3/1/2019
 */
@RestController
@RequestMapping("/api/people")
@Api(tags = {"people管理"})
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    @PostMapping
    @ApiOperation(value = "增加People") //方法描述
    public ResponseObject addPeople(@RequestBody People people) {

        peopleService.save(people);
        return ResponseObject.success(null);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除People") //方法描述
    public ResponseObject deletePeople(@PathVariable("id") Long id) {
        peopleService.delete(id);
        return ResponseObject.success(null);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过Id查找People") //方法描述
    public ResponseObject findPeople(@PathVariable("id") Long id) {
        return ResponseObject.success(peopleService.findById(id));
    }


    @GetMapping
    @JpaPage
    @ApiOperation(value = "分页查询")
    public ResponseObject<Pagination<People>> findPage() {
        Pagination<People> pagination = peopleService.findPage(PageUtil.getSpecification());
        return ResponseObject.success(pagination);
    }
}
