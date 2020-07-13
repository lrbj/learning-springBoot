package com.example.common.utils;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ListPage<T> {

    private Integer totalCount;

    private List<T> dataList;

    public ListPage(List<T> dataList) {
        this.dataList = dataList;
    }

    /**
     * 得到分页后的list
     *
     * @param pageNum 页码
     * @return
     */
    public ListPage<T> getPagedList(int pageNum, int pageSize) {
        int fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= dataList.size()) {
            return new ListPage<>(Collections.emptyList());
        }

        int toIndex = pageNum * pageSize;
        if (toIndex >= dataList.size()) {
            toIndex = dataList.size();
        }
        return new ListPage<>(dataList.subList(fromIndex, toIndex));
    }

    public  ListPage(Integer totalCount, List<T> dataList){
        this.totalCount = totalCount;
        this.dataList = dataList;
    }
}
