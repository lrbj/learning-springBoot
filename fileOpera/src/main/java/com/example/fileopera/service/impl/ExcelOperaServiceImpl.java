package com.example.fileopera.service.impl;

import com.example.fileopera.constant.ErrorEnum;
import com.example.fileopera.entity.People;
import com.example.fileopera.exception.BusinessException;
import com.example.fileopera.service.ExcelOperaService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:34 PM 11/22/2018
 */
@Service
public class ExcelOperaServiceImpl implements ExcelOperaService {

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

             for(int j = 0; j < cellNum; j++){ //按照顺序输出
          //  for(Cell cell:row){ //输出都是不为空的
             Cell cell = row.getCell(j);
                if((CellType.BLANK == cell.getCellType()) || (null == cell) ){ //如果为空则退出
                    System.out.println("(null == cell) || (CellType.BLANK == cell.getCellType())");
                    continue;
                }
                if(!cell.getCellType().equals(CellType.STRING)){
                    cell.setCellType(CellType.STRING); //将excel的字符转化成string
                }
                System.out.printf(cell.getCellType()+",");
                System.out.printf(":cell"+ cell.getStringCellValue()+",");
            }
            System.out.println("");
        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exportDataToExcel(List<People> peopleList, List<String> titleList,String exportFilePath) throws BusinessException {

        FileOutputStream out = null;
        try {

                File exportFile = new File(exportFilePath);
                if(!exportFile.exists()){
                    exportFile.createNewFile();
                }
                out = new FileOutputStream(exportFile);
                Workbook workbook = null;
                if(exportFilePath.endsWith(SUFFIX_XLS)){
                    workbook = new HSSFWorkbook();
                }else if(exportFilePath.endsWith(SUFFIX_XLSX)){
                    workbook = new XSSFWorkbook();
                }else{
                    throw  new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "文件为空");
                }

                Sheet sheet = workbook.createSheet();
                //设置题目
                createTitle(workbook,sheet, titleList);

                //填充内容

                int  rowIndex = 1;
                int clumNum = titleList.size();
                for(People people: peopleList){
                    Row row = sheet.createRow(rowIndex);
                    row.createCell(0).setCellValue(people.getName());
                    row.createCell(1).setCellValue(people.getPhone());
                    row.createCell(2).setCellValue(people.getAddress());
                    rowIndex++;
                }
                workbook.write(out);
                out.flush();
                workbook.close();


        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void createTitle(Workbook workbook, Sheet sheet, List<String> titleList) {
        Row row = sheet.createRow(0);//第一行
        int listNum = titleList.size();
        int i = 0;
        //设置列宽
        for(i = 0; i < listNum; i++)
        {
            sheet.setColumnWidth(i, 12*256);
        }

        //设置居中加粗
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(font);

        for(i = 0; i < listNum; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(titleList.get(i));
            cell.setCellStyle(cellStyle);
        }
    }


}
