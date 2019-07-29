package com.example.fileopera.service.impl;

import com.example.fileopera.constant.ErrorEnum;
import com.example.fileopera.constant.FileConstant;
import com.example.fileopera.exception.BusinessException;
import com.example.fileopera.service.CsvOperaService;
import com.example.fileopera.util.ReadFileConditon;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
        if (!fileName.endsWith(FileConstant.SUFFIX_CSV)) {
            throw new BusinessException(ErrorEnum.FILEFORMAT_ERROR.getCode(), ErrorEnum.FILEFORMAT_ERROR.getMsg());
        }

        //读取每一行数据
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), FileConstant.CODE_FORMAT));
            String line = "";
            int rowNum = 0;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("line" + rowNum + ": " + line);
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

    @Override
    public List<String> readCsvFile(MultipartFile file, ReadFileConditon readFileConditon) throws BusinessException, IOException {
        BufferedReader bReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "utf-8"));


        //1、对于第一行的判断
        if (checkCsvLineData(bReader.readLine(), readFileConditon.getColumnNum())) {
            throw new BusinessException(ErrorEnum.IMPORT_DATA_CLUMMUN_ERR.getCode(), ErrorEnum.IMPORT_DATA_CLUMMUN_ERR.getMsg());
        }

        //2、不需要读取的行
        if (readFileConditon.getIgonreRowNum() > 0) {
            for (int i = 1; i < readFileConditon.getIgonreRowNum(); i++) {
                bReader.readLine();
            }
        }

        //3、 提取文件中的数据
        List<String> stringList = readCsvData(bReader, readFileConditon.getColumnNum());
        return stringList;
    }

    @Override
    public boolean checkCsvLineData(String line, int columnNum) {
        if (("" == line.trim()) || (null == line)
                || (line.split(",").length < columnNum)) {
            return true;
        }

        return false;
    }

    @Override
    public List<String> readCsvData(BufferedReader bReader, int columnNum) throws IOException {
        List<String> stringList = new ArrayList<>();
        String line = "";
        while ((line = bReader.readLine()) != null) {

            String[] pills = line.split(",", columnNum);
            stringList.add(line);
        }
        return stringList;
    }


}
