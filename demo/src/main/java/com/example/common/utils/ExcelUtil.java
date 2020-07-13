package com.example.common.utils;


import com.example.common.annotation.ExcelProperty;
import com.example.common.constant.ErrorEnum;
import com.example.common.constant.FileImportAction;
import com.example.common.exception.BusinessException;
import org.apache.commons.collections4.list.TreeList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ExcelUtil {

    /**
     * @param workbook
     * @param sheet     当前分页
     * @param titleList 题目
     * @param rowIndex  当前所在行
     * @return 下一行所在标志
     */
    public static int writeTitleToExcel(Workbook workbook, Sheet sheet, List<String> titleList, int rowIndex) {
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


    public static int writeRowsToExcel(Workbook workbook, Sheet sheet, List<List<Object>> rows, int rowIndex) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
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
                cell.setCellStyle(cellStyle);
                colunmIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    /**
     * 此方法将数据集合按65000个进行分割成多个子集合
     *
     * @param list 需要处理的list数据集合
     * @return
     * @author OnlyOne
     */
    public static Map<Integer, List<List<Object>>> daData(List<List<Object>> list) {
        int count = list.size() / 65000;
        int yu = list.size() % 65000;
        Map<Integer, List<List<Object>>> map = new HashMap<>();
        for (int i = 0; i <= count; i++) {
            List<List<Object>> subList = new ArrayList<>();
            if (i == count) {
                subList = list.subList(i * 65000, 65000 * i + yu);
            } else {
                subList = list.subList(i * 65000, 65000 * (i + 1) - 1);
            }
            map.put(i, subList);
        }
        return map;
    }

    public static int exportDataToExcel(ExcelData data, OutputStream out) {
        Workbook workbook = getWorkbook(data.getFileName());
        if (null == workbook) {
            return FileImportAction.ERR_FILE;
        }
        int rowIndex = 0;

        List<String> title = data.getTitle();
        List<List<Object>> rows = data.getRows();
        Map<Integer, List<List<Object>>> rowsMap = daData(rows);
        try {
            for (Map.Entry<Integer, List<List<Object>>> item : rowsMap.entrySet()) {
                rowIndex = 0;
                Sheet sheet = workbook.createSheet(item.getKey().toString());
                rowIndex = writeTitleToExcel(workbook, sheet, title, rowIndex);
                rowIndex = writeRowsToExcel(workbook, sheet, item.getValue(), rowIndex);
            }
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


    /**
     * 生excel表格
     *
     * @param data
     * @param fileDir
     * @return
     * @throws Exception
     */
    public static int generateExcel(ExcelData data, String fileDir) throws Exception {

        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdir();
        }
        FileOutputStream out = new FileOutputStream(file + "/" + data.getFileName());
        return exportDataToExcel(data, out);
    }


    /**
     * 导出excel
     *
     * @param response
     * @param data
     * @return
     * @throws Exception
     */
    public static int exportExcel(HttpServletResponse response, ExcelData data) throws Exception {

        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(data.getFileName(), "utf-8"));

        return exportDataToExcel(data, response.getOutputStream());
    }

    /**
     * 根据不同的后缀和输入流获取workbook
     *
     * @param fileName
     * @param inputStream
     * @return
     */
    public static Workbook getWorkbook(String fileName, InputStream inputStream) {
        Workbook workbook = null;
        try {
            if (fileName.endsWith(FileImportAction.SUFFIX_XLS)) {
                workbook = inputStream == null ? new HSSFWorkbook() : new HSSFWorkbook(inputStream);
            } else if (fileName.endsWith(FileImportAction.SUFFIX_XLSX)) {
                workbook = inputStream == null ? new XSSFWorkbook() : new XSSFWorkbook(inputStream);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 根据后缀获取workbook
     *
     * @param fileName
     * @return
     */
    public static Workbook getWorkbook(String fileName) {
        return getWorkbook(fileName, null);
    }

    /**
     * 根据上传的文件获取workbook
     *
     * @param file
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file) {
        try {
            return getWorkbook(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ExcelData prepareExcelData(List<Object> objects, String fileName) throws IllegalAccessException {
        List<String> titles = new TreeList();
        List<List<Object>> rowDatas = new TreeList();
        boolean parseTitle = true;
        Iterator var4 = objects.iterator();

        while (var4.hasNext()) {
            Object data = var4.next();
            Field[] fields = data.getClass().getDeclaredFields();
            int var9;
            if (parseTitle) {
                Field[] var7 = fields;
                int var8 = fields.length;

                for (var9 = 0; var9 < var8; ++var9) {
                    Field field = var7[var9];
                    ExcelProperty anno = (ExcelProperty) field.getAnnotation(ExcelProperty.class);
                    if (anno != null) {
                        titles.add(anno.title());
                    }
                }
            }

            parseTitle = false;
            List<Object> row = new TreeList();
            Field[] var16 = fields;
            var9 = fields.length;

            for (int var17 = 0; var17 < var9; ++var17) {
                Field field = var16[var17];
                ExcelProperty anno = (ExcelProperty) field.getAnnotation(ExcelProperty.class);
                if (anno != null) {
                    field.setAccessible(true);
                    Object o = field.get(data);
                    row.add(o);
                }
            }

            rowDatas.add(row);
        }

        ExcelData excelData = new ExcelData();
        excelData.setRows(rowDatas);
        excelData.setTitle(titles);
        excelData.setFileName(fileName);
        return excelData;
    }

    public static int exportExcelToReponse(List<Object> objects, String fileName, HttpServletResponse response) throws Exception {
        ExcelData excelData = prepareExcelData(objects, fileName);
        return exportExcel(response, excelData);
    }

    public static int exportExcel(HttpServletResponse response, List<ExcelData> data, String fileName) throws Exception {

        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

        return exportDataListToExcel(data, response.getOutputStream(), fileName);
    }

    public static int exportDataListToExcel(List<ExcelData> excelDataList, ServletOutputStream out, String fileName) {
        if(excelDataList.size() == 1){
            exportDataToExcel(excelDataList.get(0),out);
        }

        Workbook workbook = getWorkbook(fileName);
        if (null == workbook) {
            return FileImportAction.ERR_FILE;
        }

        try {
            for(ExcelData data: excelDataList) {
                int rowIndex = 0;
                List<String> title = data.getTitle();
                List<List<Object>> rows = data.getRows();
                Map<Integer, List<List<Object>>> rowsMap = daData(rows);
                for (Map.Entry<Integer, List<List<Object>>> item : rowsMap.entrySet()) {
                    rowIndex = 0;
                    Sheet sheet = workbook.createSheet(data.getSheetName() + item.getKey().toString());
                    rowIndex = writeTitleToExcel(workbook, sheet, title, rowIndex);
                    rowIndex = writeRowsToExcel(workbook, sheet, item.getValue(), rowIndex);
                }
            }
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
        return  excelDataList.size();
    }

    /**********************************************excel文件读取***************************************************/
    /**
     * 检查当前行是否合法（一行非空的列数必须大于等于规定的列数（columnNum））
     *
     * @param sheet     当前分页
     * @param rowIndex  行id
     * @param columnNum 指定需要的列数
     * @return
     */
    public static boolean checkExcelLineData(Sheet sheet, int rowIndex, int columnNum) {
        Row row = sheet.getRow(rowIndex);
        if (row == null || row.getFirstCellNum() < 0) {
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


    public static boolean checkSheet(Workbook workbook, int sheetIndex) {
        if (null == workbook || null == workbook.getSheetAt(sheetIndex)) {
            return true;
        }

        return false;
    }


    /**
     * 读取正文
     *
     * @param sheet
     * @param ignoreRowIndex
     * @param columnNum
     * @return
     */
    public static List<String> readExcelData(Sheet sheet, int ignoreRowIndex, int columnNum) {
        HashSet<String> hashSet = new HashSet<>();
        int rowNum = sheet.getLastRowNum() + 1;
        List<String> stringList = new ArrayList<>();
        for (int i = ignoreRowIndex; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            //防止读到空行
            if (null == row || (row.getFirstCellNum() < 0)) {
                continue;
            }
            //读取每一行
            String str = readRowString(row, columnNum);
            stringList.add(str);
        }

        return stringList;
    }

    /**
     * 获取当前行，不同项以“,”隔开
     *
     * @param row
     * @param colunmNum
     * @return
     */
    public static String readRowString(Row row, int colunmNum) {
        String str = new String();
        for (int j = 0; j < colunmNum; j++) {
            //如果为空则退出
            Cell cell = row.getCell(j);
            if (isCellEmpty(cell)) {
                str += " ";
                if (j < colunmNum - 1) {
                    str += ",";
                }
                continue;
            }
            SetCellTypeToString(cell);
            //去掉换行字符
            str += cell.getStringCellValue().replace("\n", "");
            if (j < colunmNum - 1) {
                str += ",";
            }
        }

        return str;
    }

    /**
     * 以数组形式获取
     *
     * @param row
     * @param colunmNum
     * @return
     */
    public static List<String> readRowList(Row row, int colunmNum) {
        List<String> objectList = new ArrayList<>();
        for (int j = 0; j < colunmNum; j++) {
            Cell cell = row.getCell(j);
            //如果为空则退出
            if (isCellEmpty(cell)) {
                objectList.add("");
                continue;
            }
            SetCellTypeToString(cell);
            objectList.add(cell.getStringCellValue());
        }

        return objectList;
    }

    /**
     * 判断当前元素是否为空
     *
     * @param cell
     * @return
     */
    public static boolean isCellEmpty(Cell cell) {
        if ((null == cell) || (CellType.BLANK == cell.getCellType())) {
            return true;
        }
        return false;
    }

    /**
     * 将单元转化为String 类型
     *
     * @param cell
     */
    public static void SetCellTypeToString(Cell cell) {
        if (!cell.getCellType().equals(CellType.STRING)) {
            //将excel的字符转化成string
            cell.setCellType(CellType.STRING);
        }
    }


    public static Workbook getWorkbook(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Workbook workbook = null;
        //判断版本
        try {
            if (fileName.endsWith(FileImportAction.SUFFIX_XLS)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (fileName.endsWith(FileImportAction.SUFFIX_XLSX)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                workbook = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return workbook;
    }

    public static List<String> readExcelFromWorkbook(Workbook workbook, ReadFileConditon readFileConditon) throws BusinessException {
        //1、文件分页是否存在
        if (checkSheet(workbook, readFileConditon.getSheetIndex())) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR);
        }

        //2、表格题目的判断（检查某一行（默认第一行）的列数是否 >= 指定读取的列数）
        Sheet sheet = workbook.getSheetAt(readFileConditon.getSheetIndex());
        if (checkExcelLineData(sheet, readFileConditon.getTitleRowIndex(), readFileConditon.getColumnNum())) {
            throw new BusinessException(ErrorEnum.IMPORT_DATA_CLUMMUN_ERR);
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

    public static int checkExcel(Workbook workbook, ReadFileConditon readFileConditon) {

        int result = FileImportAction.ERR_SUCCESS;
        //1、文件分页是否存在
        if (checkSheet(workbook, readFileConditon.getSheetIndex())) {
            return FileImportAction.ERR_FILE;
        }

        //2、表格题目的判断
        Sheet sheet = workbook.getSheetAt(readFileConditon.getSheetIndex());
        if (checkExcelLineData(sheet, readFileConditon.getTitleRowIndex(), readFileConditon.getColumnNum())) {
            result = FileImportAction.ERR_CLUMN;
        }

        return result;
    }

    public static List<String> readExcel(MultipartFile file, ReadFileConditon readFileConditon) throws BusinessException {
        Workbook workbook = getWorkbook(file);
        List<String> stringList = readExcelFromWorkbook(workbook, readFileConditon);
        return stringList;

    }

    /**
     * 默认读取一个sheet页的全部数据
     *
     * @param file
     * @param rfc
     * @return
     */
    public static List<List<String>> getExcelData(MultipartFile file, ReadFileConditon rfc) {

        Workbook workbook = getWorkbook(file);
        if (null == workbook) {
            throw new BusinessException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        int checkResult = checkExcel(workbook, rfc);
        if (checkResult != FileImportAction.ERR_SUCCESS) {
            throw new BusinessException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        Sheet sheet = workbook.getSheetAt(rfc.getSheetIndex());
        int rowNum = sheet.getLastRowNum() + 1;
        int ignoreRowIndex = rfc.getIgonreRowNum();
        int columnNum = rfc.getColumnNum();

        return IntStream.range(ignoreRowIndex, rowNum)
                .mapToObj(sheet::getRow)
                .filter(Objects::nonNull)
                .map(row -> ExcelUtil.readRowList(row, columnNum))
                .collect(Collectors.toList());
    }
}
