package com.example.fileopera.service;

import com.example.fileopera.entity.People;
import com.example.fileopera.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:17 PM 11/22/2018
 */
public interface ExcelOperaService {
    void  readExcel(MultipartFile file) throws BusinessException;
}
