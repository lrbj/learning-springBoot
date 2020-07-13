package com.example.common.utils;

import java.util.Collections;
import java.util.List;

public class Pagination<T> {

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

    public List<T> getDataListByPage(int pageNum, int pageSize, List<T> totalDataList) {
        int fromIndex = (pageNum - 1) * pageSize;
        if (null == totalDataList || fromIndex >= totalDataList.size()) {
            return Collections.emptyList();
        }

        int toIndex = pageNum * pageSize;
        if (toIndex >= totalDataList.size()) {
            toIndex = totalDataList.size();
        }
        return totalDataList.subList(fromIndex, toIndex);
    }

    public static Pagination buildPagination(int pageNum, int pageSize, List dataList) {
        Pagination pagination = new Pagination();
        if (null == dataList || dataList.isEmpty()) {
            pagination.setTotalCount(0);
            pagination.setDataList(Collections.emptyList());
            return pagination;
        }

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = pageNum * pageSize;
        if (toIndex >= dataList.size()) {
            toIndex = dataList.size();
        }
        pagination.setTotalCount(dataList.size());
        pagination.setDataList(dataList.subList(fromIndex, toIndex));
        return pagination;
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
