package com.example.fileopera.util;

import com.example.fileopera.constant.OrderType;
import com.example.fileopera.constant.PageOperator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:48 PM 3/1/2019
 */
@Data
public class PageSearch {
    @ApiModelProperty(value = "搜索关键字")
    private String keyword;                            //关键字

    @ApiModelProperty(value = "页码，下标")
    private Integer pi = 0;                            //页码

    @ApiModelProperty(value = "一页显示的项目数")
    private Integer ps = 20;                           //一页显示的项目数

    @ApiModelProperty(value = "排序类型，默认降序")
    private OrderType orderType = OrderType.DESC;
    @ApiModelProperty(value = "排序字段")
    private String orderColumn = "id";

    @ApiModelProperty(value = "操作类型，分页or导出")
    private PageOperator opsType = PageOperator.LIST;

    @ApiModelProperty(value = "分页参数")
    private Map<String, Object> pageParameter;
    @ApiModelProperty(value = "导出参数")
    private Map<String, Object> exportParameter;
}