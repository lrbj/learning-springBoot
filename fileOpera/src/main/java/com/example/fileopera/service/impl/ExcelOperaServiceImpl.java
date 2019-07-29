package com.example.fileopera.service.impl;

import com.example.fileopera.constant.ErrorEnum;
import com.example.fileopera.constant.FileConstant;
import com.example.fileopera.util.ReadFileConditon;
import com.example.fileopera.exception.BusinessException;
import com.example.fileopera.service.ExcelOperaService;
import com.example.fileopera.util.ExcelData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:34 PM 11/22/2018
 */
@Service
public class ExcelOperaServiceImpl implements ExcelOperaService {

    //电话正则
    public static final String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[01356789]|18[0-9]|19[89])\\d{8}$";

    @Override
    public void readExcel(MultipartFile file) throws BusinessException {
        if (null == file) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "文件不存在");
        }

        String fileName = file.getOriginalFilename();
        System.out.println("fileName=" + fileName);
        String suffix = FileConstant.SUFFIX_XLSX;

        Workbook workbook = null;
        try {
            if (fileName.endsWith(FileConstant.SUFFIX_XLS)) {
                workbook = new HSSFWorkbook(file.getInputStream());
                suffix = FileConstant.SUFFIX_XLS;
            } else if (fileName.endsWith(FileConstant.SUFFIX_XLSX)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "文件为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheetAt(0); //表格
        if (null == sheet) {
            throw new BusinessException(ErrorEnum.FILEFORMAT_ERROR.getCode(), ErrorEnum.FILEFORMAT_ERROR.getMsg());
        }

        int rowMum = sheet.getLastRowNum() + 1;//总的行数
        System.out.println("rowNum= :" + rowMum);
        for (int i = 0; i < rowMum; i++) {
            Row row = sheet.getRow(i);//获取当前行

            int cellNum = row.getLastCellNum(); //总的列数
            if (suffix.equals(FileConstant.SUFFIX_XLS)) { //不同版本的样式不同
                cellNum++;
            }
            System.out.println("cellNum=;" + cellNum);

            for (int j = 0; j < cellNum; j++) { //按照顺序输出
                //  for(Cell cell:row){ //输出都是不为空的
                Cell cell = row.getCell(j);
                if ((CellType.BLANK == cell.getCellType()) || (null == cell)) { //如果为空则退出
                    System.out.println("(null == cell) || (CellType.BLANK == cell.getCellType())");
                    continue;
                }
                if (!cell.getCellType().equals(CellType.STRING)) {
                    cell.setCellType(CellType.STRING); //将excel的字符转化成string
                }
                System.out.printf(cell.getCellType() + ",");
                System.out.printf(":cell" + cell.getStringCellValue() + ",");
            }
            System.out.println("");
        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 检查当前行是否合法
     *
     * @param sheet     当前分页
     * @param rowIndex  行id
     * @param columnNum 指定的列数
     * @return
     */
    @Override
    public boolean checkExcelLineData(Sheet sheet, int rowIndex, int columnNum) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return true;
        }
        int count = 0;
        for (Cell cell : row) { //非空单元个数
            count++;
        }
        if (count < columnNum) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkSheet(Workbook workbook, int sheetIndex) {
        if (null == workbook || null == workbook.getSheetAt(sheetIndex)) {
            return true;
        }

        return false;
    }

    @Override
    public List<String> readExcelData(Sheet sheet, int ignoreRowIndex, int columnNum) {
        HashSet<String> hashSet = new HashSet<>();
        int rowNum = sheet.getLastRowNum() + 1;
        int count = 0;
        List<String> stringList = new ArrayList<>();
        for (int i = ignoreRowIndex; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            if (null == row) {
                continue;
            }
            String str = new String();
            for (int j = 0; j < columnNum; j++) {
                Cell cell = row.getCell(j);
                if ((null == cell) || (CellType.BLANK == cell.getCellType())) { //如果为空则退出
                    str += " " + ",";
                    continue;
                }
                if (!cell.getCellType().equals(CellType.STRING)) {
                    cell.setCellType(CellType.STRING); //将excel的字符转化成string
                }
                str += cell.getStringCellValue().replace("\n", "") + ",";//去掉换行字符
            }

            count++; //有效行
            stringList.add(str);
        }

        return stringList;
    }

    @Override
    public Workbook getWorkbook(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Workbook workbook = null;
        String name = fileName.substring(0, fileName.lastIndexOf("."));

        //判断版本
        try {
            if (fileName.endsWith(FileConstant.SUFFIX_XLS)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (fileName.endsWith(FileConstant.SUFFIX_XLSX)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                workbook = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return workbook;
    }

    @Override
    public List<String> readExcel(MultipartFile file, ReadFileConditon readFileConditon) throws BusinessException {
        //1、文件分页是否存在
        Workbook workbook = getWorkbook(file);
        if (checkSheet(workbook, readFileConditon.getSheetIndex())) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "文件不存在");
        }

        //2、表格题目的判断
        Sheet sheet = workbook.getSheetAt(readFileConditon.getSheetIndex());
        if (checkExcelLineData(sheet, FileConstant.TITLE_ROWINDEX, readFileConditon.getColumnNum())) {
            throw new BusinessException(ErrorEnum.IMPORT_DATA_CLUMMUN_ERR.getCode(), ErrorEnum.IMPORT_DATA_CLUMMUN_ERR.getMsg());
        }

        //3、将文件中所有数据取出
        List<String> stringList = readExcelData(sheet, readFileConditon.getIgonreRowNum(), readFileConditon.getColumnNum());

        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringList;

    }


    /**
     * @param workbook
     * @param sheet     当前分页
     * @param titleList 题目
     * @param rowIndex  当前所在行
     * @return 下一行所在标志
     */
    @Override
    public int writeTitleToExcel(Workbook workbook, Sheet sheet, List<String> titleList, int rowIndex) {
        Row row = sheet.createRow(rowIndex);//当前行
        int listNum = titleList.size();
        int i = 0;
        //设置列宽
        for (i = 0; i < listNum; i++) {
            sheet.setColumnWidth(i, 12 * 256);
        }

        //设置居中加粗
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(font);

        for (i = 0; i < listNum; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titleList.get(i));
            cell.setCellStyle(cellStyle);
        }
        rowIndex++;
        return rowIndex;
    }

    @Override
    public int writeRowsToExcel(Workbook workbook, Sheet sheet, List<List<Object>> rows, int rowIndex) {

        for (List<Object> rowData : rows) {
            Row row = sheet.createRow(rowIndex);
            int colunmIndex = 0;
            for (Object cellData : rowData) {
                Cell cell = row.createCell(colunmIndex);
                if (cellData != null) {
                    cell.setCellValue(cellData.toString());//默认转化为string 类型
                } else {
                    cell.setCellValue("");
                }
                colunmIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }


    @Override
    public int exportDataToExcel(ExcelData data, OutputStream out) {
        Workbook workbook = getWorkbook(data.getFileName());
        if (null == workbook) {
            return FileConstant.ERR_FILE;
        }

        int rowIndex = 0;
        try {
            Sheet sheet = workbook.createSheet(data.getSheetName());
            rowIndex = writeTitleToExcel(workbook, sheet, data.getTitle(), rowIndex);
            rowIndex = writeRowsToExcel(workbook, sheet, data.getRows(), rowIndex);
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rowIndex;
    }

    @Override
    public int generateExcel(ExcelData data, String fileDir) throws Exception {

        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdir();
        }
        FileOutputStream out = new FileOutputStream(file + "/" + data.getFileName());
        return exportDataToExcel(data, out);
    }

    @Override
    public int exportExcel(HttpServletResponse response, ExcelData data) throws Exception {

        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(data.getFileName(), "utf-8"));

        return exportDataToExcel(data, response.getOutputStream());
    }

    /**
     * 根据不同的后缀名获取workbook
     *
     * @param fileName
     * @return
     */
    @Override
    public Workbook getWorkbook(String fileName) {
        Workbook workbook = null;
        if (fileName.endsWith(FileConstant.SUFFIX_XLS)) {
            workbook = new HSSFWorkbook();
        } else if (fileName.endsWith(FileConstant.SUFFIX_XLSX)) {
            workbook = new XSSFWorkbook();
        } else {
            return null;
        }
        return workbook;
    }


}
