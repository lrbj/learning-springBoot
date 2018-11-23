package com.example.fileopera.controller;

import com.example.fileopera.entity.People;
import com.example.fileopera.service.ExcelOperaService;
import com.example.fileopera.util.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:53 PM 11/22/2018
 */
@RequestMapping("/api/excel")
@RestController
@Api(tags = "excel")
public class ExcelOperaController {

    @Autowired
    ExcelOperaService excelOperaService;


    @PostMapping("/upload")
    @ApiOperation(value = "上传excel文件")
    public ResponseObject upLoadDevices(@RequestParam("file") MultipartFile file)throws Exception{

        excelOperaService.readExcel(file);

        return  ResponseObject.success(null);
    }

    @PostMapping("/generate")
    @ApiOperation(value = "生成excel文件")
    public  ResponseObject createExcel(@RequestBody List<People> peopleList) throws IOException {
        String Filename = "./temp"+ UUID.randomUUID()+".xlsx";
        List<String> titleList = new ArrayList<>();
        titleList.add("姓名");
        titleList.add("电话");
        titleList.add("住址");
        excelOperaService.exportDataToExcel(peopleList, titleList ,Filename);
        return ResponseObject.success(null);
    }
}
