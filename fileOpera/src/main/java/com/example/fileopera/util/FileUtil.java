package com.example.fileopera.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 10:32 AM 11/27/2018
 */
public class FileUtil {
    /** 将数据写入文件中
     * @param fileData
     * @param fileDir 文件目录
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
            if(fileData != null ){
                out.write(fileData);
            }
            else{
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
}
