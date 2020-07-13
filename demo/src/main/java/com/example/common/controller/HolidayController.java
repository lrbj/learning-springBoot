package com.example.common.controller;



import com.example.common.annotation.JpaPage;
import com.example.common.constant.ErrorEnum;
import com.example.common.entity.Holiday;
import com.example.common.service.HolidayService;
import com.example.common.utils.JpaUtils;
import com.example.common.utils.Pagination;
import com.example.common.utils.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/holiday")
@Api(tags = "添加节假日")
public class HolidayController {

    @Autowired
    HolidayService holidayService;

    @PostMapping
    @ApiOperation(value = "添加节假日", httpMethod = "POST")
    public ResponseObject add(@ApiParam @RequestBody Holiday holiday) throws Exception {
        Boolean b = holidayService.addHoliday(holiday);
        if(!b){
            return ResponseObject.fail(ErrorEnum.HOLIDAY_EXIST);
        }
        return ResponseObject.success(b);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除节假日", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "id")
    public ResponseObject delete(@PathVariable("id") Long id) throws Exception {
        if(!holidayService.isAllowOperate(id)){
            return ResponseObject.fail(ErrorEnum.NO_PERMISSION);
        }
        Boolean b = holidayService.deleteHoliday(id);
        return ResponseObject.success(b);

    }


    @GetMapping
    @ApiOperation(value = "查询节假日", httpMethod = "GET")
    @JpaPage
    public ResponseObject findAll() throws Exception {
        Page<Holiday> pageList = holidayService.findByCondition(JpaUtils.getGlobalSpecification());
        Pagination<Holiday> page = new Pagination<Holiday>((int) pageList.getTotalElements(), pageList.getContent());
        return ResponseObject.success(page);

    }


    @PatchMapping
    @ApiOperation(value = "修改节假日", httpMethod = "PATCH")
    public ResponseObject updateName(@ApiParam @RequestBody Holiday holiday) throws Exception {
        if(!holidayService.isAllowOperate(holiday.getId())){
            return ResponseObject.fail(ErrorEnum.NO_PERMISSION);
        }
        boolean b = holidayService.update(holiday);
        if(!b){
            return ResponseObject.fail(ErrorEnum.HOLIDAY_EXIST);
        }
        return ResponseObject.success(b);

    }


}
