package com.example.fileopera.service;

import com.example.fileopera.exception.BusinessException;
import com.example.fileopera.util.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 10:33 AM 11/27/2018
 */
public interface CsvOperaService {
  void readCsvFile(MultipartFile file ) throws BusinessException;
}
