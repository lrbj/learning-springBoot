package com.example.fileopera.controller;

import com.example.fileopera.service.ExcelOperaService;
import com.example.fileopera.util.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseObject upLoadDevices(@RequestParam("file") MultipartFile file){

        excelOperaService.readExcel(file);

        return  ResponseObject.success(null);
    }
}
