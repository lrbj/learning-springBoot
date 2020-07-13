package com.example.common.utils;

import com.example.common.constant.ErrorEnum;

public class EnumUtil {

    public static <T extends ErrorEnum> T getByCode(Integer code, Class<T> enumClass) {
        //通过反射取出Enum所有常量的属性值
        for (T each: enumClass.getEnumConstants()) {
            //利用code进行循环比较，获取对应的枚举
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
