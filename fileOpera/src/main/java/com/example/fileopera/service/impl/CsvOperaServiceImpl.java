package com.example.fileopera.service.impl;

import com.example.fileopera.constant.ErrorEnum;
import com.example.fileopera.constant.FileImportAction;
import com.example.fileopera.exception.BusinessException;
import com.example.fileopera.service.CsvOperaService;
import com.example.fileopera.util.ResponseObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 10:42 AM 11/27/2018
 */
@Service
public class CsvOperaServiceImpl implements CsvOperaService {

    @Override
    public void readCsvFile(MultipartFile file) throws BusinessException {
        // 文件是否存在
        if (null == file) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "文件不存在");
        }

        //文件名判断
        String fileName = file.getOriginalFilename();
        System.out.println("fileName=" + fileName);
        if (!fileName.endsWith(FileImportAction.SUFFIX_CSV)) {
            throw new BusinessException(ErrorEnum.FILEFORMAT_ERROR.getCode(), ErrorEnum.FILEFORMAT_ERROR.getMsg());
        }

        //读取每一行数据
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), FileImportAction.CODE_FORMAT));
            String line ="";
            int rowNum = 0;
            while ((line = bufferedReader.readLine()) != null){
                System.out.println("line"+rowNum+": " + line);
                rowNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    }
