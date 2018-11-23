package com.example.fileopera.service;

import com.example.fileopera.entity.People;
import com.example.fileopera.exception.BusinessException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:17 PM 11/22/2018
 */
public interface ExcelOperaService {
    void  readExcel(MultipartFile file) throws BusinessException;

    void exportDataToExcel(List<People> peopleList,List<String> titleList, String exportFilePath ) throws BusinessException, IOException;

    void createTitle(Workbook workbook, Sheet sheet, List<String> titleList);

}
