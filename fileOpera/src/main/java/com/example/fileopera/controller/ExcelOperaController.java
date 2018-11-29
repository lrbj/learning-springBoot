package com.example.fileopera.controller;

import com.example.fileopera.entity.People;
import com.example.fileopera.service.ExcelOperaService;
import com.example.fileopera.util.ExcelData;
import com.example.fileopera.util.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    private static final String FILEDIR = "./excelData/";

    @Autowired
    ExcelOperaService excelOperaService;


    @PostMapping("/upload")
    @ApiOperation(value = "上传excel文件")
    public ResponseObject upLoadExcel(@RequestParam("file") MultipartFile file)throws Exception{

        excelOperaService.readExcel(file);

        return  ResponseObject.success(null);
    }

    @PostMapping("/generate")
    @ApiOperation(value = "生成excel文件")
    public  ResponseObject createExcel( @RequestBody List<People> peopleList) throws Exception {
        String filename = "temp"+ UUID.randomUUID()+".xlsx";
        List<String> titleList = new ArrayList<>();
        titleList.add("姓名");
        titleList.add("电话");
        titleList.add("住址");

        //构造excelData
        ExcelData data = new ExcelData();
        data.setTitle(titleList);
        data.setFileName(filename);
        List<List<Object>> rowData = new ArrayList<>();
        for(People people: peopleList){
            List<Object> row = new ArrayList<>();
            row.add(people.getName());
            row.add(people.getPhone());
            row.add(people.getAddress());
            rowData.add(row);
        }
        data.setRows(rowData);
        excelOperaService.generateExcel(data,FILEDIR );
        return ResponseObject.success(null);
    }

}
