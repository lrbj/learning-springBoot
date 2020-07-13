package com.example.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExcelData {

    @ApiModelProperty(value = "表头")
    private List<String> title;

    @ApiModelProperty(value = "数据")
    private List<List<Object>> rows;

    @ApiModelProperty(value = "标签页名字")
    private String sheetName = "sheet";

    @ApiModelProperty(value = "文件名字")
    private String fileName = "temp.xlsx";


}
