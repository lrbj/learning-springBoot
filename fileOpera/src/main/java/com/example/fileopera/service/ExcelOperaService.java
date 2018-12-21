package com.example.fileopera.service;

import com.example.fileopera.util.ReadFileConditon;
import com.example.fileopera.exception.BusinessException;
import com.example.fileopera.util.ExcelData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:17 PM 11/22/2018
 */
public interface ExcelOperaService {
    /**************************excel 表格的写入操作**********************************************/
    void  readExcel(MultipartFile file) throws BusinessException;
    List<String> readExcel(MultipartFile file, ReadFileConditon readFileConditon) throws  BusinessException;

    //检查该行是否满足指定列数
    boolean checkExcelLineData(Sheet sheet,int rowIndex, int columnNum);

    //判断分页是否存在
    boolean checkSheet(Workbook workbook, int sheetIndex);

    List<String> readExcelData(Sheet sheet, int ignoreRowIndex, int columnNum);

    Workbook  getWorkbook(MultipartFile file);
    /**********************excel表格的写入和导出操作***************************/

    //表格标题
    int writeTitleToExcel(Workbook workbook, Sheet sheet, List<String> titleList, int rowIndex);

    //写入内容
    int writeRowsToExcel(Workbook workbook, Sheet sheet, List<List<Object>>rows, int rowIndex);

    //把内容导入到文件中
    int exportDataToExcel(ExcelData data, OutputStream out );

    int generateExcel(ExcelData data, String fileDir) throws Exception;

    int exportExcel(HttpServletResponse response, ExcelData data) throws  Exception;
    Workbook getWorkbook(String fileName);


}
