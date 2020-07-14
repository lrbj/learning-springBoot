package com.example.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {

    public static String convertString(ArrayList<String> stringList) {
        if (CollectionUtils.isEmpty(stringList)) {
            return null;
        }
        return stringList.stream().collect(Collectors.joining(","));
    }

    public static ArrayList<String> convertList(String data) {
        return data == null ? null : new ArrayList<>(Arrays.stream(data.split(",")).collect(Collectors.toList()));
    }
}
