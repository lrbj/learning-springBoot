package com.example.fileopera.service.impl;

import com.example.fileopera.constant.ErrorEnum;
import com.example.fileopera.entity.People;
import com.example.fileopera.exception.BusinessException;
import com.example.fileopera.service.ExcelOperaService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:34 PM 11/22/2018
 */
@Service
public class PeopleServiceImpl implements ExcelOperaService {

    private static final String SUFFIX_XLS = ".xls";
    private static final String SUFFIX_XLSX = ".xlsx";
    //电话正则
    public static final String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[01356789]|18[0-9]|19[89])\\d{8}$";
    
    @Override
    public void readExcel(MultipartFile file) throws BusinessException {
        if(null == file){
           throw  new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "文件不存在") ;
        }

        String fileName = file.getOriginalFilename();
        System.out.println("fileName="+fileName);
        String suffix = SUFFIX_XLSX;

        Workbook workbook = null;
        try {
            if(fileName.endsWith(SUFFIX_XLS)){
              workbook = new HSSFWorkbook(file.getInputStream());
              suffix = SUFFIX_XLS;
            }else if(fileName.endsWith(SUFFIX_XLSX)){
               workbook = new XSSFWorkbook(file.getInputStream());
            }else{
                throw  new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "文件为空");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheetAt(0); //表格
        if(null == sheet ){
            throw  new BusinessException(ErrorEnum.FILEFORMAT_ERROR.getCode(),ErrorEnum.FILEFORMAT_ERROR.getMsg());
        }

        int rowMum = sheet.getLastRowNum()+1 ;//总的行数
        System.out.println("rowNum= :"+ rowMum);
        for(int i = 0; i <rowMum; i++ ){
            Row row = sheet.getRow(i);//获取当前行

            int cellNum = row.getLastCellNum(); //总的列数
            if(suffix.equals(SUFFIX_XLS)){
                cellNum++;
            }
            System.out.println("cellNum=;"+cellNum);
            for(int j = 0; j < cellNum; j++){
                System.out.printf(row.getCell(j)+",");
            }
            System.out.println("");
        }

    }
}