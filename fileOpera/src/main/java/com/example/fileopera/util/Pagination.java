package com.example.fileopera.util;

import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description: 自定义输出页面信息
 */
public class Pagination<T> {

    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * <strong>属性名不要更改</strong>
     */
    private int totalCount;

    /**
     * <strong>属性名不要更改</strong>
     */
    private List<T> dataList;

    public Pagination() {

    }

    public Pagination(int totalCount, List<T> dataList) {
        this.totalCount = totalCount;
        this.dataList = dataList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
