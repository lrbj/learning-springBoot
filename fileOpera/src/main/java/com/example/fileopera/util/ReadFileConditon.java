package com.example.fileopera.util;

import lombok.Data;

/**
 * @Author: Kayla, Ye
 * @Description:读取文件的所需要的条件
 * @Date:Created in 3:26 PM 12/21/2018
 */
@Data
public class ReadFileConditon {

    private int igonreRowNum = 1;//可以跳过的行数

    private int columnNum = 1; //规定要读取的列数

    private int sheetIndex = 0; //分页id，默认为第一页

    private String fileName;//预留可用生成的文件的名字

    private int operateType;//可以根据实际情况进行扩展操作
}
