package com.example.fileopera.util;

import com.example.fileopera.constant.ErrorEnum;
import com.example.fileopera.constant.FileConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 10:32 AM 11/27/2018
 */
public class FileUtil {
    /**
     * 将数据写入文件中
     *
     * @param fileData
     * @param fileDir  文件目录
     * @param fileName 文件名
     * @return
     * @throws Exception
     */
    public static String uploadFile(byte[] fileData, String fileDir, String fileName) throws Exception {
        File targetFile = new File(fileDir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        String path = fileDir + fileName;
        FileOutputStream out = new FileOutputStream(path);
        try {
            if (fileData != null) {
                out.write(fileData);
            } else {
                out.write(0x00);
            }
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }

        }

        return path;
    }

    /**
     * 判断文件路径的是文件目录还是文件，如果不存在则创建
     *
     * @param filePath
     * @return
     */
    public static int filePathType(String filePath) {
        File file = new File(filePath);
        int filePathType = 0;
        if (file.isDirectory()) {
            filePathType = FileConstant.IS_DIRECTORY;
        } else if (file.isFile()) {
            filePathType = FileConstant.IS_FILE;
        } else {
            return FileConstant.ERR_FILE;//错误
        }

        return filePathType;

    }

    public static ResponseObject getErrResult(int[] result) {

        if (result[0] != FileConstant.ERR_SUCCESS) {
            Object resultObj = new Object();
            switch (result[0]) {
                case FileConstant.CLUMNNUM: {
                    resultObj = ErrorEnum.IMPORT_DATA_CLUMMUN_ERR;
                    break;
                }
                case FileConstant.ERR_REPEAT: {
                    resultObj = ErrorEnum.IMPORT_DATA_REPEAT;
                    break;
                }
                default: {
                    resultObj = ErrorEnum.IMPORT_DATA_FAIL;
                    break;
                }
            }

            return ResponseObject.fail((ErrorEnum) resultObj);
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put(FileConstant.SUCCESSROWS, result[1]);
        return ResponseObject.success(resultMap);
    }
}
