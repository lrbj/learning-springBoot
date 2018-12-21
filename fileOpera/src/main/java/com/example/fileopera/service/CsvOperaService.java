package com.example.fileopera.service;

import com.example.fileopera.exception.BusinessException;
import com.example.fileopera.util.ReadFileConditon;
import com.example.fileopera.util.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 10:33 AM 11/27/2018
 */
public interface CsvOperaService {
  void readCsvFile(MultipartFile file ) throws BusinessException;

  //按照指定条件读取
  List<String> readCsvFile(MultipartFile file, ReadFileConditon readFileConditon) throws BusinessException, IOException;

  boolean checkCsvLineData(String line,int columnNum);

  List<String>  readCsvData(BufferedReader bufferedReader, int columnNum) throws IOException;
}
