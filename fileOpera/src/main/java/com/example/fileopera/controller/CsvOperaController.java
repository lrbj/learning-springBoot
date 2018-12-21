package com.example.fileopera.controller;

import com.example.fileopera.service.CsvOperaService;
import com.example.fileopera.service.ExcelOperaService;
import com.example.fileopera.util.FileUtil;
import com.example.fileopera.util.ReadFileConditon;
import com.example.fileopera.util.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 11:02 AM 11/27/2018
 */
@RequestMapping("/api/csv")
@RestController
@Api(tags = "csv")
public class CsvOperaController {
    @Autowired
    CsvOperaService csvOperaService;


    @PostMapping("/upload")
    @ApiOperation(value = "上传csv文件")
    public ResponseObject upLoadCsv(@RequestParam("file") MultipartFile file)throws Exception{

        /*String name = file.getOriginalFilename();
        String path = "";
        String filePath = "./csvdata/";
        //将文件写入另一个文件中
        path = FileUtil.uploadFile(file.getBytes(), filePath, name);
        System.out.println("path=:" + path);
        csvOperaService.readCsvFile(file);*/
        ReadFileConditon readFileConditon = new ReadFileConditon();
        readFileConditon.setColumnNum(10);
        readFileConditon.setSheetIndex(2);
        readFileConditon.setIgonreRowNum(1);
        List<String> stringList = csvOperaService.readCsvFile(file,readFileConditon);
        for(String str:stringList){
            System.out.println(str);
        }

        return  ResponseObject.success(null);
    }

}
