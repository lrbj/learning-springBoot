
package com.example.mybatisdemo.dto;

import lombok.Data;

/**
 * @description: 窗口查询请求参数(含分页参数及查询参数)
 **/
@Data
public class SearchBox {
    // 分页参数
    private PageParam pageParam;
    // 窗口查询参数
    private SearchParam searchParam;

}
