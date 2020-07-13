package com.example.common.constant;


import java.util.ArrayList;
import java.util.List;

public class FileImportAction {
    //错误情况
    public static final int ERR_SUCCESS = 0;
    public static final int ERR_FILE = -1; //文件错误
    public static final int ERR_CLUMN = -2;//列错误
    public static final int ERR_REPEAT = -3;//存在重复
    public static final int ERR_OPEARA_NOTEXIST = -4;//操作不存在



    //导入或者删除操作
    public static  final int REMOVE_DBDATA = 0;//删除数据
    public static  final int ADD_DBDATA    = 1;//增加数据
    public static final int  GENERATE_JSON = 3;//生成json数据


    //excel 成功或者失败情况
    public static final String SUCCESSROWS = "sucessrow_num";
    public static final String FAILROWS = "failrow_num";

    //文件后缀名
    public static final String SUFFIX_XLS = ".xls";
    public static final String SUFFIX_XLSX = ".xlsx";
    public static final String SUFFIX_CSV = ".csv";


    public static final int TITLE_ROWINDEX = 1;

    public static final int IGONREROWS_BASE = 1;
    public static final int CLUMNNUM_BASE  = 16;

    public static final int IGONREROWS_ALLDEVICE=5;
    public static final int CLUMNNUM_ALLDEVICE=32;

    public static final int IGONREROWS_SINGLET=3;
    public static final int CLUMNNUM__SINGLET=40;

}
