package com.example.fileopera.service;

import com.example.fileopera.entity.People;
import com.example.fileopera.exception.BusinessException;
import com.example.fileopera.util.ExcelData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:17 PM 11/22/2018
 */
public interface ExcelOperaService {
    void  readExcel(MultipartFile file) throws BusinessException;

    //表格标题
    int writeTitleToExcel(Workbook workbook, Sheet sheet, List<String> titleList, int rowIndex);

    //写入内容
    int writeRowsToExcel(Workbook workbook, Sheet sheet, List<List<Object>>rows, int rowIndex);

    //把内容导入到文件中
    int exportDataToExcel(ExcelData data, OutputStream out );

    int generateExcel(ExcelData data, String fileDir) throws Exception;

    int exportExcel(HttpServletResponse response, ExcelData data) throws  Exception;

    Workbook getWorkbook(String fileSuffix);
}
