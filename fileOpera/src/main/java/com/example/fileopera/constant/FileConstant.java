package com.example.fileopera.constant;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:10 PM 10/18/2018
 */
public class FileConstant {
    public static final int ERR_SUCCESS = 0;
    public static final int ERR_FILE = -1; //文件错误
    public static final int ERR_CLUMN = -2;//列错误
    public static final int ERR_REPEAT = -3;//存在重复
    public static final int ERR_OPEARA_NOTEXIST = -4;//操作不存在

    public static final int IGONREROWS = 1;//忽略统计的的头几行
    public static final int CLUMNNUM = 8;//固定的行数
    public static final String SUCCESSROWS = "sucessrow_num";

    public static final String SUFFIX_XLS = ".xls";
    public static final String SUFFIX_XLSX = ".xlsx";
    public static final String SUFFIX_CSV = ".csv";

    public static final String CODE_FORMAT = "utf-8";

    //验证的列
    public static final int COLUMN_TYPE = 1; //系统类型
    public static final int COLUMN_ADDRESS = 6;//设备地址


    //导入或者删除操作
    public static  final int REMOVE_DBDATA = 0;//删除数据
    public static  final int ADD_DBDATA    = 1;//增加数据

    //判断文件是否是文件夹还是文件
    public static  final int IS_DIRECTORY = 0;//文件夹
    public  static final int IS_FILE  = 1;//文件
}
