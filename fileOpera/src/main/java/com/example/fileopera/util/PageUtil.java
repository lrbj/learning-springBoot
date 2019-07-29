package com.example.fileopera.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:01 PM 3/1/2019
 */
public class PageUtil {

    /**
     * 获取PageRequest请求
     *
     * @return
     */
    public static Pageable getPageRequest() {
        PageSearch pageSearch = PageHolder.getHolder();
        PageRequest pageRequest = PageRequest.of(pageSearch.getPi(), pageSearch.getPs(),
                Sort.Direction.valueOf(pageSearch.getOrderType().toString()), pageSearch.getOrderColumn());
        return pageRequest;
    }


    /**
     * 封装查询条件
     *
     * @return
     */
    public static Specification getSpecification() {
        Specification specification = (Specification) (root, query, criteriaBuilder) -> {
            Map<String, Object> pageParameter = PageHolder.getHolder().getPageParameter();
            if (pageParameter != null) {
                /**
                 * 连接查询条件, 不定参数，可以连接0..N个查询条件
                 */
                for (Map.Entry<String, Object> entry : pageParameter.entrySet()) {
                    String[] key = entry.getKey().split("\\.");
                    Path p = null;
                    for (String k : key) {
                        if (p == null) {
                            p = root.get(k);
//                        Path path = root.get(k);
                        } else {
                            p = p.get(k);
                        }
                    }
                    if (p.getJavaType() == String.class) {
                        query = query.where(criteriaBuilder.like(p, "%" + entry.getValue() + "%"));
                    } else {
                        query = query.where(criteriaBuilder.equal(p, entry.getValue()));
                    }
                }
            }
            return null;
        };
        return specification;
    }


    //获取导出的excel数据
    public static ExcelData getexcelData() {
        PageSearch pageSearch = PageHolder.getHolder();
        Map<String, Object> pageParameters = pageSearch.getExportParameter();
        String where = "";
        ExcelData data = new ExcelData();
        for (Map.Entry<String, Object> entry : pageParameters.entrySet()) {
            if (entry.getKey().equals("title")) {
                data.setTitle((List<String>) entry.getValue());
            }

            if (entry.getKey().equals("rows")) {
                data.setRows((List<List<Object>>) entry.getValue());
            }
            if (entry.getKey().equals("sheetName")) {
                data.setSheetName((String) entry.getValue());
            }
            if (entry.getKey().equals("fileName")) {
                data.setFileName((String) entry.getValue());
            }

        }

        return data;

    }
}
